/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.jgenui.examples;

import net.nexustools.gui.Body;
import net.nexustools.gui.Label;
import net.nexustools.gui.layout.BoxLayout;
import net.nexustools.gui.provider.swing.SwingPlatform;


/**
 *
 * @author katelyn
 */
public class WidgetFactory {
    
    public static final String[] stargateNames = new String[]{"Teal'c", "Daniel", "Jack", "Samantha"};
    
    public static void main(String[] args) {
        Body body = SwingPlatform.instance().create(Body.class);
        body.setTitle("Select Platform");
        body.setLayout(BoxLayout.Vertical);
        Label label = SwingPlatform.instance().create(Label.class);
        label.setText("Select a Platform Below");
        body.add(label);
        body.show();
    }
    
}
