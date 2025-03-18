package org.example;

public class Buffer {

    private static String currentBuffer = "";

    public static void copy(String toCopy){
        currentBuffer = toCopy;
    }

    public static String read(){
        return currentBuffer;
    }

    public static void clear(){
        currentBuffer = "";
    }
}
