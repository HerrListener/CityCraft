package de.citycraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.lang.reflect.Field;
import java.util.Arrays;

public abstract class AbstractCommand extends BukkitCommand {

    public AbstractCommand(String commandName) {
            super(commandName);
            try {
                getCommandMap().register(commandName, (Command)this);
            } catch (IllegalArgumentException|IllegalAccessException|NoSuchFieldException|SecurityException e) {
                e.printStackTrace();
            }
        }

        public AbstractCommand(String commandName, String description, String usage, String... alisases) {
            super(commandName, description, usage, Arrays.asList(alisases));
            try {
                getCommandMap().register(commandName, (Command)this);
            } catch (IllegalArgumentException|IllegalAccessException|NoSuchFieldException|SecurityException e) {
                e.printStackTrace();
            }
        }

        public boolean execute(CommandSender sender, String alias, String[] args) {
            return command(sender, args);
        }

        public abstract boolean command(CommandSender commandSender, String[] args);

        public CommandMap getCommandMap() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            return (CommandMap)bukkitCommandMap.get(Bukkit.getServer());
        }

}