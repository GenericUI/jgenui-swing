/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import net.nexustools.gui.provider.awt.AWTPlatform;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.JProgressBar;
import net.nexustools.concurrent.Prop;
import net.nexustools.gui.ProgressBar;
import net.nexustools.gui.provider.awt.impl.AWTWidgetImpl;
import net.nexustools.gui.render.Pixmap;
import net.nexustools.io.Stream;

/**
 *
 * @author katelyn
 */
public class SwingProgressBar extends AWTWidgetImpl<JProgressBar> implements ProgressBar {
    
    private class Native extends JProgressBar {
        public Native() {
            setName("ProgressBar");
        }
        @Override
        public void paint(Graphics g) {
            if(!customRender((Graphics2D)g))
                super.paint(g);
        }
    }

    protected final Prop<Pixmap> pixmap = new Prop();
    public SwingProgressBar() {
        super(AWTPlatform.instance());
    }
    
    public SwingProgressBar(String source) throws IOException, URISyntaxException {
        this(Stream.open(source));
    }
    
    public SwingProgressBar(Stream source) {
        this();
    }

    @Override
    protected JProgressBar create() {
        return new Native();
    }

    public int value() {
        return read(new Reader<Integer>() {
            @Override
            public Integer read() {
                return component.getValue();
            }
        });
    }

    public void setValue(final int value) {
        act(new Runnable() {
            public void run() {
                component.setValue(value);
            }
        });
    }

    public int max() {
        return read(new Reader<Integer>() {
            @Override
            public Integer read() {
                return component.getMaximum();
            }
        });
    }

    public void setMax(final int max) {
        act(new Runnable() {
            public void run() {
                component.setMaximum(max);
            }
        });
    }

    public int min() {
        return read(new Reader<Integer>() {
            @Override
            public Integer read() {
                return component.getMinimum();
            }
        });
    }

    public void setMin(final int min) {
        act(new Runnable() {
            public void run() {
                component.setMinimum(min);
            }
        });
    }
    
}
