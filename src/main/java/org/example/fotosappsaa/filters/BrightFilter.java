package org.example.fotosappsaa.filters;

import java.awt.*;

public class BrightFilter {




    public static Color filter(Color color) {
        int brightnessFactor = 50;
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        int newRed = red + brightnessFactor;
        int newGreen = green + brightnessFactor;
        int newBlue = blue + brightnessFactor;

        red = Math.min(255, red + brightnessFactor);
        green = Math.min(255, green + brightnessFactor);
        blue = Math.min(255, blue + brightnessFactor);

        Color newColor = new Color(red, green, blue);

        return newColor;
    }
}
