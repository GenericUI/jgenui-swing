/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nexustools.gui.provider.awt;

import java.util.Arrays;
import net.nexustools.gui.MultiList;
import net.nexustools.event.SelectionListener;
import net.nexustools.gui.provider.awt.impl.AWTWidgetImpl;

/**
 *
 * @author katelyn
 */
public class AWTMultiList<I> extends AWTWidgetImpl<java.awt.List> implements MultiList<I> {

    private I[] options;
    public AWTMultiList() {
        super(AWTPlatform.instance());
    }

    AWTMultiList(AWTPlatform platform) {
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
                int i = 0;
                for(; i < options.length; i++)
                    if(options[i] == value)
                        break;
                
                component.select(i);
            }
        });
    }

    @Override
    public I value() {
        return read(new Reader<I>() {
            @Override
            public I read() {
                int index = component.getSelectedIndex();
                if(index >= 0)
                    return options[component.getSelectedIndex()];
                return null;
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
                component.removeAll();
                for(I option : options)
                    component.add(option.toString());
            }
        });

    }

    @Override
    public I[] selected() {
        return read(new Reader<I[]>() {

            @Override
            public I[] read() {
                int[] indices = component.getSelectedIndexes();
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
                return component.isMultipleMode();
            }
        });
    }

    public void allowMultiple(final boolean yes) {
        act(new Runnable() {
            public void run() {
                component.setMultipleMode(yes);
            }
        });
    }

    @Override
    protected java.awt.List create() {
        return new java.awt.List();
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
                for(int i=0; i<options.length; i++) {
                    boolean select = false;
                    for(int index : indices)
                        if(index == i) {
                            select = true;
                            break;
                        }
                    if(select)
                        component.select(i);
                    else
                        component.deselect(i);
                }
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
                return component.getSelectedIndexes();
            }
        });
    }

}
