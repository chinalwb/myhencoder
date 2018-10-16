/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myjava.lesson02;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

/**
 *
 * @author wliu
 */
public class Base64Sample {
    
    
    
    

    public Base64Sample() {
        
    }
    
    public static void main(String[] args) throws Exception {
        
        File file = new File("/Users/wliu/Downloads/x.png");
        System.out.println("file == " + file.getAbsolutePath());
        
        long len = file.length();
        InputStream is = new FileInputStream(file);
        byte[] buffer = new byte[(int) len];
        int offset = 0;
        int numRead = 0;
 
        while (offset < len
                && (numRead = is.read(buffer, offset, ((int)len - offset))) >= 0) {
            offset += numRead;
        }
        
        System.out.println("numRead " + buffer.length);
        
        byte[] base64bytes = Base64.getEncoder().encode(buffer);
        String s = new String(base64bytes);
//        System.out.println("s == " + s);
        
        byte[] outBuf = Base64.getDecoder().decode(base64bytes);
        File xFile = new File("/Users/wliu/Downloads/xbase64.png");
        if (xFile.exists()) {
            xFile.delete();
        }
        xFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(xFile);
        fos.write(outBuf);
        fos.flush();
//        byte[] bytes = null;
//        Base64.getEncoder().encode(bytes);
    }
    
}
