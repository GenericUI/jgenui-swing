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
import net.nexustools.gui.Base;
import net.nexustools.gui.Body;
import net.nexustools.gui.Button;
import net.nexustools.gui.CheckBox;
import net.nexustools.gui.ComboBox;
import net.nexustools.gui.Container;
import net.nexustools.gui.Frame;
import net.nexustools.gui.Label;
import net.nexustools.gui.RadioButton;
import net.nexustools.gui.ToggleButton;
import net.nexustools.gui.platform.Platform;
import net.nexustools.gui.platform.RenderTargetNotSupportedException;
import net.nexustools.gui.provider.awt.AWTPlatform;

/**
 *
 * @author katelyn
 */
public class SwingPlatform extends AWTPlatform {
    
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
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (UnsupportedLookAndFeelException ex) {}
    }

    @Override
    public Base create(Class<? extends Base> type) throws RenderTargetNotSupportedException {
        if(Container.class.isAssignableFrom(type))
            return new SwingContainer(this);
        else if(Label.class.isAssignableFrom(type))
            return new SwingLabel(this);
        else if(Button.class.isAssignableFrom(type))
            return new SwingButton(this);
        else if(ToggleButton.class.isAssignableFrom(type))
            return new SwingToggleButton(this);
        else if(CheckBox.class.isAssignableFrom(type))
            return new SwingCheckBox(this);
        else if(RadioButton.class.isAssignableFrom(type))
            return new SwingRadioButton(this);
        else if(ComboBox.class.isAssignableFrom(type))
            return new SwingComboBox(this);
        else if(Frame.class.isAssignableFrom(type))
            return new SwingFrame(this);
        else if(Body.class.isAssignableFrom(type))
            return new SwingBody(this);
        
        return super.create(type);
    }

    @Override
    public String[] LAFs() {
        String[] cssLAFs = cssLAFs();
        UIManager.LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
        String[] styles = new String[lookAndFeels.length+cssLAFs.length];
        for(int i=0; i<lookAndFeels.length; i++) {
            styles[i] = lookAndFeels[i].getName();
        }
        for(int i=0; i<cssLAFs.length; i++)
            styles[lookAndFeels.length+i] = cssLAFs[i];
        return styles;
    }
    
    @Override
    public void setLAF(final String laf) {
        try {
            act(new Runnable() {
                
                @Override
                public void run() {
                    boolean foundLAF = false;
                    UIManager.LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
                    for(int i=0; i<lookAndFeels.length; i++) {
                        if(lookAndFeels[i].getName().equals(laf))
                            try {
                                UIManager.setLookAndFeel(lookAndFeels[i].getClassName());
                                foundLAF = true;
                                break;
                            } catch (ClassNotFoundException ex) {} catch (InstantiationException ex) {} catch (IllegalAccessException ex) {} catch (UnsupportedLookAndFeelException ex) {}
                    }
                    if(!foundLAF) {
                        setSystemLAF();
                        SwingPlatform.super.setLAF(laf);
                    } else
                        setStyleSheet(null);
                    
                    for(Window window : Window.getWindows()) {
                        SwingUtilities.updateComponentTreeUI(window);
                    }
                }
            });
        } catch (InvocationTargetException ex) {
            ex.getCause().printStackTrace();
        }  
    }

    @Override
    public String LAF() {
        return UIManager.getLookAndFeel().getName();
    }
    
}
