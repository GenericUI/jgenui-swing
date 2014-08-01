/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import net.nexustools.gui.provider.awt.impl.AWTWidgetImpl;
import net.nexustools.gui.Label;
import java.awt.Graphics;

/**
 *
 * @author katelyn
 */
public class AWTLabel extends AWTWidgetImpl<java.awt.Label> implements Label {

    public AWTLabel() {
        this(AWTPlatform.instance());
    }
    public AWTLabel(String text) {
        this();
        setText(text);
    }
    AWTLabel(Label other, AWTPlatform platform) {
        this();
        inherit(other);
    }
    AWTLabel(AWTPlatform platform) {
        super(platform);
    }

    @Override
    protected java.awt.Label create() {
        return new java.awt.Label() {
            {
                setName("Label");
            }
            @Override
            public void paint(Graphics g) {
                if(!customRender(g))
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
