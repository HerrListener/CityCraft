package de.citycraft.listener;

import de.citycraft.MESSAGES;
import de.citycraft.api.CityCraftAPI;
import de.citycraft.plot.Plot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class BuildListener implements Listener {
    CityCraftAPI cityCraftAPI = CityCraftAPI.get();
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();

        if(!cityCraftAPI.getPlotManager().getPlotWorlds().contains(event.getBlockPlaced().getWorld().getName())) return;

        Plot plot = CityCraftAPI.get().getPlotManager().getPlotByLocation(event.getBlockPlaced().getLocation());

        if(plot != null) {
            if(!plot.isOwner(player.getUniqueId().toString())) {
                if(!player.hasPermission("citycraft.interact.plots") && !plot.isTrusted(player.getUniqueId().toString())) {
                    event.setCancelled(true);
                    player.sendMessage(MESSAGES.NOPERM.noPermission("citybuild.interact.plots"));
                }
            }
        } else {
            if(!player.hasPermission("citycraft.interact.roads")) {
                event.setCancelled(true);
                player.sendMessage(MESSAGES.NOPERM.noPermission("citybuild.interact.roads"));
            }
        }

    }

    @EventHandler
    public void onExlpode(EntityExplodeEvent event) {
        if(!cityCraftAPI.getPlotManager().getPlotWorlds().contains(event.getEntity().getWorld().getName())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPhysics(BlockPhysicsEvent event) {
        if(!cityCraftAPI.getPlotManager().getPlotWorlds().contains(event.getBlock().getWorld().getName())) return;
        Plot plot = CityCraftAPI.get().getPlotManager().getPlotByLocation(event.getBlock().getLocation());
        if(plot != null) event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();

        if(!cityCraftAPI.getPlotManager().getPlotWorlds().contains(event.getBlock().getWorld().getName())) return;

        Plot plot = CityCraftAPI.get().getPlotManager().getPlotByLocation(event.getBlock().getLocation());

        if(plot != null) {
            if(!plot.isOwner(player.getUniqueId().toString())) {
                if(!player.hasPermission("citycraft.interact.plots") && !plot.isTrusted(player.getUniqueId().toString())) {
                    event.setCancelled(true);
                    player.sendMessage(MESSAGES.NOPERM.noPermission("citybuild.interact.plots"));
                }
            }
        } else {
            if(!player.hasPermission("citycraft.interact.roads")) {
                event.setCancelled(true);
                player.sendMessage(MESSAGES.NOPERM.noPermission("citybuild.interact.roads"));
            }
        }

    }

}
