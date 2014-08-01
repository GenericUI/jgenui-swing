/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import net.nexustools.concurrent.BaseAccessor;
import net.nexustools.concurrent.ListAccessor;
import net.nexustools.concurrent.PropAccessor;
import net.nexustools.concurrent.Writer;
import net.nexustools.event.Event;
import net.nexustools.event.EventDispatcher;
import net.nexustools.gui.platform.Platform;
import net.nexustools.gui.provider.awt.AWTPlatform;

/**
 *
 * @author katelyn
 */
public class PropDispatcher<L extends EventListener, E extends Event> extends EventDispatcher<Platform, L, E> {

    private final ListenerProp<L> prop;
    public PropDispatcher(ListenerProp prop, Platform queue) {
        super(queue);
        this.prop = prop;
    }

    @Override
    public void connect() {
        System.out.println("Connected Dispatcher");
        prop.write(new Writer() {
            @Override
            public void write(final BaseAccessor data) {
                try {
                    AWTPlatform.instance().act(new Runnable() {
                        public void run() {
                            prop.connect((PropAccessor<L>)data);
                        }
                    });
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public void disconnect() {
        System.out.println("Disconnected Dispatcher");
        prop.write(new Writer() {
            @Override
            public void write(final BaseAccessor data) {
                try {
                    AWTPlatform.instance().act(new Runnable() {
                        public void run() {
                            prop.disconnect((PropAccessor<L>)data);
                        }
                    });
                } catch (InvocationTargetException ex) {
                    ex.getCause().printStackTrace();
                }
            }
        });
    }
    
}
