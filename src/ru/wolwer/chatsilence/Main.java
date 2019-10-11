package ru.wolwer.chatsilence;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin implements Listener {



  public void onEnable() {
    getLogger().info(" ");
    getLogger().info("ChatSilence Enabled!");
    getLogger().info(" ");

    //register events
    getServer().getPluginManager().registerEvents(this, this);


    //Setup default config
    getConfig().options().copyDefaults();
    saveDefaultConfig();


    if(!getDataFolder().exists()){
      getDataFolder().mkdir();
    }

  }



  @EventHandler
  public void chatSilence(AsyncPlayerChatEvent e) {
    Player p = e.getPlayer();
    if(getConfig().getString("Silence-status").equalsIgnoreCase("enabled")){
      if(!p.hasPermission(getConfig().getString("Silence-bypass-permission"))) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Silence-enabled-message")));
        e.setCancelled(true);
      }
    }
  }
  
  public void onDisable() {
    getLogger().info(" ");
    getLogger().info("ChatSilence disabled");
    getLogger().info(" ");
    saveConfig();
  }


  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equals("silence")) {
      if (sender.hasPermission(getConfig().getString("Silence-command-permission"))) {
        if (getConfig().getString("Silence-status").equalsIgnoreCase("enabled")) {
          getConfig().set("Silence-status", "disabled");
          Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Silence-disable-message")));
          saveConfig();
        } else if (getConfig().getString("Silence-status").equalsIgnoreCase("disabled")) {
          getConfig().set("Silence-status", "enabled");
          saveConfig();
          Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Silence-enable-message")));
        }

      } else {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Silence-no-permission-cmd")));
      }
    }

    return true;
  }
}
