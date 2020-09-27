package de.citycraft.plot;

import com.google.gson.Gson;
import de.citycraft.api.plot.PlotManager;

import java.io.*;

public class PlotConfiguration {

    public PlotConfiguration() {
        File folder = new File("plugins/CityCraft/");
        if(!folder.exists()) folder.mkdirs();
    }

    public void save(PlotManager plotManager) {
        File file = new File("plugins/CityCraft/plots.json");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(new Gson().toJson(plotManager));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public PlotManager get() {
        File file = new File("plugins/CityCraft/plots.json");
        if(file.exists()) {
            try {
                PlotManager plotManager = new Gson().fromJson(new FileReader(file), PlotManager.class);
                return plotManager;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
