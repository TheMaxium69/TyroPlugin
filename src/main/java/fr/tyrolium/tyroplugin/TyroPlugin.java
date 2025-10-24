package fr.tyrolium.tyroplugin;

import fr.tyrolium.tyroplugin.skin.SkinCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class TyroPlugin extends JavaPlugin implements Listener {

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

                Player player = event.getPlayer();
                String playerName = player.getName();

                World voidWorld = Bukkit.getWorld("world");
                World hubWorld = Bukkit.getWorld("world_hub");
                World faction1World = Bukkit.getWorld("world_faction1");
                World faction2World = Bukkit.getWorld("world_faction2");
                World minageWorld = Bukkit.getWorld("world_minage");
                World testWorld = Bukkit.getWorld("test");
                World test2World = Bukkit.getWorld("test2");

        /************************
                SECURITY
         ************************/

                if(voidWorld != null) {
                    player.teleport(voidWorld.getSpawnLocation());
                }

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
                        Bukkit.getScheduler().runTask(plugin, () -> {
//                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv tp " + playerName + " hub");
                            player.teleport(hubWorld.getSpawnLocation());
                        });

//                    }

                }, 3, TimeUnit.SECONDS);

        /************************
                SKIN
         ************************/
                SkinCommand.setSkin(event.getPlayer());



            }

        /************************
         HUB
         ************************/

            @EventHandler
            public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {


                World voidWorld = Bukkit.getWorld("world");
                World hubWorld = Bukkit.getWorld("world_hub");
                Player player = event.getPlayer();

                if (player.getWorld() == hubWorld) {
                    player.getInventory().clear();

                    Bukkit.getScheduler().runTask(plugin, () -> {
                       Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + player.getName() + " tyromod:tyrolium_sword 1 0 {display:{Name:Faction_Tyrolium}} ");
                    });
                    Bukkit.getScheduler().runTask(plugin, () -> {
                       Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + player.getName() + " tyromod:rhodonite_sword 1 0 {display:{Name:Faction_Rhodonite}} ");
                    });
                    Bukkit.getScheduler().runTask(plugin, () -> {
                       Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + player.getName() + " tyromod:amethys_pickaxe 1 0 {display:{Name:Minage}} ");
                    });

                } else if (player.getWorld() == voidWorld) {
                    player.getInventory().clear();
                }


            }


            @EventHandler
            public void onPlayerUseItem(PlayerInteractEvent event) {

                World voidWorld = Bukkit.getWorld("world");
                World hubWorld = Bukkit.getWorld("world_hub");
                World faction1World = Bukkit.getWorld("world_faction1");
                World faction2World = Bukkit.getWorld("world_faction2");
                World minageWorld = Bukkit.getWorld("world_minage");
                World testWorld = Bukkit.getWorld("test");
                World test2World = Bukkit.getWorld("test2");

                Player player = event.getPlayer();
                ItemStack item = event.getItem();

                if (item == null) return;

                if (player.getWorld() == hubWorld) {

                    if (item.getItemMeta().getDisplayName().equals("Faction_Tyrolium")) {
                        player.teleport(testWorld.getSpawnLocation());
                        player.sendMessage("§aTéléportation vers monde Faction Tyrolium !");

                    } else if (item.getItemMeta().getDisplayName().equals("Faction_Rhodonite")) {
                        player.teleport(test2World.getSpawnLocation());
                        player.sendMessage("§aTéléportation vers Monde Faction Rhodonite !");

                    } else if (item.getItemMeta().getDisplayName().equals("Minage")) {
                        player.teleport(minageWorld.getSpawnLocation());
                        player.sendMessage("§aTéléportation vers Monde Minage !");

                    }
                }
            }


        /************************
         SECURITY
         ************************/

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

    /************************
            COMMAND
     ************************/

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("hub")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Seul un joueur peut utiliser cette commande !");
                return true;
            }

            World hubWorld = Bukkit.getWorld("world_hub");
            Player player = (Player) sender;

            // Vérification de la permission
            if (!player.hasPermission("hubteleporter.use")) {
                player.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande !");
                return true;
            }

            Bukkit.getScheduler().runTask(plugin, () -> {
                player.teleport(hubWorld.getSpawnLocation());
            });

            player.sendMessage("§aTéléportation vers le hub !");
            return true;
        }
        return false;
    }







    /************************
     END
     ************************/

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        System.out.println("§f[TyroPlugin] §4TyroPlugin disable");
    }



    public Plugin getInstance() {
        return plugin;
    }
}
