/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Button;
import java.awt.Component;
import net.nexustools.gui.impl.Image;
import net.nexustools.gui.provider.awt.AWTButton.NAButton;
import net.nexustools.gui.provider.awt.AWTLabel.NALabel;
import net.nexustools.gui.wrap.WButton;
import net.nexustools.gui.wrap.WPlatform;
import net.nexustools.gui.wrap.impl.NButton;

/**
 *
 * @author katelyn
 */
public class AWTButton<N extends NAButton> extends WButton<N> {

    protected AWTButton(String tag, WPlatform platform) {
        super(tag, platform);
    }
    public AWTButton() {
        super("Button", AWTPlatform.instance());
    }
    
    public static abstract class NAButton<C extends Component, WW extends WButton> extends NALabel<C, WW> implements NButton<WW> {
        public NAButton(C component) {
            super(component);
        }
    }

    @Override
    protected N createNative() {
        return (N) new NAButton<Button, WButton>(new Button()) {
            public void nativeSetText(String text) {
                component.setLabel(text);
            }
            public void nativeSetIcon(Image icon) {}
        };
    }
    
}
