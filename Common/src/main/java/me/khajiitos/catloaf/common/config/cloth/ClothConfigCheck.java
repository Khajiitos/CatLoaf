package me.khajiitos.catloaf.common.config.cloth;

public class ClothConfigCheck {
    public static boolean isInstalled() {
        try {
            Class.forName("me.shedaniel.clothconfig2.ClothConfigDemo");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}