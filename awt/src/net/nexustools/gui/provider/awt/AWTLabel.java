/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Component;
import java.awt.Label;
import net.nexustools.gui.impl.Image;
import net.nexustools.gui.provider.awt.AWTLabel.NALabel;
import net.nexustools.gui.provider.awt.AWTWidget.NAComponent;
import net.nexustools.gui.wrap.WLabel;
import net.nexustools.gui.wrap.WPlatform;
import net.nexustools.gui.wrap.impl.NLabel;

/**
 *
 * @author katelyn
 */
public class AWTLabel<N extends NALabel> extends WLabel<N> {

    public AWTLabel(String tag, WPlatform platform) {
        super(tag, platform);
    }
    
    public static abstract class NALabel<C extends Component, WW extends WLabel> extends NAComponent<C, WW> implements NLabel<WW> {
        public NALabel(C component) {
            super(component);
        }
    }

    @Override
    protected N createNative() {
        return (N) new NALabel<Label, WLabel>(new Label()) {
            public void nativeSetText(String text) {
                component.setText(text);
            }
            public void nativeSetIcon(Image icon) {}
        };
    }
    
}
