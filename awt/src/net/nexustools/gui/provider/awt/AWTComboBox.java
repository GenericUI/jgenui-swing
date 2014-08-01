/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nexustools.gui.provider.awt;

import java.awt.Choice;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import net.nexustools.concurrent.PropAccessor;
import net.nexustools.gui.ComboBox;
import net.nexustools.gui.SingleInput;
import net.nexustools.event.EventDispatcher;
import net.nexustools.event.ValueListener;
import net.nexustools.gui.provider.awt.impl.AWTWidgetImpl;
import net.nexustools.gui.provider.awt.impl.ListenerProp;
import net.nexustools.gui.provider.awt.impl.PropDispatcher;

/**
 *
 * @author katelyn
 * @param <I>
 */
public class AWTComboBox<I> extends AWTWidgetImpl<Choice> implements ComboBox<I> {

    private I[] options;
    public AWTComboBox() {
        super(AWTPlatform.instance());
    }
    public AWTComboBox(I[] options) {
        this();
        setOptions(options);
    }

    @Override
    protected Choice create() {
        return new Choice() {
            {
                setName("ComboBox");
            }
            @Override
            public void paint(Graphics g) {
                if (!customRender(g)) {
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
                return false;
            }
        });
    }

    @Override
    public void setEditable(final boolean editable) {
        act(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("AWT does not support editable ComboBox's, however retro-fitting is planned.");
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
                AWTComboBox.this.options = options;
                component.removeAll();
                for(I option : options)
                    component.add(option.toString());
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
                int i=0;
                for(; i<options.length; i++) {
                    if(options[i] == value)
                        break;
                }
                
                component.select(i);
            }
        });
    }

    private final ListenerProp<ItemListener> itemListener = new ListenerProp<ItemListener>() {
        @Override
        public void connect(final PropAccessor<ItemListener> itemListener) {
            AWTComboBox.this.act(new Runnable() {
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
            AWTComboBox.this.act(new Runnable() {
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
