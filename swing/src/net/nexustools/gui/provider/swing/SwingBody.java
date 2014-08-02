/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Container;
import javax.swing.JFrame;
import net.nexustools.gui.provider.awt.AWTAbstractContainer;
import net.nexustools.gui.wrap.NativeBody;
import net.nexustools.gui.wrap.WrappedBody;
import net.nexustools.gui.wrap.WrappedWidget;
import net.nexustools.utils.Creator;

/**
 *
 * @author katelyn
 */
public class SwingBody extends AWTAbstractContainer<JFrame> implements NativeBody {

    public static Creator<NativeBody, WrappedBody> CREATOR = new Creator<NativeBody, WrappedBody>() {
        public NativeBody create(WrappedBody body) {
            return new SwingBody(body);
        }
    };
    
    public SwingBody(WrappedWidget wrap) {
        super(new JFrame() {
            {
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setLayout(null);
            }
        }, wrap);
    }

    public void nativeSetTitle(String title) {
        _c().setTitle(title);
    }

    @Override
    public Container _t() {
        return _c().getContentPane();
    }
    
}
