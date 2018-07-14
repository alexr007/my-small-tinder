package ua.danit.utils;

import java.util.UUID;

public class GeneratorID {
    private int length;

    public static Integer generateNewID(){ //toDo: check Native Java implementation (UUID?)
        Integer id = Integer.parseInt(UUID.randomUUID().toString());
        return id;
    }

    public static void main(String[] args) {
        System.out.println(GeneratorID.generateNewID());
    }
}
