package de.citycraft.plot;

import com.google.common.collect.Lists;
import de.citycraft.api.CityCraftAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

public class Plot {

    private String ownerUUID, world;
    private int maxX, minX, maxZ, minZ, id;
    private List<String> trustedPlayers;



    public Plot(String world, int maxX, int minX, int maxZ, int minZ) {
        this.world = world;
        this.id = CityCraftAPI.get().getPlotManager().getPlots().size();
        this.ownerUUID = null;
        this.maxX = maxX;
        this.maxZ = maxZ;
        this.minX = minX;
        this.minZ = minZ;
        this.trustedPlayers = Lists.newArrayList();
    }

    public boolean isInside(Location location) {
        if(location.getWorld().getName().equalsIgnoreCase(this.world)) {
            if(location.getBlockX() > this.minX && location.getBlockX() < this.maxX) {
                if(location.getBlockZ() > this.minZ && location.getBlockZ() < this.maxZ) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean isTrusted(String uuid) {
        return this.trustedPlayers.contains(uuid);
    }

    public boolean joinPlot(Location from, Location to) {
        if(!isInside(from) && isInside(to)) {
            return true;
        }
        return false;
    }

    public List<String> getTrustedPlayers() {
        return trustedPlayers;
    }

    public String getOwnerUUID() {
        return ownerUUID;
    }

    public String getWorld() {
        return world;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinX() {
        return minX;
    }

    public boolean isClaimed() {
        return this.ownerUUID != null;
    }

    public void setOwnerUUID(String ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public boolean isOwner(String uuid) {
        if(this.ownerUUID == null) return false;
        return this.ownerUUID.equalsIgnoreCase(uuid);
    }

    public long clearPlot() {

        long currentTime = System.currentTimeMillis();

        for(int x = this.minX+1; x < this.maxX; x++) {
            for(int z = this.minZ+1; z < this.maxZ; z++) {
                for(int y = 1; y < Bukkit.getWorld(this.world).getMaxHeight(); y++) {
                    if(y < 15) {
                        Bukkit.getWorld(this.world).getBlockAt(x,y,z).setType(Material.DIRT);
                    } else if(y == 15) {
                        Bukkit.getWorld(this.world).getBlockAt(x,y,z).setType(Material.GRASS);
                    } else {
                        Bukkit.getWorld(this.world).getBlockAt(x,y,z).setType(Material.AIR);
                    }
                }
            }
        }

        return System.currentTimeMillis() - currentTime;

    }

    public Location getPlotMid() {

        int x = this.maxX - this.minX;
        x = this.maxX - x;

        int z = this.maxZ - this.minZ;
        z = this.maxZ - z;

        Location location = new Location(Bukkit.getWorld(this.world), this.maxX, 16, this.maxZ);

        return location;

    }

    public int getId() {
        return id;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public int getMinZ() {
        return minZ;
    }
}
