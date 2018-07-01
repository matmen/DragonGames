package crates;

import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import main.DragonGames;

public class CrateManager implements org.bukkit.event.Listener {
	private DragonGames plugin = DragonGames.INSTANCE;

	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Random r = new Random();

		if (this.plugin.dead.contains(p) || p.getGameMode() == GameMode.SPECTATOR || this.plugin.joinable) {
			e.setCancelled(true);
			return;
		}

		if (e.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK
				&& e.getClickedBlock().getType() == Material.DRAGON_EGG) {
			e.setCancelled(true);

			if (this.plugin.crates.containsKey(e.getClickedBlock().getLocation())) {

				p.openInventory((Inventory) this.plugin.crates.get(e.getClickedBlock().getLocation()));

			} else {

				if (r.nextInt(50) == 0) {
					p.setVelocity(p.getVelocity().setY(2));
					e.getClickedBlock().getWorld().createExplosion(e.getClickedBlock().getLocation(), 0.0F);
					return;
				}

				Inventory inv = this.plugin.ilc.getInventory(r.nextInt(6) + 1);

				this.plugin.crates.put(e.getClickedBlock().getLocation(), inv);
				p.openInventory(inv);

				org.bukkit.Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
					public void run() {

						e.getClickedBlock().getWorld().createExplosion(e.getClickedBlock().getLocation(), 0.0F);
						e.getClickedBlock().setType(Material.OBSIDIAN);

					}
				}, 100L);

			}

		}
	}
}
