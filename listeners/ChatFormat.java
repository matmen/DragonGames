package listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import main.DragonGames;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatFormat implements Listener {
	private DragonGames plugin = DragonGames.INSTANCE;

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		PermissionUser pexu = PermissionsEx.getUser(p);

		if (p.hasPermission("dg.admin")) {
			e.setMessage(e.getMessage().replace("&", "§"));
		}
		if ((this.plugin.dead.contains(p.getUniqueId())) && (!this.plugin.isGrace)) {
			e.setCancelled(true);
			for (UUID u : this.plugin.dead) {
				Player t = Bukkit.getPlayer(u);
				t.sendMessage(
						"§7§o*TOT* §r" + pexu.getPrefix().replace("&", "§") + p.getName() + "§r§7: " + e.getMessage());
			}
		} else {
			e.setFormat(pexu.getPrefix().replace("&", "§") + "%1$s§7: %2$s");
		}

		String listName = pexu.getPrefix().substring(pexu.getPrefix().length() - 2, pexu.getPrefix().length())
				.replace("&", "§") + p.getName();
		if (listName.length() > 16) {
			listName = listName.substring(0, 16);
		}

		p.setPlayerListName(listName);
	}
}