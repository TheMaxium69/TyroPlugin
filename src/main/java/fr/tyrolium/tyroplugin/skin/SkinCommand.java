package fr.tyrolium.tyroplugin.skin;

import org.bukkit.entity.Player;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SkinCommand {

    public static void setSkin(Player player) {

        String pseudo = player.getName();









        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            player.performCommand("skin player " + pseudo);
            System.out.println("skin changement");
        }, 5, TimeUnit.SECONDS);




    }

}
