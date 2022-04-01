/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ormeli.usb.LytroCommands;

import org.ormeli.usb.CommandBlockWrapper;

/**
 *
 * @author quark
 */
public class LytroCommander {

    public static byte[] getBlankCommand(){
        byte[] blank = new byte[0];
        return blank;
    }
    
    
    public static byte[] checkLytroReady(){
        byte[] commandBlock = new byte[] {00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00};
//        byte[] tag = new byte[] {(byte)0x10, (byte)0x30, (byte)0x6b, (byte)0xf9};

        CommandBlockWrapper cmb = new CommandBlockWrapper(commandBlock);
        return cmb.generateByteArray();     
    }
    
    

    
    /****
     * Gets file size 0xc6 command
     * @return 
     */
//    public static byte[] getSelectedFileSize(){
//        byte[] commandBlock = new byte[] {(byte)0xc6, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00};
//        byte[] tag = new byte[] {(byte)0x10, (byte)0x30, (byte)0x6b, (byte)0xf9};
//        
//        CommandBlockWrapper cmb = new CommandBlockWrapper(tag, commandBlock);
//        cmb.changeToHostToDevice();
//        cmb.setDataTransferLength(65536);
//        return cmb.generateByteArray();
//    }

    /****
     * Gets data from Lytro 0xc4 command
     * data transfer length 65536
     * @param pocket_number
     * @return 
     */    
    public static byte[] getData(int pocket_number){
        byte[] commandBlock = new byte[] {(byte)0xc4, 00, 01, 00, 00, (byte) pocket_number, 00, 00, 00, 00, 00, 00};
//        byte[] tag = new byte[] {(byte)10, (byte)30, (byte)0x6b, (byte)0xf9};
        
        CommandBlockWrapper cmb = new CommandBlockWrapper(commandBlock);
        cmb.changeToHostToDevice();
        cmb.setDataTransferLength(65536);
        return cmb.generateByteArray();
    }
    
    /****
     * Gets data from Lytro 0xc4 0 0 command
     * data transfer length 65536
     * @param pocket_number
     * @return 
     */    
    public static byte[] DownloadData(){
        byte[] commandBlock = new byte[] {(byte)0xc4, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        
        CommandBlockWrapper cmb = new CommandBlockWrapper(commandBlock);
        cmb.changeToDeviceToHost();
        cmb.setDataTransferLength(65536);
        
        return cmb.generateByteArray();
    }

    /****
     * Gets data from Lytro 0xc4 command
     * data transfer length 0 - request data size
     * @param pocket_number
     * @return 
     */    
    public static byte[] QueryDataSize(){
        byte[] commandBlock = new byte[] {(byte)0xc6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        
        CommandBlockWrapper cmb = new CommandBlockWrapper(commandBlock);
        cmb.changeToDeviceToHost();
        cmb.setDataTransferLength(65536);
        return cmb.generateByteArray();
    }

    
    public static byte[] inquiryCommand(){
        byte[] commandBlock = new byte[] {(byte)0x12, 00, 00, 00, (byte) 0x24,
            00, 00, 00, 00, 00, 00, 00, 00};

        CommandBlockWrapper cmb = new CommandBlockWrapper(commandBlock);
        cmb.changeToHostToDevice();
        cmb.setDataTransferLength(36);
        
        return cmb.generateByteArray();
    }
    
      
    
}
