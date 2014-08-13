/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import javax.swing.JFrame;
import net.nexustools.gui.provider.awt.AWTBody.NABody;
import net.nexustools.gui.provider.awt.AWTWindow;
import net.nexustools.gui.provider.swing.SwingBody.NSBody;
import net.nexustools.gui.wrap.WBody;
import net.nexustools.gui.wrap.impl.NBody;
import net.nexustools.gui.wrap.impl.NWidget;

/**
 *
 * @author katelyn
 */
public class SwingBody<N extends NSBody> extends WBody<N> {
    
    public static class NSBody<C extends JFrame, WW extends WBody> extends AWTWindow.NAWindow<C, WW> implements NBody<WW> {

        protected NSBody(C component) {
            super(component);
        }
        protected NSBody() {
            this((C)new JFrame());
        }

        public void nativeAttachMenu() {
        }

        public void nativeSetTitle(String title) {
            component.setTitle(title);
        }

        public void nativeSetGlassWidget(NWidget glassWidget) {
            component.setGlassPane(((NABody)glassWidget)._c());
        }
        public void nativeSetTitleWidget(NWidget titleWidget) {}

        public void nativeSetMainWidget(NWidget mainWidget) {}
        
    }

    protected SwingBody(String tag, SwingPlatform platform) {
        super(tag, platform);
    }

    protected SwingBody(SwingPlatform platform) {
        this("Body", platform);
    }

    public SwingBody() {
        this(SwingPlatform.instance());
    }

    @Override
    protected N createNative() {
        return (N) new NSBody();
    }
    
}
