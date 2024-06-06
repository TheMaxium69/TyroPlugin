package fr.tyrolium.tyroplugin;

import fr.tyrolium.tyroplugin.skin.SkinCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class TyroPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("TyroPlugin enabled");


        // Excuter
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "broadcast salut je suis TyroPlugin");


        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPlayerJoin(PlayerJoinEvent event) {
                event.getPlayer().sendMessage("Bienvenue sur TyroServ !");

                SkinCommand.setSkin(event.getPlayer());

            }
        }, this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        System.out.println("TyroPlugin disable");
    }
}
