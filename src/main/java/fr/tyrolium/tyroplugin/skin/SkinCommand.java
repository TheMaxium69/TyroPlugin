package fr.tyrolium.tyroplugin.skin;

import org.bukkit.entity.Player;

public class SkinCommand {

    public static void setSkin(Player player) {

        String pseudo = player.getName();


        player.performCommand("skin player " + pseudo);




    }

}
