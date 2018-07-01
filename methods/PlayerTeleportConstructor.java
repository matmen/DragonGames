package methods;

import java.util.Date;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import main.DragonGames;

public class PlayerTeleportConstructor {
	public void teleportPlayers(DragonGames main) {
		Random r = new Random();

		World w = (World) Bukkit.getWorlds().get(0);
		Bukkit.getLogger().log(Level.INFO, "Using World: " + w.getName());

		ChestRefillConstructor crc = new ChestRefillConstructor();
		crc.initChestRefill();

		for (Player p : Bukkit.getOnlinePlayers()) {
			int x = r.nextInt(main.maxMapSize - 1) - (main.maxMapSize - 1) / 2;
			int z = r.nextInt(main.maxMapSize - 1) - (main.maxMapSize - 1) / 2;
			int y = w.getHighestBlockYAt(x, z) + 1;

			Location spawn = new Location(w, x, y, z);

			p.teleport(spawn);

			p.playSound(p.getLocation(), Sound.LEVEL_UP, 100.0F, 1.0F);
			p.getInventory().clear();

			main.cfg.set(p.getUniqueId() + ".lastGamePlayed", "[" + new Date() + "]: " + main.ilc.seed);
		}
	}
}