package listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import main.DragonGames;

public class EntityDamageByEntity implements Listener {
	private DragonGames plugin = DragonGames.INSTANCE;

	@org.bukkit.event.EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if ((this.plugin.isGrace) || (this.plugin.dead.contains(e.getDamager().getUniqueId()))) {
			e.setCancelled(true);
		}
	}
}