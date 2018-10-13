/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myjava.lesson02;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 *
 * @author wliu
 */
public class MessageDigestSample {
    
    public static void main(String[] args) {
        InputStream fis = null;
        try {
            String path = "/Users/wliu/Music/My/leiting-zuiai.wav";
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            fis = new FileInputStream(new File(path));
            int n = 0;
            byte[] buffer = new byte[8192];
            while (n != -1) {
                n = fis.read(buffer);
                if (n > 0) {
                    messageDigest.update(buffer, 0, n);
                }
            }
            
            String sha1 = new HexBinaryAdapter().marshal(messageDigest.digest());
            System.out.println("File sha1 = " + sha1);
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MessageDigestSample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
