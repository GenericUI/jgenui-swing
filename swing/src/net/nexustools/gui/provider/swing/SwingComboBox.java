/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nexustools.gui.provider.swing;

import net.nexustools.gui.provider.awt.AWTPlatform;
import net.nexustools.gui.provider.awt.AWTComboBox;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import net.nexustools.concurrent.PropAccessor;
import net.nexustools.gui.ComboBox;
import net.nexustools.gui.SingleInput;
import net.nexustools.event.EventDispatcher;
import net.nexustools.event.ValueListener;
import net.nexustools.gui.provider.awt.impl.ListenerProp;
import net.nexustools.gui.provider.awt.impl.PropDispatcher;
import net.nexustools.gui.provider.awt.impl.AWTWidgetImpl;

/**
 *
 * @author katelyn
 * @param <I>
 */
public class SwingComboBox<I> extends AWTWidgetImpl<JComboBox> implements ComboBox<I> {

    private I[] options;
    public SwingComboBox() {
        super(AWTPlatform.instance());
    }
    public SwingComboBox(I[] options) {
        this();
        setOptions(options);
    }

    @Override
    protected JComboBox create() {
        return new JComboBox() {
            {
                setName("ComboBox");
            }
            @Override
            public void paint(Graphics g) {
                if (!customRender((Graphics2D) g)) {
                    super.paint(g);
                }
            }
        };
    }

    @Override
    public boolean isEditable() {
        return read(new Reader<Boolean>() {
            @Override
            public Boolean read() {
                return component.isEditable();
            }
        });
    }

    @Override
    public void setEditable(final boolean editable) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setEditable(editable);
            }
        });
    }

    @Override
    public I[] options() {
        return read(new Reader<I[]>() {
            @Override
            public I[] read() {
                return options;
            }
        });
    }

    @Override
    public void setOptions(final I... options) {
        act(new Runnable() {
            @Override
            public void run() {
                SwingComboBox.this.options = options;
                component.setModel(new DefaultComboBoxModel<I>(options));
            }
        });
    }

    @Override
    public I value() {
        return read(new Reader<I>() {
            @Override
            public I read() {
                return (I) component.getSelectedItem();
            }
        });
    }

    @Override
    public void setValue(final I value) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setSelectedItem(value);
            }
        });
    }

    private final ListenerProp<ItemListener> itemListener = new ListenerProp<ItemListener>() {
        @Override
        public void connect(final PropAccessor<ItemListener> itemListener) {
            SwingComboBox.this.act(new Runnable() {
                @Override
                public void run() {
                    ItemListener eventListener = new ItemListener() {
                        private int index = component.getSelectedIndex();

                        @Override
                        public void itemStateChanged(final ItemEvent e) {
                            if (index == component.getSelectedIndex()) {
                                return;
                            }

                            index = component.getSelectedIndex();
                            valueDispatcher.dispatch(new EventDispatcher.Processor<ValueListener, ValueListener.ValueEvent>() {
                                @Override
                                public ValueListener.ValueEvent create() {
                                    return new ValueListener.ValueEvent(component, component.getSelectedItem());
                                }

                                @Override
                                public void dispatch(ValueListener listener, ValueListener.ValueEvent event) {
                                    listener.valueChanged(event);
                                }
                            });
                        }
                    };

                    component.addItemListener(eventListener);
                    itemListener.set(eventListener);
                }
            });

        }

        @Override
        public void disconnect(final PropAccessor<ItemListener> itemListener) {
            SwingComboBox.this.act(new Runnable() {
                @Override
                public void run() {
                    component.removeItemListener(itemListener.take());
                }
            });
        }
    };
    public final PropDispatcher<ValueListener, ValueListener.ValueEvent> valueDispatcher = new PropDispatcher(itemListener, platform());

    @Override
    public String template() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTemplate(String template) {}

    public void addValueListener(ValueListener<I, SingleInput<I>> valueListener) {
        valueDispatcher.add(valueListener);
    }

    public void removeValueListener(ValueListener<I, SingleInput<I>> valueListener) {
        valueDispatcher.remove(valueListener);
    }
    
    

}
