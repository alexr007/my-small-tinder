package ua.danit.utils;

import java.util.UUID;

public class GeneratorID {
    private int length;

    public static Long generateNewID(){
        UUID myuuid = UUID.randomUUID();
        long highbits = myuuid.getMostSignificantBits();
        long lowbits = myuuid.getLeastSignificantBits();

        return highbits * lowbits;
    }

    public static void main(String[] args) {
        UUID myuuid = UUID.randomUUID();
        long highbits = myuuid.getMostSignificantBits();
        long lowbits = myuuid.getLeastSignificantBits();
        System.out.println("My UUID is: " + highbits + " " + lowbits);
        System.out.println("My UUID is: " + highbits * lowbits);
        System.out.println(myuuid);
    }
}
