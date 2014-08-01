/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.jgenui.examples;

import net.nexustools.gui.SingleInput;
import net.nexustools.event.ValueListener;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.layout.BoxLayout;
import net.nexustools.gui.provider.swing.SwingBody;
import net.nexustools.gui.provider.swing.SwingCheckBox;
import net.nexustools.gui.provider.swing.SwingComboBox;
import net.nexustools.gui.provider.swing.SwingContainer;
import net.nexustools.gui.provider.swing.SwingFrame;
import net.nexustools.gui.provider.swing.SwingLabel;
import net.nexustools.gui.provider.swing.SwingOnOffButton;
import net.nexustools.gui.provider.swing.SwingProgressBar;
import net.nexustools.gui.provider.swing.SwingRadioButton;
import net.nexustools.gui.provider.swing.SwingRangeInput;
import net.nexustools.gui.provider.swing.SwingTabWidget;
import net.nexustools.gui.provider.swing.SwingTextInput;
import net.nexustools.gui.provider.swing.SwingToggleButton;

/**
 *
 * @author katelyn
 */
public class SwingThemeTester extends SwingBody {
    
    public static final String[] stargateNames = new String[]{"Teal'c", "Daniel", "Jack", "Samantha"};
    
    public static void main(String[] args) {
        (new SwingThemeTester()).show();
    }

