package de.citycraft.api;

import de.citycraft.api.plot.PlotManager;
import de.citycraft.plot.PlotConfiguration;

import java.util.List;

public class CityCraftAPI {

    private static CityCraftAPI api;

    public static CityCraftAPI get() {
        return api;
    }

    private PlotManager plotManager;
    private PlotConfiguration configuration;

    public CityCraftAPI() {
        api = this;
        this.configuration = new PlotConfiguration();

        this.plotManager = (this.configuration.get() == null) ? new PlotManager() : this.configuration.get();
    }

    public PlotConfiguration getConfiguration() {
        return configuration;
    }

    public PlotManager getPlotManager() {
        return plotManager;
    }
}
