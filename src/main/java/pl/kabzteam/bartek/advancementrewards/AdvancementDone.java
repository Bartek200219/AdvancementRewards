package pl.kabzteam.bartek.advancementrewards;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AdvancementDone implements Listener {

    @EventHandler
    public void onPlayerEvent(PlayerAdvancementDoneEvent e){
        Player p = e.getPlayer();
        if(!p.hasPermission("advancementRewards.allow")) return;
        String advancementName = e.getAdvancement().getKey().getKey();
        var prizes = AdvancementRewards.getPrizes();
        if (prizes.containsKey(advancementName)) {
            Integer prize = (Integer) AdvancementRewards.getPrizes().get(advancementName);
            Economy econ = AdvancementRewards.getEconomy();

            var messages = AdvancementRewards.getMessages();
            EconomyResponse r = econ.depositPlayer(p, prize);
            if(r.transactionSuccess()) {
                String message = (String) messages.get("advancementPayment");
                p.sendMessage(String.format(message, econ.format(r.amount)));
            } else {
                String message = (String) messages.get("advancementPaymentFail");
                p.sendMessage(String.format(message, r.errorMessage));
            }
        }
    }
}
