/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Window;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.nexustools.concurrent.IfWriteReader;
import net.nexustools.concurrent.Prop;
import net.nexustools.concurrent.PropAccessor;
import net.nexustools.gui.Body;
import net.nexustools.gui.Button;
import net.nexustools.gui.CheckBox;
import net.nexustools.gui.ComboBox;
import net.nexustools.gui.Container;
import net.nexustools.gui.Frame;
import net.nexustools.gui.Label;
import net.nexustools.gui.MultiList;
import net.nexustools.gui.RadioButton;
import net.nexustools.gui.ToggleButton;
import net.nexustools.gui.platform.Platform;
import net.nexustools.gui.provider.awt.AWTPlatform;
import net.nexustools.utils.Creator;

/**
 *
 * @author katelyn
 */
public class SwingPlatform extends AWTPlatform {
    
    private static final Prop<String> currentLAF = new Prop();
    public static SwingPlatform instance() {
        Platform currentPlatform = Platform.current();
        if(currentPlatform instanceof SwingPlatform)
            return (SwingPlatform)currentPlatform; // Good Enough
        return (SwingPlatform)Platform.byClass(SwingPlatform.class);
    }
    
    public SwingPlatform() {
        super("swing");
        setSystemLAF();
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
    protected void populate(Population population) {
        super.populate(population); // Inherit AWT
        
        population.add(Label.class, new Creator<Label>() {
            public Label create() {
                return new SwingLabel(SwingPlatform.this);
            }
        });
        
        population.add(Button.class, new Creator<Button>() {
            public Button create() {
                return new SwingButton(SwingPlatform.this);
            }
        });
        population.add(ToggleButton.class, new Creator<ToggleButton>() {
            public ToggleButton create() {
                return new SwingToggleButton(SwingPlatform.this);
            }
        });
        
        population.add(RadioButton.class, new Creator<RadioButton>() {
            public RadioButton create() {
                return new SwingRadioButton(SwingPlatform.this);
            }
        });
        population.add(CheckBox.class, new Creator<CheckBox>() {
            public CheckBox create() {
                return new SwingCheckBox(SwingPlatform.this);
            }
        });
        
        population.add(ComboBox.class, new Creator<ComboBox>() {
            public ComboBox create() {
                return new SwingComboBox(SwingPlatform.this);
            }
        });
        population.add(MultiList.class, new Creator<MultiList>() {
            public MultiList create() {
                return new SwingMultiList(SwingPlatform.this);
            }
        });
        
        population.add(Container.class, new Creator<Container>() {
            public Container create() {
                return new SwingContainer(SwingPlatform.this);
            }
        });
        population.add(Frame.class, new Creator<Frame>() {
            public Frame create() {
                return new SwingFrame(SwingPlatform.this);
            }
        });
        population.add(Body.class, new Creator<Body>() {
            public Body create() {
                return new SwingBody(SwingPlatform.this);
            }
        });
    }

    @Override
    public String[] LAFs0() {
        UIManager.LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
        String[] styles = new String[lookAndFeels.length];
        for(int i=0; i<lookAndFeels.length; i++) {
            styles[i] = lookAndFeels[i].getName();
        }
        return styles;
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
    
    @Override
    public void setLAF0(final String laf) {
        try {
            currentLAF.set(laf);
            act(new Runnable() {
                @Override
                public void run() {
                    boolean foundLAF = false;
                    UIManager.LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
                    for(int i=0; i<lookAndFeels.length; i++) {
                        if(lookAndFeels[i].getName().equals(laf) && setLAFClass(lookAndFeels[i].getClassName())) {
                            foundLAF = true;
                            break;
                        }
                    }
                    if(!foundLAF) {
                        setSystemLAF();
                        SwingPlatform.super.setLAF0(laf);
                    } else
                        setStyleSheet(null);
                }
            });
        } catch (InvocationTargetException ex) {
            ex.getCause().printStackTrace();
        }  
    }

    @Override
    public String LAF() {
        return currentLAF.get();
    }
    
}
