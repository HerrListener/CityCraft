package de.citycraft.generation;

import de.citycraft.api.CityCraftAPI;
import de.citycraft.plot.Plot;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

public class PlotWorldGenerator {

    private int plotsize;
    private Player player;

    public PlotWorldGenerator(Player player, int plotsize) {
        this.plotsize = plotsize;
        this.player = player;
    }

    public void generateWorld(String name) {

        this.player.sendMessage("§c§oDie Welt wird erstelllt, bei vollendung wirst du teleportiert.");

        WorldCreator worldCreator = new WorldCreator(name);
        worldCreator.type(WorldType.FLAT);
        worldCreator.generateStructures(false);
        worldCreator.generator(new PlotChunkGenerator());

        World world = worldCreator.createWorld();
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setGameRuleValue("doMobSpawning", "false");


        Plot plot = CityCraftAPI.get().getPlotManager().getPlotById(0);
        if(plot != null) {
            player.teleport(plot.getPlotMid());
            this.player.sendMessage("§a§oDie Welt wurde nun erstellt.");
        } else {
            this.player.sendMessage("§c§oEs ist ein Fehler aufgetreten.");
        }



    }

}
