package org.example;

public class Buffer {

    private static FractionNumber currentBuffer = new FractionNumber(0, 1);

    public static void copy(FractionNumber toCopy){
        currentBuffer = toCopy;
    }

    public static FractionNumber read(){
        return currentBuffer;
    }
}
