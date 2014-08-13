/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.examples;

//import net.nexustools.gui.impl.Body;

import java.io.IOException;
import java.net.URISyntaxException;
import net.nexustools.ApplicationDelegate;
import net.nexustools.gui.provider.awt.AWTBody;
import net.nexustools.gui.provider.awt.AWTButton;


/**
 *
 * @author katelyn
 */
public class WidgetFactory {
    
    public static void main(String[] args) throws IOException, URISyntaxException {
        ApplicationDelegate.init("Pixel Galaxy", "NexusTools");
        
        AWTBody body = new AWTBody();
        body.setTitle("Test");
        
        AWTButton button = new AWTButton();
        button.setText("Testing Button");
        body.add(button);
        
        body.setVisible(true);
    }
    
}
