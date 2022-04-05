/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ormeli.LytroCore;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openide.util.Exceptions;

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
        String dateTemp = (new String(temp)).trim(); 
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            //        String string1 = "2001-07-04T12:08:56.235-0700";
            this.dateTimeTaken = df1.parse(dateTemp);
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        this.rotation = inputBuffer.getInt();
    }

    /****
     * Gets base path for a file
     * @return 
     */
    private String getBasePath(){
        String picNumber = "";
        
        if (fileNumber<10){
            picNumber = "000" + String.valueOf(fileNumber);
        }else if(fileNumber<100){
            picNumber = "00" + String.valueOf(fileNumber);
        }else if(fileNumber<1000){
            picNumber = "0" + String.valueOf(fileNumber);
        }else{
            picNumber = String.valueOf(fileNumber);
        }
        
        
        return "I:\\DCIM\\" + String.valueOf(folderNumber)+folderName 
                + "\\" + fileNamePrefix+picNumber;
    }    
    
    
    /****
     * Gets string w/ raw path w/o null termination
     * @return 
     */
    public String getPathToRaw(){
        return this.getBasePath() + ".RAW";
    }

    /***
     * Gets base path to 128 jpg image
     * @return 
     */
    public String getPathTo128(){
        return this.getBasePath() + ".128";
    }
    
    /****
     * Gets Meta-Data of the file
     * @return 
     */
    public String getMetaDataPath(){
        return this.getBasePath() + ".TXT";
    }

    public String getJPGPath(){
        return this.getBasePath() + ".JPG";
    }
    
}
