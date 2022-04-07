/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ormeli.LytroCore;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.ormeli.helpers.ByteBufferConverters;

/**
 *
 * @author quark
 */
public class LytroImageObjManager {

    ArrayList<LytroImageObject> allImages = new ArrayList<>();

    public LytroImageObjManager(ByteBuffer inputBuffer) {

        //Identifies location of File start
        for (int i=0; i<inputBuffer.capacity();i++){
            if (((char) inputBuffer.get(i)) == 'P'){
                inputBuffer.position(i);
                break;
            }
        }
        
        while(inputBuffer.position() < (inputBuffer.capacity()-2)){
            LytroImageObject lio = new LytroImageObject(inputBuffer);
            allImages.add(lio);
        }

    }
    
    

    
}
