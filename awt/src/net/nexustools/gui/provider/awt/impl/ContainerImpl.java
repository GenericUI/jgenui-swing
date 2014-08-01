/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt.impl;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.util.Iterator;
import java.util.WeakHashMap;
import net.nexustools.event.DefaultEventDispatcher;
import net.nexustools.event.EventDispatcher;
import net.nexustools.gui.ContentHolder;
import net.nexustools.gui.LayoutContainer;
import net.nexustools.gui.Widget;
import net.nexustools.gui.event.LayoutListener;
import net.nexustools.gui.event.LayoutListener.LayoutEvent;
import net.nexustools.gui.geom.Point;
import net.nexustools.gui.geom.Rect;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.layout.Layout;
import net.nexustools.gui.layout.LayoutObject;
import net.nexustools.gui.platform.WidgetPeer;
import net.nexustools.gui.provider.awt.AWTPlatform;

/**
 *
 * @author katelyn
 */
public abstract class ContainerImpl<J extends Container> extends AbstractContainerImpl<J> {
    
    public static class NativeLayout implements LayoutManager2 {

        private Iterable<LayoutObject> layoutIterator(final Container parent) {
            return new Iterable<LayoutObject>() {
                final ContainerImpl containerImpl = (ContainerImpl)((WidgetPeer<net.nexustools.gui.Container>)parent).genUI();
                
                public Iterator<LayoutObject> iterator() {
                    return new Iterator<LayoutObject>() {
                        Iterator<Widget> it = containerImpl.iterator();
                        public boolean hasNext() {
                            return it.hasNext();
                        }
                        public LayoutObject next() {
                            return it.next();
                        }
                        public void remove() {}
                    };
                }
            };
        }
        
        public static class Cache {
            Size prefSize;
            Size minSize;
        }
        
        public final Layout layout;
        public final WeakHashMap<Component, Cache> cacheMap = new WeakHashMap();
        public NativeLayout(Layout layout) {
            this.layout = layout;
        }

        @Override
        public void addLayoutComponent(String name, Component comp) {}

        public void addLayoutComponent(Component comp, Object constraints) {}

        @Override
        public void removeLayoutComponent(Component comp) {}

        public Dimension maximumLayoutSize(Container target) {
            return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        public float getLayoutAlignmentX(Container target) {
            return 0;
        }

        public float getLayoutAlignmentY(Container target) {
            return 0;
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            Size size = calcPreferredLayoutSize(parent);
            Insets insets = parent.getInsets();
            return new Dimension((int)size.w+insets.left+insets.right, (int)size.h+insets.top+insets.bottom);
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            Size size = calcMinimumLayoutSize(parent);
            Insets insets = parent.getInsets();
            return new Dimension((int)size.w+insets.left+insets.right, (int)size.h+insets.top+insets.bottom);
        }

        public Size calcPreferredLayoutSize(Container parent) {
            Cache cache = cacheMap.get(parent);
            if(cache == null) {
                cache = new Cache();
                cacheMap.put(parent, cache);
            } else if(cache.prefSize != null)
                return cache.prefSize;
            
            try {
                Size size = layout.calculatePreferredSize(layoutIterator(parent));
                Insets insets = parent.getInsets();
                return /*cache.prefSize = */size;
            } catch(RuntimeException ex) {
                ex.printStackTrace();
                throw ex;
            }
        }

        public Size calcMinimumLayoutSize(Container parent) {
            Cache cache = cacheMap.get(parent);
            if(cache == null) {
                cache = new Cache();
                cacheMap.put(parent, cache);
            } else if(cache.minSize != null)
                return cache.minSize;
            
            try {
                Size size = layout.calculateMinimumSize(layoutIterator(parent));
                return /*cache.prefSize = */size;
            } catch(RuntimeException ex) {
                ex.printStackTrace();
                throw ex;
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            final Size minSize = calcMinimumLayoutSize(parent);
            final Size prefSize = calcPreferredLayoutSize(parent);
            final ContainerImpl containerImpl = (ContainerImpl)((WidgetPeer<net.nexustools.gui.Container>)parent).genUI();
            layout.performLayout(layoutIterator(parent), prefSize, containerImpl.contentSize(), containerImpl.childCount());
            containerImpl.layoutDispatcher.dispatch(new EventDispatcher.Processor<LayoutListener, LayoutEvent>() {
                @Override
                public LayoutEvent create() {
                    return new LayoutEvent(minSize, prefSize, (LayoutContainer)containerImpl);
                }
                @Override
                public void dispatch(LayoutListener listener, LayoutEvent event) {
                    listener.layoutFinished(event);
                }
            });
            Container par = parent.getParent();
            if(par.isValid())
                par.invalidate();
        }
        
        public void invalidateLayout(Container target) {
            cacheMap.remove(target);
        }
        
    }

    public ContainerImpl(AWTPlatform platform) {
        super(platform);
    }
    
    public final EventDispatcher<AWTPlatform, LayoutListener, LayoutEvent> layoutDispatcher = new DefaultEventDispatcher(platform());
    public void addLayoutListener(LayoutListener listener) {
        layoutDispatcher.add(listener);
    }
    public void removeLayoutListener(LayoutListener listener) {
        layoutDispatcher.remove(listener);
    }
    
    public int childCount() {
        return read(new Reader<Integer>() {
            @Override
            public Integer read() {
                return component.getComponentCount();
            }
        });
    }
    
    public Rect contentBounds() {
        return read(new Reader<Rect>() {

            @Override
            public Rect read() {
                Rect rect = bounds();
                Insets insets = component.getInsets();
                rect.topLeft.x += insets.left;
                rect.topLeft.y += insets.top;
                rect.size.w -= insets.left + insets.right;
                rect.size.h -= insets.top + insets.bottom;
                return rect;
            }
        });
    }
    
    public Point contentOffset() {
        return read(new Reader<Point>() {
            @Override
            public Point read() {
                Point pos = pos();
                Insets insets = component.getInsets();
                pos.x += insets.left;
                pos.y += insets.top;
                return pos;
            }
        });
    }
    
    public Size contentSize() {
        return read(new Reader<Size>() {
            @Override
            public Size read() {
                Size size = size();
                Insets insets = component.getInsets();
                size.w -= insets.left + insets.right;
                size.h -= insets.top + insets.bottom;
                return size;
            }
        });
                
    }
    
    public void setLayout(final Layout layout) {
        act(new Runnable() {
            @Override
            public void run() {
                if(layout != null) {
                    component.setLayout(new ContainerImpl.NativeLayout(layout));
                    invalidate();
                } else
                    component.setLayout(null);
                //ContainerImpl.this.layout = layout;
            }
        });
    }

    public Layout layout() {
        return read(new Reader<Layout>() {
            @Override
            public Layout read() {
                LayoutManager manager = component.getLayout();
                if(manager instanceof ContainerImpl.NativeLayout)
                    return ((ContainerImpl.NativeLayout)manager).layout;
                return null;
            }
        });
    }

    public void iterate(ContentHolder.ContentIterator<Widget> it) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
