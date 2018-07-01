package listeners;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import main.DragonGames;
import methods.GameStopConstructor;

public class PlayerDeath implements org.bukkit.event.Listener {
	private DragonGames plugin = DragonGames.INSTANCE;

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.getEntity().setHealth(20.0D);
		e.getEntity().setFoodLevel(20);

		for (ItemStack stack : e.getDrops()) {
			e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), stack);
		}

		e.getDrops().clear();

		this.plugin.alive.remove(e.getEntity().getUniqueId());
		this.plugin.dead.add(e.getEntity().getUniqueId());

		e.getEntity().setGameMode(GameMode.SPECTATOR);
		e.getEntity().setVelocity(e.getEntity().getVelocity().setY(2));
		setKD(e.getEntity());
		if (e.getEntity().getKiller() != null) {
			e.getEntity().setSpectatorTarget(e.getEntity().getKiller());
			e.getEntity().sendMessage("§7Du spectatest jetzt §c" + e.getEntity().getKiller().getName());
		}

		if (this.plugin.alive.size() <= 1) {
			e.setDeathMessage("");
			Bukkit.broadcastMessage(this.plugin.prefix + "§c" + e.getEntity().getName() + " §7ist ausgeschieden!");
			this.plugin.isGrace = true;
			UUID tw = null;

			for (UUID all : this.plugin.alive) {
				tw = all;
			}

			Player winner = Bukkit.getPlayer(tw);

			if (winner == null) {
				Bukkit.broadcastMessage(this.plugin.prefix
						+ "§cNiemand §7hat das Spiel gewonnen! Warum auch immer. matmen kann nicht programmieren.");
			} else {
				Bukkit.broadcastMessage(this.plugin.prefix + "§c" + winner.getName() + " §7hat das Spiel gewonnen!");
				this.plugin.cfg.set(winner.getUniqueId() + ".stats.won",
						Integer.valueOf(this.plugin.cfg.getInt(winner.getUniqueId() + ".stats.won") + 1));
				try {
					this.plugin.cfg.save(this.plugin.cfgFile);
				} catch (IOException ex) {
					winner.sendMessage(
							this.plugin.prefix + "Deine gewonnenen Matches §ckonnten §lnicht gespeichert §cwerden§7!");
					winner.sendMessage("§cReporte dies einem Admin: §7\n" + new Date() + ":  " + ex.toString());
				}
			}

			GameStopConstructor gs = new GameStopConstructor(this.plugin);
			gs.stopGame();
		} else {
			e.setDeathMessage(this.plugin.prefix + "§c" + e.getEntity().getName() + " §7ist ausgeschieden! §c"
					+ this.plugin.alive.size() + " Spieler §7verbleiben.");
		}
	}

	private void setKD(Player death) {
		if (this.plugin.cfg.isSet(death.getUniqueId() + ".stats.death")) {
			int newD = this.plugin.cfg.getInt(death.getUniqueId() + ".stats.death") + 1;
			this.plugin.cfg.set(death.getUniqueId() + ".stats.death", Integer.valueOf(newD));
		} else {
			this.plugin.cfg.set(death.getUniqueId() + ".stats.death", Integer.valueOf(1));
		}

		if (death.getKiller() != null) {
			if (this.plugin.cfg.isSet(death.getKiller().getUniqueId() + ".stats.kill")) {
				int newK = this.plugin.cfg.getInt(death.getKiller().getUniqueId() + ".stats.kill") + 1;
				this.plugin.cfg.set(death.getKiller().getUniqueId() + ".stats.kill", Integer.valueOf(newK));
			} else {
				this.plugin.cfg.set(death.getKiller().getUniqueId() + ".stats.kill", Integer.valueOf(1));
			}
		}
		try {
			this.plugin.cfg.save(this.plugin.cfgFile);
		} catch (IOException ex) {
			death.sendMessage(this.plugin.prefix + "Deine Tode §ckonnten §lnicht gespeichert §cwerden§7!");
			death.sendMessage("§cReporte dies einem Admin: §7\n" + new Date() + ":  " + ex.toString());
			if (death.getKiller() != null) {
				death.getKiller()
						.sendMessage(this.plugin.prefix + "Deine Kills §ckonnten §lnicht gespeichert §cwerden§7!");
				death.getKiller().sendMessage("§cReporte dies einem Admin: §7\n" + new Date() + ":  " + ex.toString());
			}

			ex.printStackTrace();
		}
	}
}