    public SwingThemeTester() {
        super("Swing Theme Tester");
        setLayout(BoxLayout.Vertical);
        
        SwingContainer columns = new SwingContainer(BoxLayout.Horizontal);
        // Start First Column
        SwingContainer column = new SwingFrame("Fields and Toggles", BoxLayout.Vertical);
        SwingComboBox<String> comboBox = new SwingComboBox();
        comboBox.setOptions(stargateNames);
        comboBox.setEditable(true);
        column.add(comboBox);
        comboBox = new SwingComboBox();
        comboBox.setOptions(stargateNames);
        comboBox.setEditable(true);
        comboBox.setEnabled(false);
        column.add(comboBox);
        
        SwingTextInput swingLineInput = new SwingTextInput();
        column.add(swingLineInput);
        swingLineInput = new SwingTextInput();
        swingLineInput.setEnabled(false);
        column.add(swingLineInput);
        
        SwingContainer horContainer = new SwingContainer(BoxLayout.Horizontal);
        SwingLabel label = new SwingLabel("Label");
        horContainer.add(label);
        label = new SwingLabel("Label");
        label.setEnabled(false);
        horContainer.add(label);
        
        SwingRangeInput swingRangeInput = new SwingRangeInput();
        horContainer.add(swingRangeInput);
        swingRangeInput = new SwingRangeInput();
        swingRangeInput.setEnabled(false);
        horContainer.add(swingRangeInput);
        column.add(horContainer);
        
        horContainer = new SwingContainer(BoxLayout.Horizontal);
        SwingCheckBox swingCheckBox = new SwingCheckBox("checkbox");
        horContainer.add(swingCheckBox);
        SwingRadioButton swingRadioButton = new SwingRadioButton("radiobutton");
        horContainer.add(swingRadioButton);
        column.add(horContainer);
        
        horContainer = new SwingContainer(BoxLayout.Horizontal);
        swingCheckBox = new SwingCheckBox("checkbox");
        swingCheckBox.setSelected(true);
        horContainer.add(swingCheckBox);
        swingRadioButton = new SwingRadioButton("radiobutton");
        swingRadioButton.setSelected(true);
        horContainer.add(swingRadioButton);
        column.add(horContainer);
        
        horContainer = new SwingContainer(BoxLayout.Horizontal);
        swingCheckBox = new SwingCheckBox("checkbox");
        horContainer.add(swingCheckBox);
        swingRadioButton = new SwingRadioButton("radiobutton");
        horContainer.add(swingRadioButton);
        horContainer.setEnabled(false);
        column.add(horContainer);
        
        horContainer = new SwingContainer(BoxLayout.Horizontal);
        swingCheckBox = new SwingCheckBox("checkbox");
        swingCheckBox.setSelected(true);
        horContainer.add(swingCheckBox);
        swingRadioButton = new SwingRadioButton("radiobutton");
        swingRadioButton.setSelected(true);
        horContainer.add(swingRadioButton);
        horContainer.setEnabled(false);
        column.add(horContainer);
        
        horContainer = new SwingContainer(BoxLayout.Horizontal);
        SwingOnOffButton onOffButton = new SwingOnOffButton();
        horContainer.add(onOffButton);
        onOffButton = new SwingOnOffButton();
        onOffButton.setEnabled(false);
        horContainer.add(onOffButton);
        column.add(horContainer);
        columns.add(column);
        // End First Column
        
        // Start Second Column
        column = new SwingFrame("Buttons and Selectors", BoxLayout.Vertical);
        SwingToggleButton toggleButton = new SwingToggleButton("togglebutton");
        column.add(toggleButton);
        toggleButton = new SwingToggleButton("togglebutton");
        toggleButton.setEnabled(false);
        column.add(toggleButton);
        toggleButton = new SwingToggleButton("togglebutton");
        toggleButton.setSelected(true);
        column.add(toggleButton);
        toggleButton = new SwingToggleButton("togglebutton");
        toggleButton.setSelected(true);
        toggleButton.setEnabled(false);
        column.add(toggleButton);
        comboBox = new SwingComboBox(platform().LAFs());
        comboBox.setTemplate("LAF: ####");
        comboBox.addValueListener(new ValueListener<String, SingleInput<String>>() {
            @Override
            public void valueChanged(ValueListener.ValueEvent<String, SingleInput<String>> event) {
                platform().setLAF(event.value);
            }
        });
        comboBox.setValue(platform().LAF());
        column.add(comboBox);
        comboBox = new SwingComboBox(platform().LAFs());
        comboBox.setTemplate("LAF: ####");
        comboBox.setValue(platform().LAF());
        comboBox.setEnabled(false);
        column.add(comboBox);
        columns.add(column);
        // End Second Column
        
        // Start Third Column
        column = new SwingFrame("Frame Borders", BoxLayout.Vertical);
        column.setMinimumSize(new Size(400, 200));
        SwingFrame frameBorder = new SwingFrame("Raised");
        frameBorder.setRaised(true);
        column.add(frameBorder);
        frameBorder = new SwingFrame("Lowered");
        frameBorder.setRaised(false);
        column.add(frameBorder);
        columns.add(column);
        // End Second Column
        
//        // Start Third Column
//        column = new SwingFrame("MultiLists", BoxLayout.Vertical);
//        SwingMultiList<String> swingList = new SwingMultiList();
//        swingList.setOptions(new String[]{"Option 1", "Option 2", "Option 3", "Option 4"});
//        column.add(swingList);
//        swingList = new SwingMultiList();
//        swingList.setOptions(new String[]{"Option 1", "Option 2", "Option 3", "Option 4"});
//        swingList.setSelectionRange(0, 1);
//        swingList.allowMultiple(true);
//        column.add(swingList);
//        swingList = new SwingMultiList();
//        swingList.setOptions(new String[]{"Option 1", "Option 2", "Option 3", "Option 4"});
//        swingList.setEnabled(false);
//        column.add(swingList);
//        columns.add(column);
//        // End Third Column
//        
//        // Start Fourth Column
//        column = new SwingFrame("Tables and TextAreas", BoxLayout.Vertical);
//        SwingTextArea textArea = new SwingTextArea("Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
//"Nullam fringilla, est ut feugiat ultrices, elit lacus ultricies nibh, id commodo tortor nisi id elit.\n" +
//"Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.\n" +
//"Morbi vel elit erat. Maecenas dignissim, dui et pharetra rutrum, tellus lectus rutrum mi, a convallis libero nisi quis tellus.\n" +
//"Nulla facilisi. Nullam eleifend lobortis nisl, in porttitor tellus malesuada vitae.\n" +
//"Aenean lacus tellus, pellentesque quis molestie quis, fringilla in arcu.\n" +
//"Duis elementum, tellus sed tristique semper, metus metus accumsan augue, et porttitor augue orci a libero.\n" +
//"Ut sed justo ac felis placerat laoreet sed id sem. Proin mattis tincidunt odio vitae tristique.\n" +
//"Morbi massa libero, congue vitae scelerisque vel, ultricies vel nisl.\n" +
//"Vestibulum in tortor diam, quis aliquet quam. Praesent ut justo neque, tempus rutrum est.\n" +
//"Duis eu lectus quam. Vivamus eget metus a mauris molestie venenatis pulvinar eleifend nisi.\n" +
//"Nulla facilisi. Pellentesque at dolor sit amet purus dapibus pulvinar molestie quis neque.\n" +
//"Suspendisse feugiat quam quis dolor accumsan cursus. ");
//        column.add(textArea);
//        columns.add(column);
//        // End Fourth Column
        
        // Start Fifth Column
        column = new SwingFrame("ProgressBars and Indicators", BoxLayout.Vertical);
        SwingProgressBar swingProgressBar = new SwingProgressBar();
        swingProgressBar.setValue(50);
        swingProgressBar.setMax(100);
        column.add(swingProgressBar);
        columns.add(column);
        // End Fifth Column
        add(columns);
        
        // Last Block
        SwingFrame frame = new SwingFrame("Tabbed Widgets", BoxLayout.Horizontal);
        SwingTabWidget tabWidget = new SwingTabWidget();
        tabWidget.add(new SwingLabel("Test"), "Farmers");
        tabWidget.add(new SwingLabel("Test"), "Seekers");
        tabWidget.add(new SwingLabel("Test"), "Masters");
        tabWidget.setOrientation(SwingTabWidget.Orientation.Top);
        frame.add(tabWidget);
        
        tabWidget = new SwingTabWidget();
        tabWidget.add(new SwingLabel("Test"), "Farmers");
        tabWidget.add(new SwingLabel("Test"), "Seekers");
        tabWidget.add(new SwingLabel("Test"), "Masters");
        tabWidget.setOrientation(SwingTabWidget.Orientation.Left);
        frame.add(tabWidget);
        
        tabWidget = new SwingTabWidget();
        tabWidget.add(new SwingLabel("Test"), "Farmers");
        tabWidget.add(new SwingLabel("Test"), "Seekers");
        tabWidget.add(new SwingLabel("Test"), "Masters");
        tabWidget.setOrientation(SwingTabWidget.Orientation.Bottom);
        frame.add(tabWidget);
        
        tabWidget = new SwingTabWidget();
        tabWidget.add(new SwingLabel("Test"), "Farmers");
        tabWidget.add(new SwingLabel("Test"), "Seekers");
        tabWidget.add(new SwingLabel("Test"), "Masters");
        tabWidget.setOrientation(SwingTabWidget.Orientation.Right);
        frame.add(tabWidget);
        add(frame);
        // End Last Block
    }
    
}
