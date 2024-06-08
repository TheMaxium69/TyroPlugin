package fr.tyrolium.tyroplugin.skin;

import fr.tyrolium.tyroplugin.Global;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SkinCommand {

    public static void setSkin(Player player) {

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        String pseudo = player.getName();

        ArrayList<Object> stateSkin = SkinApi.getSkinInfo(pseudo);

//        System.out.println(stateSkin);
//        System.out.println(stateSkin.get(0));

        String skin = (String) stateSkin.get(0);
        String slim = (String) stateSkin.get(0);

        if (skin.equals("vide")) {

            executorService.schedule(() -> {
//                System.out.println("skin de "+ pseudo +" update");
                player.performCommand("skin player " + pseudo);

            }, 2, TimeUnit.SECONDS);

        } else if (skin.equals("err")) {

            executorService.schedule(() -> {
//                System.out.println("skin de "+ pseudo +" update");
                player.performCommand("skin player " + pseudo);

            }, 2, TimeUnit.SECONDS);

        } else {

            String urlSkin = Global.skin_url + skin;
            String textSlim;
            if (slim.equals("1")) {
                textSlim = "slim";
            } else {
                textSlim = "";
            }

//            System.out.println(urlSkin);

            executorService.schedule(() -> {
//                System.out.println("skin de "+ pseudo +" update");
                player.performCommand("skin url " + urlSkin + " " + textSlim);

            }, 2, TimeUnit.SECONDS);

        }



    }

}
