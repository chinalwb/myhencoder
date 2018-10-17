/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myjava.lesson04;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyDemo {
   public static void main(String[] args) throws IllegalArgumentException {
      InvocationHandler handler = new SampleInvocationHandler(new SampleClass()) ;
      SampleInterface proxy = (SampleInterface) Proxy.newProxyInstance(
         SampleInterface.class.getClassLoader(),
         new Class[] { SampleInterface.class },
         handler);
      int x = proxy.showMessage();
       System.out.println("x == " + x);
//      Class invocationHandler = Proxy.getInvocationHandler(proxy).getClass();
//
//      System.out.println(invocationHandler.getName());
   }
}

class SampleInvocationHandler implements InvocationHandler {

    Object target;
    public SampleInvocationHandler(Object target) {
        this.target = target;
    }

   @Override
   public Object invoke(Object proxy, Method method, Object[] args)
      throws Throwable {
      System.out.println("Before calling..");
      int x = (Integer) method.invoke(this.target);
       System.out.println("After calling!");
      return ++x;
   }
}

interface SampleInterface {
   int showMessage();
}

class SampleClass implements SampleInterface {
   public int showMessage(){
      System.out.println("Hello World");
      return 10;
   }
}
