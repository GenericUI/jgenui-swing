/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.examples;

import java.io.IOException;
import java.net.URISyntaxException;
import net.nexustools.gui.event.ActionListener;
import net.nexustools.gui.impl.Body;
import net.nexustools.gui.provider.swing.SwingAppDelegate;
import net.nexustools.gui.provider.swing.SwingButton;


/**
 *
 * @author katelyn
 */
public class WidgetFactory extends SwingAppDelegate {

    private WidgetFactory(String[] args) {
        super(args, "Swing Widget Factory", "GenericUI Examples");
    }
    
    public static void main(String[] args) throws IOException, URISyntaxException {
        new WidgetFactory(args);
    }

    @Override
    protected void populate(String[] args, Body body) {
        final SwingButton button = new SwingButton();
        button.setText("Testing Button");
        button.addActionListener(new ActionListener() {
            int count = 1;
            @Override
            public void activated(ActionListener.ActionEvent event) {
                button.setText("Click #" + (count++));
            }
        });
        body.add(button);
    }
    
}
