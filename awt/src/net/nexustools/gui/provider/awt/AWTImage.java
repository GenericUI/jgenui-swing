/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.WeakHashMap;
import net.nexustools.concurrent.Prop;
import net.nexustools.gui.Image;
import net.nexustools.gui.provider.awt.impl.AWTWidgetImpl;
import net.nexustools.gui.render.Painter;
import net.nexustools.gui.render.Pixmap;
import net.nexustools.gui.render.Renderable;
import net.nexustools.io.Stream;

/**
 *
 * @author katelyn
 */
public class AWTImage extends AWTWidgetImpl<Component> implements Image {
    
    private static class Renderer implements net.nexustools.gui.render.Renderer {
        public static class State {
            java.awt.Image image;
            Prop<Double> progress = new Prop();
        }
        public static final WeakHashMap<Stream, State> statesByStream = new WeakHashMap();
        public static final WeakHashMap<Pixmap, State> statesByPixmap = new WeakHashMap();
        @Override
        public void render(Renderable target, Painter painter) {
            Pixmap pixmap = ((AWTImage)target).pixmap.get();
            if(pixmap == null)
                return;
        }
    }
    
    private class Native extends Component {
        public Native() {
            setTag("Image");
        }
        @Override
        public void paint(Graphics g) {
            if(!customRender((Graphics2D)g))
                super.paint(g);
        }
    }

    protected final Prop<Pixmap> pixmap = new Prop();
    public AWTImage() {
        super(AWTPlatform.instance());
    }
    
    public AWTImage(String source) throws IOException, URISyntaxException {
        this(Stream.open(source));
    }
    
    public AWTImage(Stream source) {
        this();
        setPixmap(new Pixmap(source));
        setRenderer(new Renderer());
    }
    
    public void setPixmap(Pixmap pixmap) {
        this.pixmap.set(pixmap);
    }

    @Override
    protected Component create() {
        return new Native();
    }

    public void play() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void pause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void stop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isPlaying() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int currentFrame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int countFrames() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Mode mode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setMode(Mode mode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
