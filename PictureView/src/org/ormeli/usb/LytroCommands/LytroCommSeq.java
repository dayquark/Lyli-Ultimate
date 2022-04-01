/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ormeli.usb.LytroCommands;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import org.ormeli.LytroCore.LytroDescriptor;
import org.ormeli.LytroCore.LytroImageObjGenerator;
import org.ormeli.helpers.ByteBufferConverters;
import org.ormeli.usb.USBBulkComm;
import org.usb4java.DeviceHandle;

/**
 *
 * @author quark
 */
public class LytroCommSeq {
    
    /** The input endpoint 0x82 of the Lytro */
    public static final byte IN_ENDPOINT = (byte) 0x82;

    /** The output endpoint 0x02 of the Lytro */
    public static final byte OUT_ENDPOINT = 0x02;
    
    
    
    /****
     * Sequence of commands to see if camera is ready to communicate
     */
    public static void checkCameraReady(){
        
    }
    
    private static int QueryTransferSize(DeviceHandle handle){

        USBBulkComm.writeToUsb(handle, LytroCommander.QueryDataSize(), OUT_ENDPOINT);
        ByteBuffer output = USBBulkComm.readFromUsb(handle, 4, IN_ENDPOINT);        
        output.order(ByteOrder.LITTLE_ENDIAN);
        int length = output.getInt();
        
        System.out.println("Data recieved from Query datasize is: " + String.valueOf(length));
        output = USBBulkComm.readFromUsb(handle, 13, IN_ENDPOINT); 
        
        return length;
    }    
    
    /***
     * Basic communication with camera which returns id properties of the camera
     * 
     * @param handle
     * @return LytroDescriptor containing all camera information
     */
    public static LytroDescriptor ReadCameraInformation (DeviceHandle handle){
        
        System.out.println("Reading basic camera data");
        
        ByteBuffer output;
        
        USBBulkComm.writeToUsb(handle, LytroCommLoad.LoadCameraInfoCommand(), OUT_ENDPOINT);
        USBBulkComm.readFromUsb(handle, 13, IN_ENDPOINT);
        
        int dataInSize = QueryTransferSize(handle);

        USBBulkComm.writeToUsb(handle, LytroCommander.DownloadData(), OUT_ENDPOINT);
        output = USBBulkComm.readFromUsb(handle, dataInSize, IN_ENDPOINT);
        USBBulkComm.readFromUsb(handle, 13, IN_ENDPOINT);
        
        LytroDescriptor ld = new LytroDescriptor(output);
        
        return ld;
    }
    
    public static ByteBuffer ReadFirmwareFile(DeviceHandle handle){

        System.out.println("Reading firmware file");
        
        USBBulkComm.writeToUsb(handle, LytroCommLoad.LoadFile(), OUT_ENDPOINT);
        USBBulkComm.writeToUsb(handle, ByteBufferConverters.StringTo16Byte("A:\\FIRMWARE.TXT"), OUT_ENDPOINT);
        
        ByteBuffer output = USBBulkComm.readFromUsb(handle, 16, IN_ENDPOINT);
        String filename = ByteBufferConverters.ByteBufferToString(output);
        
        System.out.println("FirmwareFile");
        System.out.println(filename);
        
        int dataInSize = QueryTransferSize(handle);

        USBBulkComm.writeToUsb(handle, LytroCommander.DownloadData(), OUT_ENDPOINT);
        output = USBBulkComm.readFromUsb(handle, dataInSize, IN_ENDPOINT);
        USBBulkComm.readFromUsb(handle, 13, IN_ENDPOINT);        
        
        ByteBufferConverters.ByteBufferToTxtFile("firmwareLytro.txt", output);
        System.out.println("Wrote firmware txt");
        
        return output;
    }
    
    public static ByteBuffer ReadImageList(DeviceHandle handle){

        System.out.println("Reading Image list");
        
        USBBulkComm.writeToUsb(handle, LytroCommLoad.LoadPictureListCommand(), OUT_ENDPOINT);
//        USBBulkComm.writeToUsb(handle, ByteBufferConverters.StringTo16Byte("A:\\FIRMWARE.TXT"), OUT_ENDPOINT);
        
        ByteBuffer output = USBBulkComm.readFromUsb(handle, 16, IN_ENDPOINT);
//        String filename = ByteBufferConverters.ByteBufferToString(output);
        
//        System.out.println("FirmwareFile");
//        System.out.println(filename);
        
        int dataInSize = QueryTransferSize(handle);

        USBBulkComm.writeToUsb(handle, LytroCommander.DownloadData(), OUT_ENDPOINT);
        output = USBBulkComm.readFromUsb(handle, dataInSize, IN_ENDPOINT);
        USBBulkComm.readFromUsb(handle, 13, IN_ENDPOINT);        
        
        ByteBufferConverters.ByteBufferToTxtFile("fileList.txt", output);
        System.out.println("Wrote firmware txt");

        CharBuffer cbuff = output.asCharBuffer();
        cbuff.rewind();
//        char car = cbuff.get(85);
        
        LytroImageObjGenerator liog = new LytroImageObjGenerator(output);
        
        return output;
    }
}
