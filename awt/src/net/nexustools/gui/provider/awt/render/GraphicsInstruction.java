/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt.render;

import java.awt.Graphics;
import java.util.ListIterator;
import net.nexustools.gui.render.Painter;

/**
 *
 * @author katelyn
 */
public abstract class GraphicsInstruction implements Painter.Instruction {
    
    public static GraphicsInstruction compile(Painter.Instruction instruction) {
        if(instruction instanceof GraphicsInstruction)
            return ((GraphicsInstruction)instruction);
        return null;
    }
    
    public static void optimize(ListIterator<Painter.Instruction> instructions) {
    }
    
    public abstract void run(Graphics graphics);
    
}
