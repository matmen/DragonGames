package listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import main.DragonGames;

public class PlayerLogin implements Listener {
	private DragonGames plugin = DragonGames.INSTANCE;

	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		if (!this.plugin.joinable) {
			e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cDas Spiel läuft bereits!");
		}
	}
}