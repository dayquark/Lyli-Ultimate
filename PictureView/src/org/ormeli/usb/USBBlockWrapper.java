/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ormeli.usb;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

/**
 *
 * @author quark
 */
public class USBBlockWrapper {
    String signature = "USBC";
    byte[] tag = new byte[4];
    //0 - host to device w/ 1 - device to host
    int flag = 0;
    int dataTransferLength = 0;
    int CBWLUN = 0; //Lytro specific
    int commandBlockLength = 12;
    byte [] commandBlock;

    public USBBlockWrapper( byte[] commandBlock) {

        Random rnd = new Random();
        rnd.nextBytes(this.tag);
                
        this.commandBlock = commandBlock;
    } 

    /***
     * Sets flag to 0x80
     */
    public void changeToDeviceToHost(){
        flag = 128;
    }
    
    public void changeToHostToDevice(){
        flag = 0;
    }
    
    public byte[] generateByteArray(){
        byte[] r_buffer = new byte[31];
        
        byte[] sig = signature.getBytes();

        byte[] dTransLength = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(dataTransferLength).array();
        byte flag = (byte) (((byte) this.flag) & 0xFF);
        byte cbsLun = (byte) (((byte) this.CBWLUN) & 0xFF);
                
        for (int i=0; i<4; i++){
            r_buffer[i] = sig[i];
        }

        for (int i=4; i<8; i++){
            r_buffer[i] = tag[i-4];
        }

        for (int i=8; i<12; i++){
            r_buffer[i] = dTransLength[i-8];
        }        
        r_buffer[12] = flag;
        r_buffer[13] = cbsLun;
        r_buffer[14] = (byte) this.commandBlockLength;
        

        for (int i=0; i<12; i++){
            r_buffer[i+15] = commandBlock[i];
        }

        
        return r_buffer;
    }

    public int getDataTransferLength() {
        return dataTransferLength;
    }

    public void setDataTransferLength(int dataTransferLength) {
        this.dataTransferLength = dataTransferLength;
    }

    public byte[] getTag() {
        return tag;
    }

    public void setTag(byte[] tag) {
        this.tag = tag;
    }
    
    
    
}
