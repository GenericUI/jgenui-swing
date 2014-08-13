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
import java.util.List;
import java.util.ListIterator;
import net.nexustools.concurrent.BaseAccessor;
import net.nexustools.concurrent.BaseReader;
import net.nexustools.concurrent.BaseWriter;
import net.nexustools.concurrent.FakeLock;
import net.nexustools.concurrent.PropList;
import net.nexustools.gui.platform.Clipboard;
import net.nexustools.gui.platform.GUIPlatform;
import net.nexustools.gui.wrap.WPlatform;

/**
 *
 * @author katelyn
 */
public class AWTPlatform extends WPlatform {
    
    static abstract class RunnableReader<R> implements Runnable {
        public R value;
    }
    public static class AWTEventQueue extends EventQueue {
        private final PropList<Runnable> idleEvents = new PropList();
        
        @Override
        protected void dispatchEvent(AWTEvent event) {
            super.dispatchEvent(event);
            testIdle();
        }
        
        public void testIdle() {
            if (peekEvent() == null) {
                List<Runnable> taken = idleEvents.take();
                ListIterator<Runnable> li = taken.listIterator(taken.size());

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
            idleEvents.push(run);
            invokeLater(new Runnable() {
                public void run() {
                    testIdle();
                }
            });
        }
    }
    protected static final AWTEventQueue eventQueue = new AWTEventQueue();
    
    public static AWTPlatform instance() {
        return instance(true);
    }
    
    public static AWTPlatform instance(boolean exactMatch) {
        if(!exactMatch) {
            GUIPlatform currentPlatform = GUIPlatform.current();
            if(currentPlatform instanceof AWTPlatform)
                return (AWTPlatform)currentPlatform; // Good Enough
        }
        return (AWTPlatform)GUIPlatform.byClass(AWTPlatform.class);
    }
    
    protected AWTPlatform(String name) {
        super(name);
    }
    public AWTPlatform() {
        this("AWT");
    }
    
    @Override
    public boolean supports(Feature feature) {
        return false;
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
    public Clipboard clipboard() {
        return null;
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

    @Override
    protected void invokeAndWaitImpl(Runnable run) {
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

    @Override
    protected void invokeOnIdleImpl(Runnable run) {
        eventQueue.onIdle(run);
    }

    @Override
    protected void invokeLaterImpl(Runnable run) {
        EventQueue.invokeLater(run);
    }

    @Override
    protected void populate(WidgetRegistry baseRegistry) {
        
    }
    
}
