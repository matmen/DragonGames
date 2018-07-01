package methods;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import main.DragonGames;

public class GameStopConstructor {
	private DragonGames plugin;

	public GameStopConstructor(DragonGames plugin) {
		this.plugin = plugin;
	}

	public void stopGame() {
		Bukkit.broadcastMessage(this.plugin.prefix + "Der Server startet in 20 Sekunden neu!");

		Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
			public void run() {
				for (Player all : Bukkit.getOnlinePlayers()) {
					all.kickPlayer("§cDer Server startet neu!");
				}
				Bukkit.shutdown();
			}
		}, 400L);
	}
}