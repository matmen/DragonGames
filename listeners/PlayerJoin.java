package listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

import main.DragonGames;
import methods.SpawnCountdownConstructor;
import ru.tehkode.permissions.PermissionUser;

public class PlayerJoin implements org.bukkit.event.Listener {
	private DragonGames plugin = DragonGames.INSTANCE;

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		PermissionUser pexu = ru.tehkode.permissions.bukkit.PermissionsEx.getUser(p);

		if (this.plugin.joinable) {
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.updateInventory();

			for (PotionEffect pE : p.getActivePotionEffects()) {
				p.removePotionEffect(pE.getType());
			}

			p.setHealth(20.0D);
			p.setLevel(0);
			p.setFoodLevel(20);
			p.setFlying(false);
			p.setAllowFlight(false);
			p.setGameMode(GameMode.SURVIVAL);

			e.setJoinMessage(pexu.getPrefix().replace("&", "§") + p.getName() + " §7hat das Spiel betreten.");
			String listName = pexu.getPrefix().substring(pexu.getPrefix().length() - 2, pexu.getPrefix().length())
					.replace("&", "§") + p.getName();
			if (listName.length() > 16) {
				listName = listName.substring(0, 16);
			}
			p.setPlayerListName(listName);

			this.plugin.alive.add(p.getUniqueId());

			org.bukkit.World w = Bukkit.getWorld(this.plugin.cfg.getString("DG.Lobby.World"));
			double x = this.plugin.cfg.getDouble("DG.Lobby.X");
			double y = this.plugin.cfg.getDouble("DG.Lobby.Y");
			double z = this.plugin.cfg.getDouble("DG.Lobby.Z");
			float yaw = (float) this.plugin.cfg.getDouble("DG.Lobby.Yaw");
			float pitch = (float) this.plugin.cfg.getDouble("DG.Lobby.Pitch");

			p.teleport(new org.bukkit.Location(w, x, y, z, yaw, pitch));

			if ((Bukkit.getOnlinePlayers().size() == this.plugin.minPlayerCount)
					&& (this.plugin.teleportCountdownID == -1)) {
				SpawnCountdownConstructor mtp = new SpawnCountdownConstructor();
				mtp.init();
			}
		} else {
			e.setJoinMessage("");
			e.getPlayer().kickPlayer("§cDas Spiel läuft bereits!");
		}
	}
}