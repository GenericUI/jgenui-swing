/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.jgenui.examples;

import net.nexustools.gui.Body;
import net.nexustools.gui.Button;
import net.nexustools.gui.Container;
import net.nexustools.gui.Label;
import net.nexustools.gui.event.ActionListener;
import net.nexustools.gui.layout.BoxLayout;
import net.nexustools.gui.provider.awt.AWTPlatform;
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
        //body.add(label);
        Container container = SwingPlatform.instance().create(Container.class);
        container.setLayout(BoxLayout.Horizontal);
        Button button = SwingPlatform.instance().create(Button.class);
        button.addActionListener(new ActionListener() {
            @Override
            public void activated(ActionListener.ActionEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        button.setText("Swing");
        container.add(button);
        button = AWTPlatform.instance().create(Button.class);
        button.addActionListener(new ActionListener() {
            @Override
            public void activated(ActionListener.ActionEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        button.setText("AWT");
        body.add(button);
        //container.add(button);
        //body.add(container);
        body.show();
    }
    
}
