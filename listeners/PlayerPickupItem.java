package listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import main.DragonGames;

public class PlayerPickupItem implements Listener {
	private DragonGames plugin = DragonGames.INSTANCE;

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent e) {
		if ((this.plugin.canMove) && (!this.plugin.dead.contains(e.getPlayer().getUniqueId()))) {
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
	}
}