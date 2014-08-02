/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.AWTEvent;
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
import net.nexustools.gui.Label;
import net.nexustools.gui.platform.Platform;
import net.nexustools.gui.wrap.WrappedLabel;
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
        invokeLater(new Runnable() {
            public void run() {
                makeCurrent();
            }
        });
        makeCurrent();
    }
    public AWTPlatform() {
        this("awt");
    }

    @Override
    protected void populate(BaseRegistry population) {
        population.add(Label.class, new Creator<Label, AWTPlatform>() {
            public Label create(AWTPlatform using) {
                return new WrappedLabel(AWTPlatform.this, AWTLabel.CREATOR);
            }
        });
//        
//        population.add(Button.class, new Creator<Button>() {
//            public Button create() {
//                return new AWTButton(AWTPlatform.this);
//            }
//        });
//        population.add(CheckBox.class, new Creator<CheckBox>() {
//            public CheckBox create() {
//                return new AWTCheckBox(AWTPlatform.this);
//            }
//        });
//        population.add(ComboBox.class, new Creator<ComboBox>() {
//            public ComboBox create() {
//                return new AWTComboBox(AWTPlatform.this);
//            }
//        });
//        
//        population.add(Container.class, new Creator<Container>() {
//            public Container create() {
//                return new AWTContainer(AWTPlatform.this);
//            }
//        });
//        population.add(Body.class, new Creator<Body>() {
//            public Body create() {
//                return new WrappedBody(new AWTBody(AWTPlatform.this));
//            }
//        });
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
    public void onIdle(final Runnable run) {
        invokeLater(new Runnable() {
            @Override
            public void run() {
                eventQueue.onIdle(run);
            }
        });
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
    public AWTClipboard clipboard() {
        return AWTClipboard.instance();
    }

    public void write(final BaseAccessor data, final BaseWriter actor) {
        invokeAndWait(new Runnable() {
            public void run() {
                actor.write(data, FakeLock.instance);
            }
        });
    }

    public Object read(final BaseAccessor data, final BaseReader reader) {
        RunnableReader swingReader = new RunnableReader() {
            public void run() {
                value = reader.read(data, FakeLock.instance);
            }
        };
        invokeAndWait(swingReader);
        return swingReader.value;
    }

    public final void invokeLater(Runnable run) {
        invokeLater(run, QueuePlacement.AtBack);
    }
    
    @Override
    public final void invokeLater(Runnable run, QueuePlacement placement) {
        switch(placement) {
            case AtBack:
                EventQueue.invokeLater(run);
                break;
                
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public final void invokeAndWait(Runnable run) {
        if(EventQueue.isDispatchThread())
            run.run();
        else
            while(true)
                try {
                    EventQueue.invokeAndWait(run);
                    break;
                } catch (InterruptedException ex) {
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                    break;
                }
    }
    
}
