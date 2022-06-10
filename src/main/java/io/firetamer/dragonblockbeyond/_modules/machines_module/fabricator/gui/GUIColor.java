package io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.gui;

public class GUIColor {
    int[] rgba = new int[4];

    public GUIColor(int r, int g, int b) {
        testColorValueRange(r, g, b, 255);

        rgba[0] = r;
        rgba[1] = g;
        rgba[2] = b;
        rgba[3] = 255;
    }

    public GUIColor(int r, int g, int b, int a) {
        testColorValueRange(r, g, b, 255);

        rgba[0] = r;
        rgba[1] = g;
        rgba[2] = b;
        rgba[3] = a;
    }

    public int[] getRGBA() { return rgba; }

    private static void testColorValueRange(int r, int g, int b, int a) {
        boolean rangeError = false;
        String badComponentString = "";

        if (r < 0 || r > 255) {
            rangeError = true;
            badComponentString = " Red";
        }
        if (g < 0 || g > 255) {
            rangeError = true;
            badComponentString = " Green";
        }
        if (b < 0 || b > 255) {
            rangeError = true;
            badComponentString = " Blue";
        }
        if (a < 0 || a > 255) {
            rangeError = true;
            badComponentString = " Alpha";
        }

        if (rangeError) {
            throw new IllegalArgumentException("DBBColor parameter outside of expected range:" + badComponentString);
        }
    }
}
