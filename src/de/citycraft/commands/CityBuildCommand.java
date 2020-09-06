package de.citycraft.commands;

import de.citycraft.MESSAGES;
import de.citycraft.api.CityCraftAPI;
import de.citycraft.generation.PlotWorldGenerator;
import de.citycraft.plot.Plot;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
                if(!player.hasPermission("ciytcraft.create.world")) {
                    player.sendMessage(MESSAGES.NOPERM.noPermission("citybuild.create.world"));
                    return true;
                }
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
                    player.sendMessage(MESSAGES.PREFIX.getText()+"§4Dies ist keine gültige Eingabe");
                }

            } else if(args[0].equalsIgnoreCase("add")) {
                Plot plot = CityCraftAPI.get().getPlotManager().getPlotByLocation(player.getLocation());
                if(plot == null) {
                    player.sendMessage(MESSAGES.PREFIX.getText()+"§4Du befindest dich auf keinem Plot");
                    return true;
                }

                if(plot.isOwner(player.getUniqueId().toString())) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if(target==null) {
                        player.sendMessage(MESSAGES.PREFIX.getText()+"§4Dieser Spieler ist nicht online");
                        return true;
                    }
                    if(plot.isTrusted(target.getUniqueId().toString())) {
                        player.sendMessage(MESSAGES.PREFIX.getText()+"§4Dieser Spieler wurde bereits hinzugefügt");
                        return true;
                    }
                    player.sendMessage(MESSAGES.PREFIX.getText()+"§aDer Spieler wurde erfolgreich hinzugefügt.");
                } else {
                    if(player.hasPermission("citybuild.admin")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target==null) {
                            player.sendMessage(MESSAGES.PREFIX.getText()+"§4Dieser Spieler ist nicht online");
                            return true;
                        }
                        if(plot.isTrusted(target.getUniqueId().toString())) {
                            player.sendMessage(MESSAGES.PREFIX.getText()+"§4Dieser Spieler wurde bereits hinzugefügt");
                            return true;
                        }
                        plot.getTrustedPlayers().add(target.getUniqueId().toString());
                        player.sendMessage(MESSAGES.PREFIX.getText()+"§aDer Spieler wurde erfolgreich hinzugefügt.");
                    } else {
                        player.sendMessage(MESSAGES.PREFIX.getText()+"§4Dir gehört das Grundstück nicht.");
                    }
                }

            } else if(args[0].equalsIgnoreCase("remove")) {
                Plot plot = CityCraftAPI.get().getPlotManager().getPlotByLocation(player.getLocation());
                if(plot == null) {
                    player.sendMessage(MESSAGES.PREFIX.getText()+"§4Du befindest dich auf keinem Plot");
                    return true;
                }

                if(plot.isOwner(player.getUniqueId().toString())) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    if(!plot.isTrusted(target.getUniqueId().toString())) {
                        player.sendMessage(MESSAGES.PREFIX.getText()+"§4Dieser Spieler wurde nicht hinzugefügt");
                        return true;
                    }
                    player.sendMessage(MESSAGES.PREFIX.getText()+"§aDer Spieler wurde erfolgreich entfernt.");
                } else {
                    if(player.hasPermission("citybuild.admin")) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                        if(!plot.isTrusted(target.getUniqueId().toString())) {
                            player.sendMessage(MESSAGES.PREFIX.getText()+"§4Dieser Spieler wurde nicht hinzugefügt");
                            return true;
                        }
                        player.sendMessage(MESSAGES.PREFIX.getText()+"§aDer Spieler wurde erfolgreich entfernt.");
                        plot.getTrustedPlayers().remove(target.getUniqueId().toString());
                    } else {
                        player.sendMessage(MESSAGES.PREFIX.getText()+"§4Dir gehört das Grundstück nicht.");
                    }
                }

            }

        } else if(args.length == 1) {

            if(args[0].equalsIgnoreCase("claim")) {

                Plot plot = CityCraftAPI.get().getPlotManager().getPlotByLocation(player.getLocation());

                if(plot == null) {
                    player.sendMessage(MESSAGES.PREFIX.getText()+"§4Du befindest dich auf keinem Plot");
                    return true;
                }

                if(!plot.isClaimed()) {
                    player.sendMessage(MESSAGES.PREFIX.getText()+"§aDieses Grundstück gehört nun dir");
                    plot.setOwnerUUID(player.getUniqueId().toString());
                } else {
                    player.sendMessage(MESSAGES.PREFIX.getText()+"§4Das Plot ist bereits in besitz");
                }

            } else if(args[0].equalsIgnoreCase("clear")) {

                Plot plot = CityCraftAPI.get().getPlotManager().getPlotByLocation(player.getLocation());

                if(plot == null) {
                    player.sendMessage(MESSAGES.PREFIX.getText()+"§4Du befindest dich auf keinem Plot");
                    return true;
                }

                if(!plot.isOwner(player.getUniqueId().toString())) {
                    if(player.hasPermission("citycraft.plots.clear")) {
                        long timeMS = plot.clearPlot();
                        player.sendMessage(MESSAGES.PREFIX.getText()+"§7Das leeren des Grundstückes hat §e"+timeMS+"ms §7benötigt");
                    } else {
                        player.sendMessage(MESSAGES.PREFIX.getText()+"§4Dieses Grundstück gehört nicht dir");
                        return true;
                    }
                } else {
                    long timeMS = plot.clearPlot();
                    player.sendMessage(MESSAGES.PREFIX.getText()+"§7Das leeren des Grundstückes hat §e"+timeMS+"ms §7benötigt");
                }

            } else if(args[0].equalsIgnoreCase("save")) {
                if(player.hasPermission("citycraft.save")) {
                    CityCraftAPI cityCraftAPI = CityCraftAPI.get();
                    cityCraftAPI.getConfiguration().save(cityCraftAPI.getPlotManager());
                    player.sendMessage("§a§oWurde gespeichert.");
                } else {
                    player.sendMessage(MESSAGES.NOPERM.noPermission("citybuild.save"));
                }
            }

        }

        return false;
    }
}
