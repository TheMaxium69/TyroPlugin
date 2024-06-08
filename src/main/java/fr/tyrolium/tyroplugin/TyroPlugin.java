package fr.tyrolium.tyroplugin;

import fr.tyrolium.tyroplugin.skin.SkinCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class TyroPlugin extends JavaPlugin {

    private static Plugin plugin = null;
    private static ArrayList<String> playerVerif = new ArrayList<String>();

    @Override
    public void onEnable() {

        /*INIT PLUGIN*/
        System.out.println("§f[TyroPlugin] §aTyroPlugin enabled");
        plugin = TyroPlugin.getPlugin(TyroPlugin.class);
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        getServer().getPluginManager().registerEvents(new Listener() {

            // Player Connected
            @EventHandler
            public void onPlayerJoin(PlayerJoinEvent event) {

                String playerName = event.getPlayer().getName();

                /*SECURITY*/
                Runnable titleAnimation = new Runnable() {
                    int count = 0;

                    @Override
                    public void run() {
                        String title;
                        if (count % 3 == 0) {
                            title = "§4Chargement du serveur.";
                        } else if (count % 3 == 1) {
                            title = "§4Chargement du serveur..";
                        } else {
                            title = "§4Chargement du serveur...";
                        }

                        event.getPlayer().sendTitle(title, "", 10, 70, 20);
                        count++;
                        if (count < 3) {
                            executorService.schedule(this, 833, TimeUnit.MILLISECONDS);
                        }
                    }
                };
                executorService.schedule(titleAnimation, 0, TimeUnit.SECONDS);

                executorService.schedule(() -> {
                    /* SI N'EST PAS KICK PAR LE MOD ALORS IL EST VERIF */
//                    if (!playerName.equals("Luigi_Guyot")) {
                        playerVerif.add(playerName);
                        event.getPlayer().sendTitle("§aConnexion établie", "", 10, 70, 20);
                        // LAISSER LE MOD LE FAIRE ---- event.getPlayer().sendMessage("§f[TyroPlugin] §aConnexion établie !");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say tp " + playerName + " dans une autre dimension");
//                    }

                }, 3, TimeUnit.SECONDS);

                /*SKIN*/
                SkinCommand.setSkin(event.getPlayer());

            }


            // Player Takes Damage
            @EventHandler
            public void onPlayerDamage(EntityDamageEvent event) {
                if (event.getEntity() instanceof Player) {
                    String playerName = ((Player) event.getEntity()).getPlayer().getName();

                    if (playerVerif.contains(playerName)) {
                        event.setCancelled(false);
                    } else {
                        event.setCancelled(true);
                        ((Player) event.getEntity()).getPlayer().sendMessage("§f[TyroPlugin] §4Chargement du serveur, vous ne pouvez pas prendre de dégâts");
                    }
                }
            }

            // Player Drop Item
            @EventHandler
            public void onPlayerDropItem(PlayerDropItemEvent event) {
                String playerName = event.getPlayer().getName();
                // change condition as required
                if (playerVerif.contains(playerName)) {
                    event.setCancelled(false);
                } else {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("§f[TyroPlugin] §4Chargement du serveur, vous ne pouvez pas jeter d'objet");
                }
            }

            // Player Interact with Inventory
            @EventHandler
            public void onInventoryClick(InventoryClickEvent event) {
                String playerName = event.getWhoClicked().getName();
                if (playerVerif.contains(playerName)) {
                    event.setCancelled(false);
                } else {
                    event.setCancelled(true);
                    event.getWhoClicked().sendMessage("§f[TyroPlugin] §4Chargement du serveur, vous ne pouvez pas toucher votre inventaire");
                }
            }

            // Player Send Commande
            @EventHandler
            public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
                String playerName = event.getPlayer().getName();

                if (playerVerif.contains(playerName)) {
                    event.setCancelled(false);
                } else {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("§f[TyroPlugin] §4Chargement du serveur, vous ne pouvez pas excuter de commande");
                }

            }

            // Player Sends a Chat Message
            @EventHandler
            public void onPlayerChat(AsyncPlayerChatEvent event) {
                String playerName = event.getPlayer().getName();

                if (playerVerif.contains(playerName)) {
                    event.setCancelled(false);
                } else {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("§f[TyroPlugin] §4Chargement du serveur, vous ne pouvez pas envoyer de message");
                }
            }

            // Player Move
            @EventHandler
            public void onPlayerMove(PlayerMoveEvent event) {
                String playerName = event.getPlayer().getName();

                if (playerVerif.contains(playerName)) {
                    event.setCancelled(false);
                } else {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("§f[TyroPlugin] §4Chargement du serveur, vous ne pouvez pas bouger");
                }
            }

            // Player Interact
            @EventHandler
            public void onPlayerInteract(PlayerInteractEvent event) {
                String playerName = event.getPlayer().getName();

                if (playerVerif.contains(playerName)) {
                    event.setCancelled(false);
                } else {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("§f[TyroPlugin] §4Chargement du serveur, vous ne pouvez pas interagir");
                }
            }

            // Player Leaving
            @EventHandler
            public void onPlayerQuit(PlayerQuitEvent event) {
                String playerName = event.getPlayer().getName();
                playerVerif.remove(playerName);
            }

        }, plugin);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        System.out.println("§f[TyroPlugin] §4TyroPlugin disable");
    }

    public Plugin getInstance() {
        return plugin;
    }
}
