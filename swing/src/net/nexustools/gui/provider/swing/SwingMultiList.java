/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nexustools.gui.provider.swing;

import net.nexustools.gui.provider.awt.AWTPlatform;
import java.util.Arrays;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import net.nexustools.gui.MultiList;
import net.nexustools.event.SelectionListener;
import net.nexustools.gui.provider.swing.impl.ScrollDeligateImpl;

/**
 *
 * @author katelyn
 */
public class SwingMultiList<I> extends ScrollDeligateImpl<JList<I>> implements MultiList<I> {

    private I[] options;
    public SwingMultiList() {
        super(AWTPlatform.instance());
    }

    SwingMultiList(AWTPlatform platform) {
        super(platform);
    }

    @Override
    public String template() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTemplate(String template) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValue(final I value) {
        act(new Runnable() {
            public void run() {
                component.view.setSelectedValue(value, true);
            }
        });
    }

    @Override
    public I value() {
        return read(new Reader<I>() {
            @Override
            public I read() {
                return component.view.getSelectedValue();
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

            public void run() {
                component.view.setModel(new DefaultListModel<I>() {
                    {
                        for (I option : options) {
                            addElement(option);
                        }
                    }
                });
            }
        });

    }

    @Override
    public I[] selected() {
        return read(new Reader<I[]>() {

            @Override
            public I[] read() {
                int[] indices = component.view.getSelectedIndices();
                I[] selected = Arrays.copyOf(options, indices.length);
                for(int i=0; i<indices.length; i++)
                    selected[i] = options[indices[i]];
                return selected;
            }
        });
    }

    @Override
    public void addSelectionListener(SelectionListener<I, MultiList> selectionListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeSelectionListener(SelectionListener<I, MultiList> selectionListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean isAllowingMultiple() {
        return read(new Reader<Boolean>() {

            @Override
            public Boolean read() {
                return component.view.getSelectionMode() != ListSelectionModel.SINGLE_SELECTION;
            }
        });
    }

    public void allowMultiple(final boolean yes) {
        act(new Runnable() {
            public void run() {
                component.view.setSelectionMode(yes ? ListSelectionModel.MULTIPLE_INTERVAL_SELECTION : ListSelectionModel.SINGLE_SELECTION);
            }
        });
    }

    @Override
    protected JList<I> createView() {
        return new JList<I>();
    }

    public void setSelectionRange(final int s, final int e) {
        int[] indices = new int[e-s+1];
        for(int i=s; i<=e; i++) {
            indices[i-s] = i;
        }
        setSelection(indices);
    }

    public void setSelection(final int... indices) {
        act(new Runnable() {
            public void run() {
                component.view.setSelectedIndices(indices);
            }
        });
    }

    public void setSelectionStart(final int start) {
        act(new Runnable() {
            public void run() {
                setSelectionRange(start, selectionEnd());
            }
        });
    }

    public void setSelectionEnd(final int end) {
        act(new Runnable() {
            public void run() {
                setSelectionRange(selectionStart(), end);
            }
        });
    }

    public int selectionEnd() {
        int[] selection = selection();
        if(selection.length < 1)
            return 0;
        int end = Integer.MIN_VALUE;
        for(int sel : selection)
            end = Math.min(sel, end);
        return end;
    }

    public int selectionStart() {
        int[] selection = selection();
        if(selection.length < 1)
            return 0;
        int start = Integer.MAX_VALUE;
        for(int sel : selection)
            start = Math.min(sel, start);
        return start;
    }

    public int[] selection() {
        return read(new Reader<int[]>() {
            @Override
            public int[] read() {
                return component.view.getSelectedIndices();
            }
        });
    }

}
