/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Dimension;
import java.awt.Graphics;
import net.nexustools.gui.Container;
import net.nexustools.gui.layout.Layout;
import net.nexustools.gui.platform.WidgetPeer;
import net.nexustools.gui.provider.awt.impl.ContainerImpl;

/**
 *
 * @author katelyn
 */
public class AWTContainer extends ContainerImpl<java.awt.Container> implements net.nexustools.gui.Container {

    public class NativeContainer extends java.awt.Container implements WidgetPeer<Container> {

        public NativeContainer() { // For consistency, buttons can expand infinitely by default
            setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
            setName("Container");
            setLayout(null);
        }
        
        @Override
        public void paint(Graphics g) {
            if(!customRender(g))
                super.paint(g);
        }
        
        @Override
        public Container genUI() {
            return AWTContainer.this;
        }
        
    }
    
    AWTContainer(AWTPlatform platform) {
        super(platform);
    }
    public AWTContainer() {
        this(AWTPlatform.instance());
    }
    public AWTContainer(Layout layout) {
        this();
        setLayout(layout);
    }

    @Override
    protected java.awt.Container create() {
        return new NativeContainer();
    }

}
