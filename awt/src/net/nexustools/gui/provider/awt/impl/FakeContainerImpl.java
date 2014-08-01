/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt.impl;

import java.awt.Graphics;
import net.nexustools.gui.provider.awt.AWTPlatform;
import net.nexustools.gui.provider.awt.impl.FakeContainerImpl.FakeContainer;

/**
 *
 * @author katelyn
 */
public abstract class FakeContainerImpl extends AbstractContainerImpl<FakeContainer> {
    
    protected class FakeContainer extends java.awt.Container {
        @Override
        public void paint(Graphics g) {
            if(!customRender(g))
                super.paint(g);
        }
    }

    public FakeContainerImpl(AWTPlatform platform) {
        super(platform);
    }

    @Override
    protected FakeContainer create() {
        return new FakeContainer();
    }
    
}
