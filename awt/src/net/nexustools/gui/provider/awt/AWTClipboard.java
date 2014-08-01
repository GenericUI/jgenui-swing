/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import net.nexustools.event.ValueListener;
import net.nexustools.gui.platform.Clipboard;
import net.nexustools.gui.platform.MimeStorage;

/**
 *
 * @author katelyn
 */
public class AWTClipboard implements Clipboard {

    private static final AWTClipboard clipboard = new AWTClipboard();
    public static AWTClipboard instance() {
        return clipboard;
    }

    public MimeStorage content() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setContent(MimeStorage data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String string() {
        return content().string();
    }
    
    public double number() {
        return content().number();
    }

    public void addListener(ValueListener<MimeStorage, Clipboard> valueListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void removeListener(ValueListener<MimeStorage, Clipboard> valueListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setString(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setNumber(double number) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Object get(String mime) {
        return content().get(mime);
    }

    public void put(String mime, Object data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
