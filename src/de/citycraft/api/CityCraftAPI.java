package de.citycraft.api;

import de.citycraft.api.plot.PlotManager;

import java.util.List;

public class CityCraftAPI {

    private static CityCraftAPI api;

    public static CityCraftAPI get() {
        return api;
    }

    private PlotManager plotManager;

    public CityCraftAPI() {
        api = this;
        this.plotManager = new PlotManager();
    }

    public PlotManager getPlotManager() {
        return plotManager;
    }
}
