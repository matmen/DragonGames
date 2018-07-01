package listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import main.DragonGames;

public class EntityDamage implements Listener {
	private DragonGames plugin = DragonGames.INSTANCE;

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (this.plugin.isGrace) {
			e.setCancelled(true);
		} else if ((e.getCause() == EntityDamageEvent.DamageCause.FALL) && (e.getDamage() > 10.0D)) {
			e.setDamage(e.getDamage() / 4.0D);
		}
	}
}