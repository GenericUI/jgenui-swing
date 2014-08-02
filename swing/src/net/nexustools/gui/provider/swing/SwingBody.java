/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import javax.swing.JFrame;
import net.nexustools.gui.geom.Point;
import net.nexustools.gui.geom.Shape;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.layout.SizeConstraints;
import net.nexustools.gui.provider.awt.AWTContainer;
import net.nexustools.gui.provider.awt.AWTWidget;
import net.nexustools.gui.wrap.NativeBody;
import net.nexustools.gui.wrap.NativeWidget;
import net.nexustools.gui.wrap.WrappedBody;
import net.nexustools.gui.wrap.WrappedWidget;
import net.nexustools.utils.Creator;

/**
 *
 * @author katelyn
 */
public class SwingBody extends AWTContainer<JFrame> implements NativeBody {

    public static Creator<NativeBody, WrappedBody> CREATOR = new Creator<NativeBody, WrappedBody>() {
        public NativeBody create(WrappedBody body) {
            return new SwingBody(body);
        }
    };
    
    public SwingBody(WrappedWidget wrap) {
        super(new JFrame(), wrap);
    }

    public void nativeSetTitle(String title) {
        _c().setTitle(title);
    }
    
}
