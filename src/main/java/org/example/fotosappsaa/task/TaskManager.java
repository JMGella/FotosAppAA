package org.example.fotosappsaa.task;


import javafx.concurrent.Task;
import org.example.fotosappsaa.filters.BrightFilter;
import org.example.fotosappsaa.filters.GreyscaleFilter;
import org.example.fotosappsaa.filters.InvertedFilter;

import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;

public class TaskManager extends Task<BufferedImage> {

    private BufferedImage image;
    private List<String> selectedFilters;

    public TaskManager(BufferedImage image, List<String> selectedFilters) {
        this.image = image;
        this.selectedFilters = selectedFilters;

    }




    @Override
    public BufferedImage call() throws Exception {
        if (image == null) {
            throw new IllegalArgumentException("No se ha proporcionado ninguna imagen para procesar.");
        }
        int totalProcessedPixels = 0;
        updateMessage("Aplicando filtro...");
        int imageSize = image.getHeight() * image.getWidth();
        float totalProcessed = 0f;

        for (int y = 0; y < image.getHeight(); y++) {
            Thread.sleep(10);
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                for (String selectedFilter : this.selectedFilters) {
                    switch (selectedFilter) {
                        case "GREYSCALE":
                            color = GreyscaleFilter.filter(color);
                            break;
                        case "BRIGHT":
                            color = BrightFilter.filter(color);
                            break;
                        case "INVERTED":
                            color = InvertedFilter.filter(color);
                            break;
                        default:
                            throw new IllegalArgumentException("Filtro no reconocido: " + selectedFilter);
                    }
                }
                image.setRGB(x, y, color.getRGB());
                totalProcessed++;
            }
            updateProgress(totalProcessed, imageSize);
        }
        updateMessage("Filtro aplicado");

        return image;
    }
}
