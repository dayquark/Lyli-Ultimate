/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ormeli.helpers;

import org.ormeli.LytroCore.LytroCamera;
import java.util.List;
import javax.usb.UsbDevice;
import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbHub;
import javax.usb.UsbPort;
import javax.usb.UsbServices;
import org.ormeli.helpers.OutputPrinter;

/**
 *
 * @author quark
 */
public class LytroUSBDetector {
    
    public static LytroCamera searchLytroCamera(OutputPrinter outStatus) throws UsbException{
        final UsbServices services = UsbHostManager.getUsbServices();
        outStatus.printlnReg("**********************************");
        outStatus.printlnReg("USB Service Implementation: " + services.getImpDescription());
        outStatus.printlnReg("\n");
        
        LytroCamera cameraObj = findLytroCamera(services.getRootUsbHub(), outStatus);       
        
        if (cameraObj == null){
            outStatus.printlnErr("Didn't find camera");
        }else{
            outStatus.printlnReg("Found camera");
        }
        
        return cameraObj;
    }
    
    
    private static LytroCamera findLytroCamera(final UsbDevice device, OutputPrinter op)
    {
        
        LytroCamera lytroCamera = null;
        
        // Dump information about the device itself
        System.out.println(device);
        final UsbPort port = device.getParentUsbPort();
        if (port != null)
        {
            System.out.println("Connected to port: " + port.getPortNumber());
            System.out.println("Parent: " + port.getUsbHub());
        }



        if (LytroCamera.isLytroCamera(device)){
            System.out.println("Lytro Camera Detected");
            lytroCamera = new LytroCamera(device, op);
            lytroCamera.printCameraUSBInformation();
            return lytroCamera;
        }
        
        System.out.println();

        // Dump child devices if device is a hub
        if (device.isUsbHub())
        {
            final UsbHub hub = (UsbHub) device;
            for (UsbDevice child: (List<UsbDevice>) hub.getAttachedUsbDevices())
            {
                lytroCamera = findLytroCamera(child, op);
                if (lytroCamera != null)
                    return lytroCamera;
            }
        }
        
        return lytroCamera;
    }    

    
}
