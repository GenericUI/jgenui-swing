/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Container;
import net.nexustools.gui.provider.awt.AWTAbstractContainer.NAAbstractContainer;
import net.nexustools.gui.provider.awt.AWTContainer.NAContainer;
import net.nexustools.gui.wrap.WContainer;
import net.nexustools.gui.wrap.WPlatform;
import net.nexustools.gui.wrap.impl.NContainer;

/**
 *
 * @author katelyn
 */
public class AWTContainer<N extends NAContainer> extends WContainer<N> {
    
    public static class NAContainer<C extends Container, WW extends WContainer> extends NAAbstractContainer<C, WW> implements NContainer<WW> {

        protected NAContainer(C component) {
            super(component);
        }
        protected NAContainer() {
            this((C)new Container());
        }
        
    }

    protected AWTContainer(String tag, AWTPlatform platform) {
        super(tag, platform);
    }
    protected AWTContainer(AWTPlatform platform) {
        this("Container", platform);
    }
    public AWTContainer() {
        this("Container", AWTPlatform.instance());
    }

    @Override
    protected N createNative() {
        return (N) new NAContainer();
    }
    
}
