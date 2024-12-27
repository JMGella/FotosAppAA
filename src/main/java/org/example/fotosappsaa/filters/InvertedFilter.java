package org.example.fotosappsaa.filters;

import java.awt.*;

public class InvertedFilter {
    public static Color filter(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        int newRed = 255 - red;
        int newGreen = 255 - green;
        int newBlue = 255 - blue;

        return new Color(newRed, newGreen, newBlue);
    }
}
