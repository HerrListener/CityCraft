package de.citycraft;

import de.citycraft.api.CityCraftAPI;
import de.citycraft.commands.CityBuildCommand;
import de.citycraft.generation.PlotChunkGenerator;
import de.citycraft.listener.BuildListener;
import de.citycraft.listener.MoveListener;
import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class CityCraft extends JavaPlugin {

    @Override
    public void onEnable() {

        Arrays.asList(CityBuildCommand.class).forEach(clazz -> {
            try {
                clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        new CityCraftAPI();

        getServer().getPluginManager().registerEvents(new MoveListener(), this);
        getServer().getPluginManager().registerEvents(new BuildListener(), this);

    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new PlotChunkGenerator();
    }
}
