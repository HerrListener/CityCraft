package de.citycraft.generation;

import de.citycraft.api.CityCraftAPI;
import de.citycraft.plot.Plot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.List;
import java.util.Random;

public class PlotChunkGenerator extends ChunkGenerator {

    CityCraftAPI cityCraftAPI = CityCraftAPI.get();


    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {

        ChunkData chunkData = createChunkData(world);

        int plotSizeX = 90;
        int plotSizeZ = 90;

        int walkSize = 6;

        int diffX = plotSizeX + walkSize;
        int diffZ = plotSizeZ + walkSize;

        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                chunkData.setBlock(x,0,z,Material.BEDROCK);
            }
        }

        for(int y = 0; y < 16; y++) {
            for(int x = 0; x < 16; x++) {
                for(int z = 0; z < 16; z++) {
                    if(y == 15) {
                        chunkData.setBlock(x,y,z,Material.GRASS);
                    } else {
                        chunkData.setBlock(x,y,z, Material.DIRT);
                    }
                }
            }
        }

        int realX = (chunkX * 16);
        int realZ = (chunkZ * 16);

        int minX = 0;
        int minZ = 0;

        int maxX = 0;
        int maxZ = 0;


        if(realX < 0)
            realX = diffX + realX % diffX;
        if(realZ < 0)
            realZ = diffZ + realZ % diffZ;

        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {

                long relX = (realX + x) % diffX;
                long relZ = (realZ + z) % diffZ;

                if(relX < plotSizeX && relZ < plotSizeZ) {
                    chunkData.setBlock(x, 15, z, Material.GRASS);
                } else {
                    chunkData.setBlock(x, 15, z, Material.WOOD);
                }

                if(relX == 0 && relZ < plotSizeZ) {
                    chunkData.setBlock(x, 16, z, Material.STONE_SLAB2);
                }

                if(relX == plotSizeX && relZ < plotSizeZ) {
                    chunkData.setBlock(x, 16, z, Material.STONE_SLAB2);
                }

                if(relX < plotSizeX && relZ == 0) {
                    chunkData.setBlock(x, 16, z, Material.STONE_SLAB2);
                }

                if(relX <= plotSizeX && relZ == plotSizeZ) {
                    chunkData.setBlock(x, 16, z, Material.STONE_SLAB2);
                }


                if(relX == 0 && relZ == 0) {
                    minZ = realZ+z;
                    minX = realX+x;
                }

            }
        }

        maxZ = (minZ + plotSizeZ);
        maxX = (minX + plotSizeX);

        Plot plot = new Plot(world.getName(), maxX, minX, maxZ, minZ);
        if(cityCraftAPI.getPlotManager().getPlot(minX, minZ) == null) {
            cityCraftAPI.getPlotManager().getPlots().add(plot);
        }


        return chunkData;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return super.getDefaultPopulators(world);
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return super.getFixedSpawnLocation(world, random);
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return super.canSpawn(world, x, z);
    }

}
