package de.citycraft.listener;

import de.citycraft.api.CityCraftAPI;
import de.citycraft.api.packets.Title;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        CityCraftAPI cityCraftAPI = CityCraftAPI.get();

        cityCraftAPI.getPlotManager().getPlots().forEach(plot -> {
            if(plot.joinPlot(event.getFrom(), event.getTo())) {
                if(plot.getOwnerUUID() == null) {
                    Title.sendTitleAndSubtitle(player, "§7Plot #§e"+plot.getId(), "§cNot claimed");
                } else {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(plot.getOwnerUUID()));
                    Title.sendTitleAndSubtitle(player, "§7Plot #§e"+plot.getId(), "§a"+offlinePlayer.getName());
                }
            }
        });

    }

}
