/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Dimension;
import net.nexustools.gui.Canvas;
import net.nexustools.gui.provider.awt.AWTPlatform;
import net.nexustools.gui.provider.awt.impl.AWTWidgetImpl;
import net.nexustools.gui.render.Renderer;

/**
 *
 * @author katelyn
 */
public class AWTCanvas extends AWTWidgetImpl<java.awt.Canvas> implements Canvas{

    AWTCanvas(AWTPlatform platform) {
        super(platform);
    }
    public AWTCanvas() {
        this(AWTPlatform.instance());
    }

    @Override
    protected java.awt.Canvas create() {
        return new java.awt.Canvas() {
            {
                setName("Canvas");
                setMinimumSize(new Dimension(50, 50));
                setPreferredSize(new Dimension(150, 150));
            }
            
        };
    }

    public Renderer overlayRenderer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setOverlayRenderer(Renderer renderer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
