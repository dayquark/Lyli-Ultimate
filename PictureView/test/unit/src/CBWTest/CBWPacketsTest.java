/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CBWTest;



import java.io.IOException;
import java.nio.ByteBuffer;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import org.openide.util.Exceptions;
import org.ormeli.helpers.ByteBufferConverters;
import org.ormeli.usb.LytroCommands.LytroCommLoad;
import org.ormeli.LytroCore.LytroImageObjManager;

/**
 *
 * @author quark
 */
public class CBWPacketsTest{
    
    @Test
    public void requestAllFileListCommand(){
        byte[] expectedCommand = {(byte)0x55, (byte)0x53, (byte)0x42, (byte)0x43,
            (byte)0x10, (byte)0x30, (byte)0x6b, (byte)0xf9, (byte)0x13,
            00, 00, 00, 00, 00, (byte)0x0c, (byte)0xc2, 00, (byte) 0x01,
            00, 00, 00, 00, 00, 00, 
            00, 00, 00, 00, 00, 00, 00};

        byte[] actualCommand = LytroCommLoad.LoadFile();
        assertArrayEquals(expectedCommand, actualCommand);
    }
    
    @Test
    public void textToByteConversion(){
        byte[] expected = {(byte)0x41, (byte)0x3a, (byte)0x5c, (byte)0x46, (byte)0x49,
            (byte)0x52, (byte)0x4d, (byte)0x57, (byte)0x41,
            (byte)0x52, (byte)0x45, (byte)0x2e, (byte)0x54, (byte)0x58, (byte)0x54, 0};
        
        byte[] actual = ByteBufferConverters.StringTo16Byte("A:\\FIRMWARE.TXT");
        assertArrayEquals(expected, actual);

    }
    
    @Test
    public void testConversionByteToLytroObj(){
        try {
//            ByteBufferConverters.readFile("fileList.txt");
            ByteBuffer bb = ByteBufferConverters.readFileToByteBuffer("fileList.txt");
            LytroImageObjManager liog = new LytroImageObjManager(bb);
            int i = 3;
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    
    
}
