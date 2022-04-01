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
public class LytroCommLoad {
     
    /****
     * Loads file from camera
     * 0xc2 0 01 command
     * @return array
     */
    public static byte[] LoadFile(){
        byte[] commandBlock = new byte[] {(byte)0xc2, 00, 01, 00, 00, 00, 00, 00, 00, 00, 00, 00};
        
        CommandBlockWrapper cmb = new CommandBlockWrapper(commandBlock);
        cmb.setDataTransferLength(16);
        return cmb.generateByteArray();
    }
        
    /****
     * Generates request file list command for Lytro Camera
     * 0xc2 0 2 command
     * @return 
     */
    public static byte[] LoadPictureListCommand(){
        byte[] commandBlock = new byte[] {(byte)0xc2, 00, (byte)0x02, 00, 00, 00, 00, 00, 00, 00, 00, 00};
        
        CommandBlockWrapper cmb = new CommandBlockWrapper(commandBlock);
        return cmb.generateByteArray();
    }

    /****
     * Loads internal list of files
     * @return 
     */
    public static byte[] LoadInternalListCommand(){
        byte[] commandBlock = new byte[] {(byte)0xc2, 00, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        
        CommandBlockWrapper cmb = new CommandBlockWrapper(commandBlock);
        return cmb.generateByteArray();
    }
    
    /****
     * Load camera information C2 0 0
     * @return 
     */
    public static byte[] LoadCameraInfoCommand(){
        byte[] commandBlock = new byte[] {(byte)0xc2, 0, 0, 0, 0,
            00, 00, 00, 00, 00, 00, 0, 0};   

        CommandBlockWrapper cmb = new CommandBlockWrapper(commandBlock);
        
        return cmb.generateByteArray();
    } 
    

    /****
     * Load Picture - loads picture from camera
     * @return 
     */
    public static byte[] LoadPictureCommand(){
        byte[] commandBlock = new byte[] {(byte)0xC2, 00, 05, 00, 0x00,
            00, 00, 00, 00, 00, 00, 00, 00};
              

        CommandBlockWrapper cmb = new CommandBlockWrapper(commandBlock);
        cmb.changeToHostToDevice();
        cmb.setDataTransferLength(36);
        
        return cmb.generateByteArray();
    }
    
    /****
    * Loads Calibration Data from the camera
    * C2 style command
    * @return 
    */
    public static byte[] LoadCalibrationDataCommand(){
        byte[] commandBlock = new byte[] {(byte)0xc2, 00, 06, 00, 0x00,
            00, 00, 00, 00, 00, 00, 00, 00};      

        CommandBlockWrapper cmb = new CommandBlockWrapper(commandBlock);
        cmb.changeToHostToDevice();
        cmb.setDataTransferLength(36);
        
        return cmb.generateByteArray();
    } 


    /****
    * Loads compressed jpeg data
    * C2 style commands C2 00 07
    * @return 
    */
    public static byte[] LoadCompressedJpeg(){
        byte[] commandBlock = new byte[] {(byte)0xc2, 00, 07, 00, 0x00,
            00, 00, 00, 00, 00, 00, 00, 00};
        
        byte[] tag = new byte[] {(byte)10, (byte)30, (byte)0x6b, (byte)0xf9};        

        CommandBlockWrapper cmb = new CommandBlockWrapper(commandBlock);
        cmb.changeToHostToDevice();
        cmb.setDataTransferLength(36);
        
        return cmb.generateByteArray();
    }     
}
