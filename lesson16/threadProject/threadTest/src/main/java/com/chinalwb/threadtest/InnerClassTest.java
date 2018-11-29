package com.chinalwb.threadtest;

public class InnerClassTest {

    public static void main(String[] args) {
      InnerClassTest innerClassTest = new InnerClassTest();
      InnerClass innerClass = innerClassTest.new InnerClass();

      new InnerStaticClass();
    }

    String name;
    class InnerClass {
        int age;
    }

    static class InnerStaticClass {
        int count;
    }
}
