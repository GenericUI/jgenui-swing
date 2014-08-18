/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Window;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.nexustools.concurrent.logic.IfWriteReader;
import net.nexustools.concurrent.Prop;
import net.nexustools.concurrent.PropAccessor;
import net.nexustools.gui.impl.Body;
import net.nexustools.gui.platform.GUIPlatform;
import net.nexustools.gui.provider.awt.AWTPlatform;
import net.nexustools.utils.Creator;

/**
 *
 * @author katelyn
 */
public class SwingPlatform extends AWTPlatform {
    
    private static final Prop<String> currentLAF = new Prop();
    public static SwingPlatform instance() {
        return instance(true);
    }
    
    public static SwingPlatform instance(boolean exactMatch) {
        if(!exactMatch) {
            GUIPlatform currentPlatform = GUIPlatform.current();
            if(currentPlatform instanceof SwingPlatform)
                return (SwingPlatform)currentPlatform; // Good Enough
        }
        return (SwingPlatform)GUIPlatform.byClass(SwingPlatform.class);
    }

    public SwingPlatform() {
        super("Swing");
    }
    
    protected static void setSystemLAF() {
        if(setLAFClass(UIManager.getSystemLookAndFeelClassName())) {
            UIManager.LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
            for(UIManager.LookAndFeelInfo lookAndFeel : lookAndFeels) {
                if(lookAndFeel.getClassName().equals(UIManager.getLookAndFeel().getClass().getName()))
                    currentLAF.set(lookAndFeel.getName());
            }
        }
    }

    @Override
    protected void populate(WidgetRegistry baseRegistry) {
        super.populate(baseRegistry);
        baseRegistry.add(Body.class, new Creator<Body, SwingPlatform>() {
            public Body create(SwingPlatform using) {
                return new SwingBody(using);
            }
        });
    }
    
    private static final Prop<String> lafClassName = new Prop();
    protected static boolean setLAFClass(final String className) {
        return lafClassName.read(new IfWriteReader<Boolean, PropAccessor<String>>() {
            @Override
            public Boolean def() {
                return true;
            }
            @Override
            public boolean test(PropAccessor<String> against) {
                return !className.equals(against.get());
            }
            @Override
            public Boolean read(PropAccessor<String> data) {
                try {
                    UIManager.setLookAndFeel(className);
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            for(Window window : Window.getWindows()) {
                                SwingUtilities.updateComponentTreeUI(window);
                            }
                        }
                    });
                    data.set(className);
                    return true;
                } catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }
                return false;
            }
        });
    }
    
//    @Override
//    public void setLAF0(final String laf) {
//        try {
//            currentLAF.set(laf);
//            act(new Runnable() {
//                @Override
//                public void run() {
//                    boolean foundLAF = false;
//                    UIManager.LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
//                    for(int i=0; i<lookAndFeels.length; i++) {
//                        if(lookAndFeels[i].getName().equals(laf) && setLAFClass(lookAndFeels[i].getClassName())) {
//                            foundLAF = true;
//                            break;
//                        }
//                    }
//                    if(!foundLAF) {
//                        setSystemLAF();
//                        SwingPlatform.super.setLAF0(laf);
//                    } else
//                        setStyleSheet(null);
//                }
//            });
//        } catch (InvocationTargetException ex) {
//            ex.getCause().printStackTrace();
//        }  
//    }
//
//    @Override
//    public String LAF() {
//        return currentLAF.get();
//    }
    
}
