/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ormeli.LytroCore;

import org.ormeli.usb.LytroCommands.LytroCommSeq;
import org.ormeli.usb.LytroCommands.LytroCommander;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.List;
import javax.usb.UsbConfiguration;
import javax.usb.UsbDevice;
import javax.usb.UsbEndpoint;
import javax.usb.UsbException;
import javax.usb.UsbInterface;
import org.ormeli.helpers.OutputPrinter;
import org.usb4java.BufferUtils;
import org.usb4java.DeviceHandle;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

/**
 *
 * @author quark
 */
public class LytroCamera {
  
    static String USB_VENDOR_ID = "0x24cf";
    static Short USB_VENDOR_ID_SH = 9423;
    static String USB_PRODUCT_ID = "0x00a1";
    static Short USB_PRODUCT_ID_SH = 161;
    
    /** The input endpoint of the Lytro */
    private static final byte IN_ENDPOINT = (byte) 0x82;

    /** The output endpoint of the Lytro */
    private static final byte OUT_ENDPOINT = 0x02;
    
    /** The communication timeout in milliseconds. */
    private static final int TIMEOUT = 5000;
    
    private static final byte INTERFACE = 0;
    
    public static boolean isLytroCamera(UsbDevice device){
        return (device.getUsbDeviceDescriptor().idVendor() == USB_VENDOR_ID_SH)
                & (device.getUsbDeviceDescriptor().idProduct() == USB_PRODUCT_ID_SH);
    }
    
    UsbDevice LytroUSBDevice;
    DeviceHandle LytroHandle = null;
    OutputPrinter connStatus;

    public LytroCamera(UsbDevice LytroUSBDevice, OutputPrinter op) {
        this.LytroUSBDevice = LytroUSBDevice;
        connStatus = op;
        
        // Initialize the libusb context
        int result = LibUsb.init(null);
        if (result != LibUsb.SUCCESS)
        {
            throw new LibUsbException("Unable to initialize libusb", result);
        }        

        LytroHandle = LibUsb.openDeviceWithVidPid(null, USB_VENDOR_ID_SH,
            USB_PRODUCT_ID_SH);
        
        if (LytroHandle == null)
        {
            System.err.println("Test device not found.");
            System.exit(1);
        }

        // Claim the ADB interface
        int r = LibUsb.detachKernelDriver(LytroHandle, INTERFACE);
        result = LibUsb.claimInterface(LytroHandle, INTERFACE);
        if (result != LibUsb.SUCCESS)
        {
            throw new LibUsbException("Unable to claim interface", result);
        }
     
        
        // Sets configuration
        LibUsb.setConfiguration(LytroHandle, 1);
        
        
        this.connStatus.printlnReg("Connected to the device and claimed");
        
    }
    
    public void closeLytroConn(){
                // Release the ADB interface
        int result = LibUsb.releaseInterface(LytroHandle, INTERFACE);
        if (result != LibUsb.SUCCESS)
        {
            throw new LibUsbException("Unable to release interface", result);
        }

        // Close the device
        LibUsb.close(LytroHandle);

        // Deinitialize the libusb context
        LibUsb.exit(null);
        
        this.connStatus.printlnReg("closed connection");
    }
    
    
    public void printCameraUSBInformation(){
        // Dump device descriptor
//        System.out.println(LytroUSBDevice.getUsbDeviceDescriptor());
//                // Process all configurations
//        for (UsbConfiguration configuration: (List<UsbConfiguration>) LytroUSBDevice
//            .getUsbConfigurations())
//        {
//            // Dump configuration descriptor
//            System.out.println(configuration.getUsbConfigurationDescriptor());
//
//            // Process all interfaces
//            for (UsbInterface iface: (List<UsbInterface>) configuration
//                .getUsbInterfaces())
//            {
//                // Dump the interface descriptor
//                System.out.println(iface.getUsbInterfaceDescriptor());
//
//                // Process all endpoints
//                for (UsbEndpoint endpoint: (List<UsbEndpoint>) iface
//                    .getUsbEndpoints())
//                {
//                    // Dump the endpoint descriptor
//                    System.out.println(endpoint.getUsbEndpointDescriptor());
//                }
//            }
//        }
    }

    
    public void getCameraInformation() throws UsbException{
        
        connStatus.printlnReg("\n Sending get camera inforormation");
        
        if (this.LytroHandle!= null){

//            LytroCommSeq.ReadCameraInformation(LytroHandle);
//            LytroCommSeq.ReadFirmwareFile(LytroHandle);
            LytroCommSeq.ReadImageList(LytroHandle);
            
        }
    }

}
