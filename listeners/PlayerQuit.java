package listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import main.DragonGames;
import ru.tehkode.permissions.PermissionUser;

public class PlayerQuit implements Listener {
	private DragonGames plugin = DragonGames.INSTANCE;

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		PermissionUser pexu = ru.tehkode.permissions.bukkit.PermissionsEx.getUser(p);

		if (this.plugin.joinable) {
			e.setQuitMessage(pexu.getPrefix().replace("&", "§") + p.getName() + " §7hat das Spiel verlassen.");
			this.plugin.alive.remove(p.getUniqueId());
		}

		if (this.plugin.alive.contains(p.getUniqueId())) {
			if (!this.plugin.joinable) {
				e.getPlayer().setHealth(0.0D);
				e.setQuitMessage("");
			}
		} else {
			if (this.plugin.dead.contains(p.getUniqueId())) {
				this.plugin.dead.remove(p.getUniqueId());
			}
			e.setQuitMessage("");
		}
	}
}