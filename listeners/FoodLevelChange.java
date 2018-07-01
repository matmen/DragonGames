package listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import main.DragonGames;

public class FoodLevelChange implements Listener {
	private DragonGames plugin = DragonGames.INSTANCE;

	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		if (this.plugin.isGrace) {
			e.setCancelled(true);
		} else {
			e.setCancelled(false);
		}
	}
}