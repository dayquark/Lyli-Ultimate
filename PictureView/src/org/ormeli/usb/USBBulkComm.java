/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ormeli.usb;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import org.usb4java.BufferUtils;
import org.usb4java.DeviceHandle;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

/**
 *
 * @author quark
 */
public class USBBulkComm {


    
    /** The communication timeout in milliseconds. */
    public static final int TIMEOUT = 5000;
    
//    public static final byte INTERFACE = 0;
    
    
    /****
     * Sends data to end_point specified
     * @param DeviceHandle handle
     * @param data as byte[] 
     */
    public static void writeToUsb(DeviceHandle handle, byte[] data, byte endPoint){
        ByteBuffer buffer = BufferUtils.allocateByteBuffer(data.length);
        buffer.put(data);
        IntBuffer transferred = BufferUtils.allocateIntBuffer();
        
        int result = LibUsb.bulkTransfer(handle, endPoint, buffer,
            transferred, TIMEOUT);
        
        if (result != LibUsb.SUCCESS)
        {
            throw new LibUsbException("Unable to send data", result);
        }
        
        System.out.println(transferred.get() + " bytes sent to device");        
    }
    
    /***
     * Read data from USB
     * @param handle DeviceHandle
     * @param size
     * @param endPoint to send to 
     * @return ByteBuffer with data
     */
    public static ByteBuffer readFromUsb(DeviceHandle handle, int size, byte endPoint) {
        
        ByteBuffer buffer = BufferUtils.allocateByteBuffer(size).order(
            ByteOrder.LITTLE_ENDIAN);
        IntBuffer transferred = BufferUtils.allocateIntBuffer();
        int result = LibUsb.bulkTransfer(handle, endPoint, buffer,
            transferred, TIMEOUT);
        
        if (result != LibUsb.SUCCESS)
        {
            throw new LibUsbException("Unable to read data", result);
        }
        System.out.println(transferred.get() + " bytes read from device");
        
        return buffer;
    }

    
}
