package methods;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import main.DragonGames;

public class ChestRefillConstructor {
	DragonGames plugin = DragonGames.INSTANCE;

	public void initChestRefill() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
			public void run() {
				for (Location l : ChestRefillConstructor.this.plugin.crates.keySet()) {
					l.getBlock().setType(org.bukkit.Material.DRAGON_EGG);
					l.getWorld().createExplosion(l, 0.0F);
				}

				ChestRefillConstructor.this.plugin.crates.clear();
				Bukkit.broadcastMessage(ChestRefillConstructor.this.plugin.prefix + "Die Kisten wurden befüllt!");
			}
		}, 12000L, 12000L);
	}
}