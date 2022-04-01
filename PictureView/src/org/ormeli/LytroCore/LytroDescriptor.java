/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ormeli.LytroCore;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 *
 * @author quark
 */
public class LytroDescriptor {

    String manufacturer;
    String serialNumber;
    String buildId;
    String softwareVersion;

    public LytroDescriptor(ByteBuffer buffer) {
        
        Charset charset = Charset.forName("ISO-8859-1");
        this.manufacturer = charset.decode(buffer).toString();
        
        buffer.position(256);
        this.serialNumber = charset.decode(buffer).toString();
        
        buffer.position(128);
        this.buildId = charset.decode(buffer).toString();
        
        buffer.position(128);
        this.softwareVersion = charset.decode(buffer).toString();
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getBuildId() {
        return buildId;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    @Override
    public String toString() {
        String output = this.manufacturer+"\n";
        output += this.serialNumber+"\n";
        output += this.buildId+"\n";
        output += this.softwareVersion;
        
        return output;
    }
       
    
}
