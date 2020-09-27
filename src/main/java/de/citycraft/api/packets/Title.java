package de.citycraft.api.packets;


import org.bukkit.entity.Player;

import java.util.Arrays;

public class Title {

    public static void sendTitleAndSubtitle(Player player, String tt, String sb) {

        player.sendTitle(tt, sb);

    }

}
