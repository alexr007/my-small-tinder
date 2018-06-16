package ua.danit.utils;

public class GeneratorID {
    private int length;

    public static Integer generateNewID(){
        Integer source = (int)System.currentTimeMillis();
        String stringId = "";
        for (int i = 0; i < 6; i++) {
            source = source << 1;
        }
        for (int i = 0; i < 9; i++) {
            stringId += Math.abs(source % 10);
            source = (source / 10)*9;
        }
        Integer id = Integer.parseInt(stringId);
        return id;
    }

    public static void main(String[] args) {
        System.out.println(GeneratorID.generateNewID());
    }
}