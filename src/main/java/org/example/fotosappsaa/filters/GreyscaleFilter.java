package org.example.fotosappsaa.filters;

import java.awt.*;

public class GreyscaleFilter {
    public static Color filter(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        int newRed = (int) (red * 0.299);
        int newGreen = (int) (green * 0.587);
        int newBlue = (int) (blue * 0.114);

        int grey = newRed + newGreen + newBlue;

        return new Color(grey, grey, grey);
    }
}
