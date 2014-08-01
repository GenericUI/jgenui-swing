/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.ListIterator;
import net.nexustools.concurrent.BaseAccessor;
import net.nexustools.concurrent.BaseReader;
import net.nexustools.concurrent.BaseWriter;
import net.nexustools.concurrent.FakeLock;
import net.nexustools.gui.Body;
import net.nexustools.gui.Button;
import net.nexustools.gui.CheckBox;
import net.nexustools.gui.ComboBox;
import net.nexustools.gui.Container;
import net.nexustools.gui.Label;
import net.nexustools.gui.Widget;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.platform.Platform;
import net.nexustools.gui.platform.PlatformException;
import net.nexustools.gui.provider.awt.impl.AWTWidgetImpl;
import net.nexustools.gui.render.Font;
import net.nexustools.gui.render.StyleSheet;
import net.nexustools.gui.wrap.WrappedPlatform;
import net.nexustools.utils.Creator;

/**
 *
 * @author katelyn
 */
public class AWTPlatform extends WrappedPlatform<java.awt.Component> {
    
    static abstract class RunnableReader<R> implements Runnable {
        public R value;
    }
    public static class AWTEventQueue extends EventQueue {
        private final ArrayList<Runnable> idleEvents = new ArrayList();
        
        @Override
        protected void dispatchEvent(AWTEvent event) {
            super.dispatchEvent(event);
            testIdle();
        }
        
        public void testIdle() {
            if (peekEvent() == null) {
                ListIterator<Runnable> li = idleEvents.listIterator(idleEvents.size());

                while(li.hasPrevious()) {
                    try {
                        li.previous().run();
                    } catch(RuntimeException ex) {
                        ex.printStackTrace();
                    }
                }
                idleEvents.clear();
            }
        }

        private void onIdle(final Runnable run) {
            idleEvents.add(run);
            testIdle();
        }
    }
    protected static final AWTEventQueue eventQueue = new AWTEventQueue();
    
    public static AWTPlatform instance() {
        Platform currentPlatform = Platform.current();
        if(currentPlatform instanceof AWTPlatform)
            return (AWTPlatform)currentPlatform; // Good Enough
        return (AWTPlatform)Platform.byClass(AWTPlatform.class);
    }
    
    protected AWTPlatform(String name) {
        super("jgenui-" + name);
        System.out.println("Creating `" + name + "` platform");
        try {
            act(new Runnable() {
                public void run() {
                    makeCurrent();
                }
            });
        } catch (InvocationTargetException ex) {}
        makeCurrent();
    }
    public AWTPlatform() {
        this("awt");
    }

    @Override
    public <T, F> T convert(F from) throws PlatformException {
        if(from instanceof java.awt.Font)
            return (T)new Font();
        if(from instanceof java.awt.Color)
            return (T)new Font();
        if(from instanceof java.awt.Dimension)
            return (T)new Size(((java.awt.Dimension)from).width, ((java.awt.Dimension)from).height);
        if(from instanceof Widget) {
            return (T)convertWidget((Widget)from);
        }
        
        throw new PlatformException("Cannot convert " + from.getClass().getName());
    }
    
    public Widget convertWidget(Widget from) throws PlatformException {
        if(from instanceof AWTWidgetImpl)
            return from;

        if(from instanceof Label)
            return new AWTLabel((Label)from, this);
        if(from instanceof Button)
            return new AWTButton((Button)from, this);

        throw new PlatformException("AWT has no implementation widget compatible with " + from.getClass().getName());
    }

    @Override
    protected void populate(Population population) {
        population.add(Label.class, new Creator<Label>() {
            public Label create() {
                return new AWTLabel(AWTPlatform.this);
            }
        });
        
        population.add(Button.class, new Creator<Button>() {
            public Button create() {
                return new AWTButton(AWTPlatform.this);
            }
        });
        population.add(CheckBox.class, new Creator<CheckBox>() {
            public CheckBox create() {
                return new AWTCheckBox(AWTPlatform.this);
            }
        });
        population.add(ComboBox.class, new Creator<ComboBox>() {
            public ComboBox create() {
                return new AWTComboBox(AWTPlatform.this);
            }
        });
        
        population.add(Container.class, new Creator<Container>() {
            public Container create() {
                return new AWTContainer(AWTPlatform.this);
            }
        });
        population.add(Body.class, new Creator<Body>() {
            public Body create() {
                return new AWTBody(AWTPlatform.this);
            }
        });
    }

    @Override
    public boolean supports(Feature feature) {
        switch(feature) {
            case MultipleBodies:
            case ComplexDrawing:
            case FullPainter:
                return true;
        }
        return false;
    }

    @Override
    public String[] LAFs0() {
        return new String[]{"AWT"};
    }
    
    protected void setLAF0(final String laf) {
        if(laf.equals("AWT"))
            setStyleSheet(null);
        else
            setStyleSheet(lafStyleSheet(laf));
    }

    @Override
    public String LAF() {
        return "AWT";
    }

    @Override
    public void onIdle(final Runnable run) {
        try {
            act(new Runnable() {
                @Override
                public void run() {
                    eventQueue.onIdle(run);
                }
            });
        } catch (InvocationTargetException ex) {
            ex.getCause().printStackTrace();
        }
    }

    @Override
    public void act(Runnable run) throws InvocationTargetException {
        if(EventQueue.isDispatchThread())
            run.run();
        else
            while(true)
                try {
                    eventQueue.invokeAndWait(run);
                    break;
                } catch (InterruptedException ex) {}
    }

    @Override
    public void open(String url) {
        try {
            open(new URI(url));
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void open(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else
            throw new UnsupportedOperationException();
    }

    @Override
    public Component nativeFor(Widget widget) throws PlatformException {
        return ((AWTWidgetImpl)convertWidget(widget))._n();
    }

    @Override
    public AWTClipboard clipboard() {
        return AWTClipboard.instance();
    }

    public void write(final BaseAccessor data, final BaseWriter actor) {
        try {
            act(new Runnable() {
                public void run() {
                    actor.write(data, FakeLock.instance);
                }
            });
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }

    public Object read(final BaseAccessor data, final BaseReader reader) {
        RunnableReader swingReader = new RunnableReader() {
            public void run() {
                value = reader.read(data, FakeLock.instance);
            }
        };
        try {
            act(swingReader);
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return swingReader.value;
    }

    public StyleSheet styleSheet() {
        return null;
    }

    public void setStyleSheet(StyleSheet styleSheet) {
        // TODO: Implement
    }
    
}
