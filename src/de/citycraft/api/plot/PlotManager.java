package de.citycraft.api.plot;

import com.google.common.collect.Lists;
import de.citycraft.plot.Plot;
import org.bukkit.Location;

import java.util.List;

public class PlotManager {

    private List<Plot> plots;
    private List<String> plotWorlds;

    public PlotManager() {
        this.plots = Lists.newArrayList();
        this.plotWorlds = Lists.newArrayList();
    }

    public List<String> getPlotWorlds() {
        return plotWorlds;
    }

    public List<Plot> getPlots() {
        return plots;
    }

    public Plot getPlot(int x, int z) {
        for(Plot plot : plots) {
            if(plot.getMinX() == x && plot.getMinZ() == z) {
                return plot;
            }
        }
        return null;
    }

    public Plot getPlotById(int id) {
        for(Plot plot : plots) {
            if(plot.getId() == id) {
                return plot;
            }
        }
        return null;
    }

    public Plot getPlotByLocation(Location location) {
        for(Plot plot : plots) {
            if(plot.isInside(location)) {
                return plot;
            }
        }
        return null;
    }

}
