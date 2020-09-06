package de.citycraft.api.packets;

import net.minecraft.server.v1_16_R2.IChatBaseComponent;
import net.minecraft.server.v1_16_R2.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Title {

    public static void sendTitleAndSubtitle(Player player, String tt, String sb) {

        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
                IChatBaseComponent.ChatSerializer.a("{\"text\": \""+tt+"\"}"));

        PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
                IChatBaseComponent.ChatSerializer.a("{\"text\": \""+sb+"\"}"));

        Arrays.asList(title, subtitle).forEach(p -> {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(p);
        });

    }

}
