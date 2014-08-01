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
import net.nexustools.gui.Base;
import net.nexustools.gui.Body;
import net.nexustools.gui.Button;
import net.nexustools.gui.CheckBox;
import net.nexustools.gui.Container;
import net.nexustools.gui.Label;
import net.nexustools.gui.Widget;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.platform.Platform;
import net.nexustools.gui.platform.PlatformException;
import net.nexustools.gui.platform.RenderTargetSupportedException;
import net.nexustools.gui.provider.awt.impl.AWTWidgetImpl;
import net.nexustools.gui.render.Font;
import net.nexustools.io.format.StreamReader;

/**
 *
 * @author katelyn
 */
public class AWTPlatform extends Platform<java.awt.Component> {
    
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
    public Base create(Class<? extends Base> type) throws RenderTargetSupportedException {
        if(Container.class.isAssignableFrom(type))
            return new AWTContainer(this);
        else if(Label.class.isAssignableFrom(type))
            return new AWTLabel(this);
        else if(Button.class.isAssignableFrom(type))
            return new AWTButton(this);
        else if(CheckBox.class.isAssignableFrom(type))
            return new AWTCheckBox(this);
        else if(Body.class.isAssignableFrom(type))
            return new AWTBody(this);
        
        throw new RenderTargetSupportedException();
    }

    @Override
    public Widget parse(StreamReader processor) throws PlatformException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public String[] LAFs() {
        return new String[]{"AWT", "Blank"};
    }
    
    @Override
    public void setLAF(final String laf) {
        // TODO: Implement Blank
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
    
}
