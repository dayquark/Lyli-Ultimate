/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ormeli.usb;

import java.util.Random;

/**
 *
 * @author quark
 */
public class CommandBlockWrapper extends USBBlockWrapper{



    /****
     * Crates CBW w/ tag, data and command block
     * @param tag
     * @param dataTransLength
     * @param commandBlock 
     */
    public CommandBlockWrapper(byte[] commandBlock) {
        super(commandBlock);
        
        this.signature = "USBC";
        
    }
    

    
    
    
}
