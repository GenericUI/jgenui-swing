/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import net.nexustools.gui.provider.awt.AWTPlatform;
import net.nexustools.gui.provider.awt.impl.AWTWidgetImpl;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;
import net.nexustools.gui.Label;

/**
 *
 * @author katelyn
 */
public class SwingLabel extends AWTWidgetImpl<JLabel> implements Label {

    public SwingLabel() {
        this(AWTPlatform.instance());
    }
    public SwingLabel(String text) {
        this();
        setText(text);
    }
    SwingLabel(Label other, AWTPlatform platform) {
        this();
        inherit(other);
    }
    SwingLabel(AWTPlatform platform) {
        super(platform);
    }

    @Override
    protected JLabel create() {
        return new JLabel() {
            {
                setName("Label");
            }
            @Override
            public void paint(Graphics g) {
                if(!customRender((Graphics2D)g))
                    super.paint(g);
            }
        };
    }

    @Override
    public String text() {
        return component.getText();
    }

    @Override
    public void setText(String text) {
        component.setText(text);
    }
    
}
