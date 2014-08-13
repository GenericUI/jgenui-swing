/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Frame;
import net.nexustools.gui.impl.Action;
import net.nexustools.gui.provider.awt.AWTBody.NABody;
import net.nexustools.gui.provider.awt.AWTWindow.NAWindow;
import net.nexustools.gui.wrap.WBody;
import net.nexustools.gui.wrap.WPlatform;
import net.nexustools.gui.wrap.impl.NBody;
import net.nexustools.gui.wrap.impl.NWidget;

/**
 *
 * @author katelyn
 */
public class AWTBody<N extends NABody> extends WBody<N> {

    public AWTBody() {
        this(AWTPlatform.instance());
    }
    protected AWTBody(AWTPlatform platform) {
        this("Body", platform);
    }
    protected AWTBody(String tag, AWTPlatform platform) {
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

        public void nativeSetGlassWidget(NWidget glassWidget) {}
        public void nativeSetTitleWidget(NWidget titleWidget) {}

        public void nativeSetMainWidget(NWidget mainWidget) {
        }
        
    }

    @Override
    protected N createNative() {
        return (N)new NABody();
    }
    
}
