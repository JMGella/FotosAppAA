package org.example.fotosappsaa.task;


import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.example.fotosappsaa.filters.BrightFilter;
import org.example.fotosappsaa.filters.GreyscaleFilter;
import org.example.fotosappsaa.filters.InvertedFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ExecutorService;


public class ServiceManager extends Service<BufferedImage> {



    private BufferedImage image;
    private List<String> selectedFilters;
    private ExecutorService executor;





    public ServiceManager(BufferedImage image, List<String> selectedFilters) {
        this.image = image;
        this.selectedFilters = selectedFilters;


    }


    @Override
    protected Task<BufferedImage> createTask() {
        return new Task() {

            @Override
            protected BufferedImage call() throws Exception {
                if (image == null) {
                    throw new IllegalArgumentException("No se ha proporcionado ninguna imagen para procesar.");
                }

                int totalProcessedPixels = 0;
                updateMessage("Aplicando filtro...");
                int imageSize = image.getHeight() * image.getWidth();


                for (int y = 0; y < image.getHeight(); y++) {

                    Thread.sleep(10);

                    for (int x = 0; x < image.getWidth(); x++) {

                        Color color = new Color(image.getRGB(x, y));
                        for (String selectedFilter : selectedFilters) {
                            if (selectedFilter.equals("GREYSCALE")) {
                                color = GreyscaleFilter.filter(color);
                            } else if (selectedFilter.equals("BRIGHT")) {
                                color = BrightFilter.filter(color);
                            } else if (selectedFilter.equals("INVERTED")) {
                                color = InvertedFilter.filter(color);
                            }
                        }
                        if (color != null) {
                            image.setRGB(x, y, color.getRGB());
                        }
                        totalProcessedPixels++;
                    }

                    updateProgress(totalProcessedPixels, imageSize);

                }


                updateMessage("Filtro aplicado");


                return image;
            }
        };


    }
    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
        super.setExecutor(executor);
    }
    @Override
    public void start() {
        if (executor != null) {
            setExecutor(executor);
        }
        super.start();
    }








}
