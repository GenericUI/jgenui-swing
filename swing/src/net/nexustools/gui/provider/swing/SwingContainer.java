/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import net.nexustools.gui.Container;
import net.nexustools.gui.layout.Layout;
import net.nexustools.gui.platform.WidgetPeer;
import net.nexustools.gui.provider.awt.AWTPlatform;
import net.nexustools.gui.provider.awt.impl.ContainerImpl;

/**
 *
 * @author katelyn
 */
public class SwingContainer extends ContainerImpl<JPanel> implements net.nexustools.gui.Container {

    public class NativeContainer extends JPanel implements WidgetPeer<Container> {

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
            return SwingContainer.this;
        }
        
    }
    
    SwingContainer(AWTPlatform platform) {
        super(platform);
    }
    public SwingContainer() {
        this(AWTPlatform.instance());
    }
    public SwingContainer(Layout layout) {
        this();
        setLayout(layout);
    }

    @Override
    protected JPanel create() {
        return new NativeContainer();
    }

}
