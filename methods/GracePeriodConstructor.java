package methods;

import org.bukkit.Bukkit;

import main.DragonGames;

public class GracePeriodConstructor {
	private DragonGames plugin = DragonGames.INSTANCE;

	public void init() {
		this.plugin.isGrace = true;

		this.plugin.graceCountdownID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
			public void run() {
				if ((GracePeriodConstructor.this.plugin.graceCountdown == 30)
						|| (GracePeriodConstructor.this.plugin.graceCountdown == 10)
						|| (GracePeriodConstructor.this.plugin.graceCountdown == 5)
						|| ((GracePeriodConstructor.this.plugin.graceCountdown <= 3)
								&& (GracePeriodConstructor.this.plugin.graceCountdown > 0))) {
					Bukkit.broadcastMessage(GracePeriodConstructor.this.plugin.prefix + "Die Schutzzeit endet in §c"
							+ GracePeriodConstructor.this.plugin.graceCountdown + " Sekunden§7!");
				} else if (GracePeriodConstructor.this.plugin.graceCountdown == 0) {
					GracePeriodConstructor.this.plugin.isGrace = false;
					Bukkit.broadcastMessage(GracePeriodConstructor.this.plugin.prefix + "Die Schutzzeit ist vorbei!");
					Bukkit.getScheduler().cancelTask(GracePeriodConstructor.this.plugin.graceCountdownID);
				}

				GracePeriodConstructor.this.plugin.graceCountdown -= 1;
			}
		}, 0L, 20L);
	}
}