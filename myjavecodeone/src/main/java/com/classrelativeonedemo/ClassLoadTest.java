package com.classrelativeonedemo;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;


public class ClassLoadTest {

    public static void main(String[] args) throws IOException {

        ClassLoadTest.getBootstrap();
        ClassLoadTest.getSystemLoad();
    }

    public static void  getBootstrap(){
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (URL url:urls){
            System.out.println(url);
        }
    }


    public static void getSystemLoad() throws IOException {
      Enumeration<URL> em = ClassLoader.getSystemClassLoader().getResources("");
      while (em.hasMoreElements()){
          System.out.println(em.nextElement());
      }
    }
}
