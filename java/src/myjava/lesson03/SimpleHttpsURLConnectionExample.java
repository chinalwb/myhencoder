/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myjava.lesson03;

 
import java.net.*; 
import java.io.*; 
import javax.net.ssl.*; 
 
/**
 * 用Java调用 HttpsURLConnection 来请求网页。
 * @author wliu
 */
public class SimpleHttpsURLConnectionExample { 
  public static void main(String args[ ])throws Exception { 
    try { 
    
        URL url = new URL("https://www.hencoder.com");
//        SSLSocketFactory factory = HttpsURLConnection.getDefaultSSLSocketFactory();
//        HttpsURLConnection.setDefaultSSLSocketFactory(factory);

        HostnameVerifier hv = new HostnameVerifier() {
            @Override
            public boolean verify(String host, SSLSession ssls) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                System.out.println("host == " + host);
                return false;
            }
        };
        System.out.println("hv == " + hv);
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
        
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        HostnameVerifier chv = connection.getHostnameVerifier();
        System.out.println("chv == " + chv);
        connection.connect();
        
        // read the output from the server
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      StringBuilder stringBuilder = new StringBuilder();

      String line = null;
      while ((line = reader.readLine()) != null)
      {
          System.out.println("line = " + line);
        stringBuilder.append(line + "\n");
      }
        System.out.println(stringBuilder.toString());
    } catch(IOException e) {
        e.printStackTrace();
    } 
  } 
} 