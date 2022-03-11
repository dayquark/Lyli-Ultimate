/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ormeli.helpers;

import org.netbeans.api.io.IOProvider;
import org.netbeans.api.io.InputOutput;

/**
 * Output tab printer for Netbeans Application platform
 * @author quark
 */
public class OutputPrinter {
    
    InputOutput io;
    String tabName;

    public OutputPrinter(String tabName) {
        this.tabName = tabName;
        
        io = IOProvider.getDefault().getIO(tabName, true);
        io.show();
    }
    
    public void closeIO(){
        io.close();
    }
    
    public void reopenIO(){
        io.show();
    }
    
    /**
     * Prints out regular message
     * @param message 
     */
    public void printlnReg(String message){
        io.getOut().println(message);
    } 
    
    /**
     * Prints out error message
     * @param message 
     */
    public void printlnErr(String message){
        io.getOut().println(message);
    }
    
}
