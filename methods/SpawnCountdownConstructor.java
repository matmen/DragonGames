package methods;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import main.DragonGames;

public class SpawnCountdownConstructor {
	DragonGames plugin = DragonGames.INSTANCE;

	public void init() {
		if (this.plugin.teleportCountdownID != -1) {
			return;
		}
		this.plugin.teleportCountdownID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {

			public void run() {
				SpawnCountdownConstructor.this.plugin.teleportCountdown -= 1;

				for (Player all : Bukkit.getOnlinePlayers()) {
					if (SpawnCountdownConstructor.this.plugin.teleportCountdown >= 0) {
						all.setLevel(SpawnCountdownConstructor.this.plugin.teleportCountdown);
					}
				}

				if ((SpawnCountdownConstructor.this.plugin.teleportCountdown == 10)
						|| (SpawnCountdownConstructor.this.plugin.teleportCountdown == 5)
						|| ((SpawnCountdownConstructor.this.plugin.teleportCountdown <= 3)
								&& (SpawnCountdownConstructor.this.plugin.teleportCountdown > 0))) {
					for (Player all : Bukkit.getOnlinePlayers()) {
						all.sendMessage(SpawnCountdownConstructor.this.plugin.prefix + "Das Spiel beginnt in §c"
								+ SpawnCountdownConstructor.this.plugin.teleportCountdown + " Sekunden§7!");
						all.playSound(all.getLocation(), org.bukkit.Sound.NOTE_PLING, 100.0F, 1.0F);
					}

				} else if (SpawnCountdownConstructor.this.plugin.teleportCountdown == 0) {
					if (Bukkit.getOnlinePlayers().size() >= SpawnCountdownConstructor.this.plugin.minPlayerCount) {
						GracePeriodConstructor grace = new GracePeriodConstructor();
						grace.init();

						PlayerTeleportConstructor playertp = new PlayerTeleportConstructor();
						playertp.teleportPlayers(SpawnCountdownConstructor.this.plugin);

						SpawnCountdownConstructor.this.plugin.joinable = false;

						Bukkit.getScheduler().cancelTask(SpawnCountdownConstructor.this.plugin.teleportCountdownID);
					} else {
						Bukkit.broadcastMessage(SpawnCountdownConstructor.this.plugin.prefix
								+ "Zu wenig Spieler online, starte Countdown neu!");
						SpawnCountdownConstructor.this.plugin.teleportCountdown = 60;
					}

				}
			}
		}, 0L, 20L);
	}
}