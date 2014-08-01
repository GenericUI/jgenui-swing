/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import net.nexustools.gui.Body;
import net.nexustools.gui.Container;
import net.nexustools.gui.Menu;
import net.nexustools.gui.Toolbar;
import net.nexustools.gui.Widget;
import net.nexustools.gui.event.LayoutListener;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.layout.Layout;
import net.nexustools.gui.platform.WidgetPeer;
import net.nexustools.gui.provider.awt.impl.ContainerImpl;
import net.nexustools.gui.render.StyleSheet;

/**
 *
 * @author katelyn
 */
public class SwingBody extends ContainerImpl<JFrame> implements Body {
    
    public static enum CloseMode {
        DontExitOnClose,
        ExitOnNoBodies,
        ExitOnNoWindows
    }
    
    private static CloseMode exitOnCloseMode = CloseMode.ExitOnNoWindows;
    
    private class NativeBody extends JFrame implements WidgetPeer<Body> {

        public NativeBody() {
            setName("Body");
            addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {}
                @Override
                public void windowClosing(WindowEvent e) {
                    System.out.println(exitOnCloseMode.toString());
                    
                    boolean canExit = true;
                    switch(exitOnCloseMode) {
                        case ExitOnNoBodies:
                            for(Window win : Window.getWindows()) {
                                if(win instanceof NativeBody && win != component && win.isVisible()) {
                                    canExit = false;
                                    break;
                                }
                            }
                        break;
                        
                        case ExitOnNoWindows:
                            for(Window win : Window.getWindows()) {
                                if(win instanceof NativeBody && win != component && win.isVisible()) {
                                    canExit = false;
                                    break;
                                }
                            }
                        break;
                            
                        case DontExitOnClose:
                            canExit = false;
                            break;
                    }
                    if(canExit)
                        System.exit(0);
                }
                @Override
                public void windowClosed(WindowEvent e) {}
                @Override
                public void windowIconified(WindowEvent e) {}
                @Override
                public void windowDeiconified(WindowEvent e) {}
                @Override
                public void windowActivated(WindowEvent e) {}
                @Override
                public void windowDeactivated(WindowEvent e) {}
            });
        }

        @Override
        public void paint(Graphics g) {
            if(!customRender(((Graphics2D)g)))
                super.paint(g); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Body genUI() {
            return SwingBody.this;
        }
        
        public boolean compare(Dimension from, Dimension to) {
            return from.width == to.width || from.width == to.width;
        }

    }

    private Widget mainWidget;
    SwingBody(SwingPlatform platform) {
        super(SwingPlatform.instance());
    }
    public SwingBody(String title) {
        this(SwingPlatform.instance());
        setTitle(title);
    }

    @Override
    public void addToolbar(Toolbar toolbar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeToolbar(Toolbar toolbar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected JFrame create() {
        return new NativeBody();
    }

    @Override
    public String title() {
        return component.getTitle();
    }

    @Override
    public void setTitle(String title) {
        component.setTitle(title);
    }

    @Override
    public Menu menu(String pos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addMenu(String pos, Menu menu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(Widget widget) {
        mainContainer().add(widget);
    }

    @Override
    public void remove(Widget widget) {
        mainContainer().remove(widget);
    }
    
    @Override
    public StyleSheet stylesheet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setStylesheet(StyleSheet styleSheet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Layout layout() {
        return mainContainer().layout(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLayout(Layout layout) {
        mainContainer().setLayout(layout); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setMainWidget(final Widget mainWidget) {
        act(new Runnable() {
            @Override
            public void run() {
                java.awt.Container container = component.getContentPane();
                
                SwingBody.this.mainWidget = mainWidget;
                if(mainWidget instanceof ContainerImpl) {
                    ((ContainerImpl)mainWidget).addLayoutListener(new LayoutListener() {
                        public void layoutFinished(final LayoutListener.LayoutEvent event) {
                            System.out.println("Layout Update Finished");
                            act(new Runnable() {
                                public boolean compare(Dimension size, Dimension other) {
                                    return size.width == other.width && size.height == other.height;
                                }
                                public void run() {
                                    System.out.println("Fixing Sizing");
                                    System.out.println(event.preferredSize);
                                    System.out.println(event.minimumSize);
                                    
                                    Size size = event.minimumSize;
                                    Insets insets = component.getInsets();
                                    size.h += insets.top + insets.bottom;
                                    size.w += insets.left + insets.right;
                                    
                                    Dimension nSize = new Dimension((int)size.w, (int)size.h);
                                    Dimension cSize = component.getMinimumSize();
                                    //if(!compare(nSize, cSize))
                                        component.setMinimumSize(nSize);
                                    
                                    size = event.preferredSize;
                                    size.h += insets.top + insets.bottom;
                                    size.w += insets.left + insets.right;
                                    
                                    nSize = new Dimension((int)size.w, (int)size.h);
                                    cSize = component.getMinimumSize();
                                    //if(!compare(nSize, cSize))
                                        component.setPreferredSize(nSize);
                                    
                                    
                                }
                            });
                        }
                    });
                    container = _n(mainWidget);
                } else
                    throw new UnsupportedOperationException("Not supported yet.");
                
                component.setContentPane(container);
            }
        });
    }

    @Override
    public void setMainWidget(Widget mainWidget, String title) {
        setMainWidget(mainWidget);
        setTitle(title);
    }
    
    @Override
    public Container mainContainer() {
        return read(new Reader<Container>() {
            @Override
            public Container read() {
                if(!(mainWidget instanceof Container))
                    setMainWidget(mainWidget = new SwingContainer());
                
                return (Container) mainWidget;
            }
        });
    }
    @Override
    public Widget mainWidget() {
        return read(new Reader<Widget>() {
            @Override
            public Widget read() {
                if(mainWidget == null)
                    return mainContainer();
                
                return mainWidget;
            }
        });
    }
    
    @Override
    public void setVisible(boolean visible) {
        if(visible)
            SwingUtilities.updateComponentTreeUI(component);
        super.setVisible(visible);
    }

}
