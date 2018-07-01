package listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommand implements Listener {
	@org.bukkit.event.EventHandler
	public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
		if ((e.getMessage().split(" ")[0].equalsIgnoreCase("/dg"))
				|| (e.getMessage().split(" ")[0].equalsIgnoreCase("/start"))
				|| (e.getMessage().split(" ")[0].equalsIgnoreCase("/forcestart"))
				|| (e.getMessage().split(" ")[0].equalsIgnoreCase("/stats"))
				|| (e.getPlayer().hasPermission("dg.admin"))) {
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
	}
}