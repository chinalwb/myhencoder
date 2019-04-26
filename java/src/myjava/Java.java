/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myjava;

/**
 *
 * @author wliu
 */
public class Java {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int x = -96;
        System.out.println("x binary == " + Integer.toBinaryString(x));
        System.out.println("x binary == " + Integer.toBinaryString(x >>> 1));
        x = x >>> 1;
        System.out.println("x binary == " + Integer.toBinaryString(x));
        System.out.println("x == " + x);
    }
    
}
