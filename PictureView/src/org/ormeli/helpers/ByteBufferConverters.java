/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ormeli.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.openide.util.Exceptions;
import org.ormeli.LytroCore.LytroImageObject;

/**
 *
 * @author quark
 */
public class ByteBufferConverters {

    /****
     * Converts ByteBuffer to String
     * @param buffer
     * @return 
     */
    public static String ByteBufferToString(ByteBuffer buffer){
        Charset charset = Charset.forName("ISO-8859-1");
        return charset.decode(buffer).toString();
    }
    
    /****
     * Converts byte buffer to text file
     * @param filename
     * @param dataForWriting
     * @return 
     */
    public static File ByteBufferToTxtFile(String filename, ByteBuffer dataForWriting){
        File outputFile = new File(filename);
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            
            outputStream.getChannel().write(dataForWriting);
//            outputStream.flush();
            outputStream.close();
            
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        return outputFile;
    }
    
    /***
     * 
     * @param input
     * @return 
     */
    public static byte[] StringTo16Byte(String input){
        ByteBuffer bb = ByteBuffer.allocate(input.length()+1);
        Charset charset = Charset.forName("ISO-8859-1");
        bb.put(input.getBytes(charset));
        return bb.array();
    }
    
    
    public static ArrayList<ByteBuffer> readFile(String filename) throws IOException{
    
        ArrayList<ByteBuffer> allBBs = new ArrayList<>();
        
        FileInputStream inFile = new FileInputStream(filename);
        FileChannel inChannel = inFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(92);
        inChannel.read(buf);
        
        String temp;
        
        buf = ByteBuffer.allocate(128);
        buf.rewind();
        while (inChannel.read(buf) != -1) {

            buf.rewind();
            int size = buf.asCharBuffer().capacity();
            
            LytroImageObject lio = new LytroImageObject(buf);
            
            allBBs.add(buf);
            char[] data = new char[128];
            for (int i=0;i<128;i++){
                data[i]= (char) buf.get(i);                
            }        
            

            temp = ByteBufferConverters.ByteBufferToString(buf);
            buf = ByteBuffer.allocate(128);
//            buf.clear();
        }
        
        inFile.close();
        return allBBs;
    }
}
