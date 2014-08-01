/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.awt.impl;

import java.util.EventListener;
import net.nexustools.concurrent.Prop;
import net.nexustools.concurrent.PropAccessor;

/**
 *
 * @author katelyn
 */
public abstract class ListenerProp<T extends EventListener> extends Prop<T> {
    
    public abstract void connect(PropAccessor<T> myself);
    public abstract void disconnect(PropAccessor<T> myself);
    
}
