/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Frame;
import net.nexustools.gui.impl.Action;
import net.nexustools.gui.provider.awt.AWTBody.NABody;
import net.nexustools.gui.provider.awt.AWTContainer.NAContainer;
import net.nexustools.gui.provider.awt.AWTWindow.NAWindow;
import net.nexustools.gui.wrap.WBody;
import net.nexustools.gui.wrap.WPlatform;
import net.nexustools.gui.wrap.impl.NBody;

/**
 *
 * @author katelyn
 */
public class AWTBody<N extends NABody> extends WBody<N> {

    public AWTBody() {
        this("Body", AWTPlatform.instance());
    }
    protected AWTBody(String tag, WPlatform platform) {
        super(tag, platform);
    }
    
    public static class NABody<C extends Frame, WW extends WBody> extends NAWindow<C, WW> implements NBody<WW> {

        protected NABody(C component) {
            super(component);
        }
        protected NABody() {
            this((C)new Frame());
        }

        public void nativeAttachMenu() {
        }

        public void nativeSetTitle(String title) {
            component.setTitle(title);
        }
        
    }

    public void insertAction(Action action, Action after) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void clearActions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected N createNative() {
        return (N)new NABody();
    }
    
}
