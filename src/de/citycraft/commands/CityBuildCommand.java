package de.citycraft.commands;

import de.citycraft.api.CityCraftAPI;
import de.citycraft.generation.PlotWorldGenerator;
import de.citycraft.plot.Plot;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CityBuildCommand extends AbstractCommand {

    public CityBuildCommand() {
        super("citybuild", "Siehe alle Befehle", "/cb", "cb");
    }

    @Override
    public boolean command(CommandSender commandSender, String[] args) {

        if(!(commandSender instanceof Player)) return true;

        Player player = (Player) commandSender;

        if(args.length == 2) {

            if(args[0].equalsIgnoreCase("create")) {
                String worldName = args[1];
                CityCraftAPI.get().getPlotManager().getPlotWorlds().add(worldName);
                PlotWorldGenerator plotWorldGenerator = new PlotWorldGenerator(player, 20);
                plotWorldGenerator.generateWorld(worldName);
            }

            if(args[0].equalsIgnoreCase("tp")) {

                try {
                    int id = Integer.parseInt(args[1]);
                    Plot plot = CityCraftAPI.get().getPlotManager().getPlotById(id);
                    player.teleport(plot.getPlotMid());
                } catch (Exception e) {

                }

            }

        } else if(args.length == 1) {

            if(args[0].equalsIgnoreCase("claim")) {

                Plot plot = CityCraftAPI.get().getPlotManager().getPlotByLocation(player.getLocation());

                if(plot == null) {
                    player.sendMessage("§4§oDu befindest dich auf keinem Plot");
                    return true;
                }

                if(!plot.isClaimed()) {
                    player.sendMessage("§a§oDu hast dieses Grundstück geclaimt");
                    plot.setOwnerUUID(player.getUniqueId().toString());
                } else {
                    player.sendMessage("§4§oDieses Plot gehört schon jemandem");
                }

            } else if(args[0].equalsIgnoreCase("clear")) {

                Plot plot = CityCraftAPI.get().getPlotManager().getPlotByLocation(player.getLocation());

                if(plot == null) {
                    player.sendMessage("§4§oDu befindest dich auf keinem Plot");
                    return true;
                }

                if(!plot.isOwner(player.getUniqueId().toString())) {
                    if(player.hasPermission("citycraft.plots.clear")) {
                        long timeMS = plot.clearPlot();
                        player.sendMessage("§7§oDas leeren des Grundstückes hat §e§o"+timeMS+"ms §7§ogedauert.");
                    } else {
                        player.sendMessage("§4§oDu bist nicht der Inhaber dieses Grundstückes");
                        return true;
                    }
                } else {
                    long timeMS = plot.clearPlot();
                    player.sendMessage("§7§oDas leeren des Grundstückes hat §e§o"+timeMS+"ms §7§ogedauert.");
                }

            } else if(args[0].equalsIgnoreCase("save")) {
                CityCraftAPI cityCraftAPI = CityCraftAPI.get();
                cityCraftAPI.getConfiguration().save(cityCraftAPI.getPlotManager());
                player.sendMessage("§a§oWurde gespeichert.");
            }

        }

        return false;
    }
}
