package org.example.fotosappsaa.task;


import javafx.concurrent.Task;
import org.example.fotosappsaa.filters.BrightFilter;
import org.example.fotosappsaa.filters.GreyscaleFilter;
import org.example.fotosappsaa.filters.InvertedFilter;

import java.awt.*;
import java.util.List;
import java.io.BufferedInputStream;
import java.awt.image.BufferedImage;

public class TaskManager extends Task<BufferedImage> {

    private BufferedImage image;
    private List<String> selectedFilters;

    public TaskManager(BufferedImage image, List<String> selectedFilters) {
        this.image = image;
        this.selectedFilters = selectedFilters;

    }




    @Override
    protected BufferedImage call() throws Exception {
        int totalProcessedPixels = 0;
        updateMessage("Procesando imagen...");
        int imageSize = image.getHeight() * image.getWidth();
        float totalProcessed = 0f;

        for (int y =0; y < image.getHeight(); y++) {
            Thread.sleep(10);
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                for (String selectedFilter: this.selectedFilters){
                    if (selectedFilter.equals("GREYSCALE")){
                        color = GreyscaleFilter.filter(color);
                    }
                    if (selectedFilter.equals("BRIGHT")){
                        color = BrightFilter.filter(color);
                    }
                    if (selectedFilter.equals("INVERTED")){
                        color = InvertedFilter.filter(color);
                    }

                }




//
//                if (isCancelled()) {
//                    updateMessage("Proceso cancelado");
//                    return null;
//                }
                totalProcessedPixels++;
                updateProgress(totalProcessedPixels, imageSize);
                totalProcessed = totalProcessedPixels / (float) imageSize;
                updateMessage("Procesando imagen... " + (int) ((totalProcessed / imageSize) * 100) + "%");
            }
        }

        updateProgress(totalProcessedPixels, imageSize);
        updateMessage("Proceso completado");
        return image;
    }
}
