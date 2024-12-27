package org.example.fotosappsaa.filters;

import java.awt.*;

public class BrightFilter {




    public static Color filter(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        int newRed = red + 50;
        int newGreen = green + 50;
        int newBlue = blue + 50;

        if (newRed > 255) {
            newRed = 255;
        }
        if (newGreen > 255) {
            newGreen = 255;
        }
        if (newBlue > 255) {
            newBlue = 255;
        }

        return new Color(newRed, newGreen, newBlue);
    }
}
