package org.example;

public class Memory {

    private static FractionNumber currentMemory = new FractionNumber(0, 1);

    public static void clear(){
        currentMemory = new FractionNumber(0, 1);
    }

    public static FractionNumber read(){
        return currentMemory;
    }

    public static void save(FractionNumber toSave){
        currentMemory = toSave;
    }

    public static void add(FractionNumber toAdd){
        currentMemory = currentMemory.add(toAdd);
    }
}
