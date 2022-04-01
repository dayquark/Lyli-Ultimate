/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ormeli.usb;

/**
 *
 * @author quark
 */
public class StatusBlockWrapper extends USBBlockWrapper{

    public StatusBlockWrapper(byte[] tag, byte[] commandBlock) {
        super(commandBlock);
        
        this.signature = "USBS";
    }
    
    
    
}
