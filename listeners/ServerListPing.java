package listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import main.DragonGames;

public class ServerListPing implements Listener {
	private DragonGames plugin = DragonGames.INSTANCE;

	@org.bukkit.event.EventHandler
	public void OnServerListPing(ServerListPingEvent e) {
		if (this.plugin.joinable) {
			e.setMotd("§aLobby");
		} else if ((this.plugin.alive.size() > 1) || (this.plugin.minPlayerCount == 0)) {
			e.setMotd("§cKampfphase");
		} else if (this.plugin.isGrace) {
			e.setMotd("§eFriedensphase");
		} else {
			e.setMotd("§eNeustart");
		}
	}
}