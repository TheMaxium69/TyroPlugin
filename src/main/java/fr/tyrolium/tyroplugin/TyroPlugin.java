package fr.tyrolium.tyroplugin;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class TyroPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("TyroPlugin enabled");


// Dispatch a command from another plugin
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "broadcast salut je suis TyroPlugin");


        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPlayerJoin(PlayerJoinEvent event) {
                event.getPlayer().sendMessage("Welcome to the server!");
//                event.getPlayer().send("Welcome to the server!");
            }
        }, this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        System.out.println("TyroPlugin disable");
    }
}
