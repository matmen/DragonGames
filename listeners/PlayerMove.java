package listeners;

import org.bukkit.Sound;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import main.DragonGames;

public class PlayerMove implements Listener {
	private DragonGames plugin = DragonGames.INSTANCE;

	@org.bukkit.event.EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (!this.plugin.joinable) {

			if ((e.getTo().getX() > this.plugin.maxMapSize / 2) || (e.getTo().getX() <= -(this.plugin.maxMapSize / 2))
					|| (e.getTo().getZ() >= this.plugin.maxMapSize / 2)
					|| (e.getTo().getZ() <= -(this.plugin.maxMapSize / 2))) {

				if (e.getTo().getX() > this.plugin.maxMapSize / 2) {
					e.getPlayer().setVelocity(new Vector(-1.0D, 0.2D, 0.0D));
				}

				if (e.getTo().getX() < -(this.plugin.maxMapSize / 2)) {
					e.getPlayer().setVelocity(new Vector(1.0D, 0.2D, 0.0D));
				}

				if (e.getTo().getZ() > this.plugin.maxMapSize / 2) {
					e.getPlayer().setVelocity(new Vector(0.0D, 0.2D, -1.0D));
				}

				if (e.getTo().getZ() < -(this.plugin.maxMapSize / 2)) {
					e.getPlayer().setVelocity(new Vector(0.0D, 0.2D, 1.0D));
				}

				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.WITHER_SHOOT, 100.0F, 50.0F);

				e.getPlayer().sendMessage("§cDu hast das Mapende erreicht!");
			}
		}
	}
}