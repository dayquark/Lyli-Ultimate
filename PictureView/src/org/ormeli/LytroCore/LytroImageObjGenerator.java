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
public class LytroImageObjGenerator {

    ArrayList<LytroImageObject> allImages = new ArrayList<>();

    public LytroImageObjGenerator(ByteBuffer inputBuffer) {
        
        String content= ByteBufferConverters.ByteBufferToString(inputBuffer);
        inputBuffer.rewind();
        inputBuffer.position(84);
        
        LytroImageObject lio = new LytroImageObject(inputBuffer);
        allImages.add(lio);
    }
    
    

    
}
