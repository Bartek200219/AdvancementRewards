package pl.kabzteam.bartek.advancementrewards;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;

import java.util.*;
import java.util.logging.Level;

public final class AdvancementRewards extends JavaPlugin implements Listener {


    private static Economy econ = null;
    private static Map<String, Object> prizes = new HashMap<>();
    private static Map<String, Object> messages = new HashMap<>();
    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, String.format("Disabled Version %s", getDescription().getVersion()));
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new AdvancementDone(),this);
        if (!setupEconomy() ) {
            getLogger().log(Level.SEVERE, "Disabled due to no Vault/Economy dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.saveDefaultConfig();
        if (!this.getConfig().isSet("advancements")){
            var advIterate = Bukkit.advancementIterator();
            TreeMap<String, Integer> advancementMap = new TreeMap<>();
            while (advIterate.hasNext()) {
                var adv = advIterate.next().getKey().getKey();
                if (adv.contains("recipes/") || adv.contains("/root")) continue;
                advancementMap.put(adv, this.getConfig().getInt("defaultPrize"));
            }
            this.getConfig().createSection("advancements",advancementMap);
            this.saveConfig();
        }
        prizes = this.getConfig().getConfigurationSection("advancements").getValues(false);
        messages = this.getConfig().getConfigurationSection("messages").getValues(false);
        this.getServer().getPluginManager().addPermission(new Permission("advancementRewards.allow", PermissionDefault.TRUE));
        getLogger().log(Level.INFO, "Successfully enabled!");
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Map<String, Object> getPrizes(){
        return prizes;
    }

    public static Map<String, Object> getMessages() {
        return messages;
    }
}
