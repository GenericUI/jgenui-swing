/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import net.nexustools.gui.Frame;
import net.nexustools.gui.layout.Layout;
import net.nexustools.gui.provider.awt.AWTPlatform;

/**
 *
 * @author katelyn
 */
public class SwingFrame extends SwingContainer implements Frame {

    private String title;
    private boolean raised;
    SwingFrame(AWTPlatform platform) {
        super(platform);
    }
    public SwingFrame(String title) {
        super();
        setTitle(title);
    }
    public SwingFrame(String title, Layout layout) {
        this(title);
        setLayout(layout);
    }

    @Override
    public String title() {
        return read(new Reader<String>() {
            @Override
            public String read() {
                return title;
            }
        });
    }

    @Override
    public void setTitle(final String title) {
        act(new Runnable() {
            @Override
            public void run() {
                SwingFrame.this.title = title;
                updateBorder();
            }
        });
    }

    public void setRaised(final boolean on) {
        act(new Runnable() {
            @Override
            public void run() {
                raised = on;
                updateBorder();
            }
        });
    }
    
    protected void updateBorder() {
        Border borderStyle = BorderFactory.createEtchedBorder(raised ? EtchedBorder.RAISED : EtchedBorder.LOWERED);
        component.setBorder(BorderFactory.createTitledBorder(borderStyle, title));
    }
    
}
