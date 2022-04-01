/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ormeli.LytroCore;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 *
 * @author quark
 */
public class LytroImageObject {

    //8 byte Each
    String folderName;
    String fileNamePrefix;
    
    int folderNumber;
    int fileNumber;
    
    int pictureLiked = 0;
    
    float lastLambda;
    String pictureSHAID;
    Date dateTimeTaken;
    
    int rotation;

    public LytroImageObject(ByteBuffer inputBuffer) {
        
        inputBuffer.order(ByteOrder.LITTLE_ENDIAN);

        byte[] temp = new byte[8];
        inputBuffer.get(temp, 0, 8);
        folderName = (new String(temp, StandardCharsets.UTF_8)).trim();

        inputBuffer.get(temp, 0, 8);
        fileNamePrefix = (new String(temp, StandardCharsets.UTF_8)).trim();
                
        folderNumber = inputBuffer.getInt();
        fileNumber = inputBuffer.getInt();        
        
        inputBuffer.position(40);
        this.pictureLiked = inputBuffer.getInt();
        
        this.lastLambda = inputBuffer.getFloat();
        
        temp = new byte[48];
        inputBuffer.get(temp, 0, 48);
        this.pictureSHAID = (new String(temp, StandardCharsets.UTF_8)).trim();
        
        temp = new byte[28];
        inputBuffer.get(temp, 0, 28);
        String dateTemp = (new String(temp, StandardCharsets.UTF_8)).trim(); 
        
        this.rotation = inputBuffer.getInt();
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFileNamePrefix() {
        return fileNamePrefix;
    }

    public void setFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
    }

    public int getFolderNumber() {
        return folderNumber;
    }

    public void setFolderNumber(int folderNumber) {
        this.folderNumber = folderNumber;
    }

    public int getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(int fileNumber) {
        this.fileNumber = fileNumber;
    }

    public int getPictureLiked() {
        return pictureLiked;
    }

    public void setPictureLiked(int pictureLiked) {
        this.pictureLiked = pictureLiked;
    }

    public float getLastLambda() {
        return lastLambda;
    }

    public void setLastLambda(float lastLambda) {
        this.lastLambda = lastLambda;
    }

    public String getPictureSHAID() {
        return pictureSHAID;
    }

    public void setPictureSHAID(String pictureSHAID) {
        this.pictureSHAID = pictureSHAID;
    }

    public Date getDateTimeTaken() {
        return dateTimeTaken;
    }

    public void setDateTimeTaken(Date dateTimeTaken) {
        this.dateTimeTaken = dateTimeTaken;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
    
    
    
}
