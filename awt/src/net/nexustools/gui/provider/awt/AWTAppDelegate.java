/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import static net.nexustools.Application.defaultName;
import static net.nexustools.Application.defaultOrganization;
import net.nexustools.gui.wrap.WAppDelegate;

/**
 *
 * @author kate
 */
public abstract class AWTAppDelegate<B extends AWTBody, P extends AWTPlatform> extends WAppDelegate<B, P> {
    
    protected AWTAppDelegate(String[] args, P platform) {
        this(args, defaultName(), defaultOrganization(), platform);
    }
    protected AWTAppDelegate(String[] args, String name, String organization) {
        this(args, name, organization, (P)AWTPlatform.instance());
    }
    protected AWTAppDelegate(String[] args, String name, String organization, P platform) {
        super(args, name, organization, platform);
    }

    @Override
    public void mainLoop(String[] args) {
        // TODO: Wait until the application is finished
    }

    @Override
    public boolean needsMainLoop() {
        return false;
    }
    
}
