package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import crates.CrateManager;
import crates.ItemListConstructor;
import listeners.BlockBreak;
import listeners.BlockPlace;
import listeners.ChatFormat;
import listeners.EntityDamage;
import listeners.EntityDamageByEntity;
import listeners.FoodLevelChange;
import listeners.PlayerCommand;
import listeners.PlayerDeath;
import listeners.PlayerDropItem;
import listeners.PlayerInteract;
import listeners.PlayerJoin;
import listeners.PlayerLogin;
import listeners.PlayerMove;
import listeners.PlayerPickupItem;
import listeners.PlayerQuit;
import listeners.ServerListPing;
import methods.SpawnCountdownConstructor;

public class DragonGames extends JavaPlugin {
	public static DragonGames INSTANCE;
	public String prefix = "§7[§cDragonGames§7] ";

	public ArrayList<UUID> dead = new ArrayList<UUID>();
	public ArrayList<UUID> alive = new ArrayList<UUID>();
	public HashMap<Location, Inventory> crates = new HashMap<Location, Inventory>();

	public ItemListConstructor ilc = new ItemListConstructor();

	public File cfgFile = new File("plugins/DragonGames", "cfg.yml");
	public FileConfiguration cfg = YamlConfiguration.loadConfiguration(this.cfgFile);

	public PluginDescriptionFile pdf = getDescription();

	public boolean joinable;
	public boolean canMove;

	public boolean isGrace;
	public int graceCountdownID;
	public int graceCountdown;
	public int teleportCountdownID;
	public int teleportCountdown;
	public int minPlayerCount;
	public int maxMapSize;
	public int maxItemsInCrate;
	private Boolean silentRL = Boolean.valueOf(false);

	public double getKD(Player p) {
		int k = 0;
		int d = 1;

		if (this.cfg.isSet("stats.kill." + p.getUniqueId())) {
			k = this.cfg.getInt("stats.kill." + p.getUniqueId());
		}

		if (this.cfg.isSet("stats.death." + p.getUniqueId())) {
			d = this.cfg.getInt("stats.death." + p.getUniqueId());
		}

		double kd = Math.round(k / d * 100.0D) / 100.0D;

		return kd;
	}

	public int getWonMatches(Player p) {
		int w = 0;

		if (this.cfg.isSet("stats.won." + p.getUniqueId())) {
			w = this.cfg.getInt("stats.won." + p.getUniqueId());
		}

		return w;
	}

	public int getKills(Player p) {
		int k = 0;

		if (this.cfg.isSet("stats.kill." + p.getUniqueId())) {
			k = this.cfg.getInt("stats.kill." + p.getUniqueId());
		}

		return k;
	}

	public int getDeaths(Player p) {
		int d = 1;

		if (this.cfg.isSet("stats.death." + p.getUniqueId())) {
			d = this.cfg.getInt("stats.death." + p.getUniqueId());
		}

		return d;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("dg")) {
			if (args.length == 0) {
				p.sendMessage(this.prefix + "DragonGames §cv" + this.pdf.getVersion() + "§7 by matmen");
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("setlobby")) {
					if (p.hasPermission("dg.admin")) {
						this.cfg.set("DG.Lobby.World", p.getLocation().getWorld().getName());
						this.cfg.set("DG.Lobby.X", Double.valueOf(p.getLocation().getX()));
						this.cfg.set("DG.Lobby.Y", Double.valueOf(p.getLocation().getY()));
						this.cfg.set("DG.Lobby.Z", Double.valueOf(p.getLocation().getZ()));
						this.cfg.set("DG.Lobby.Yaw", Float.valueOf(p.getLocation().getYaw()));
						this.cfg.set("DG.Lobby.Pitch", Float.valueOf(p.getLocation().getPitch()));
						try {
							this.cfg.save(this.cfgFile);
							p.sendMessage(this.prefix + "Lobby gesetzt!");
						} catch (IOException e) {
							e.printStackTrace();
							p.sendMessage(this.prefix + "§cFehler: " + e.getMessage());
						}
					} else {
						p.sendMessage(this.prefix + "Dazu hast du §ckeine Rechte§7!");
					}
				} else if (args[0].equalsIgnoreCase("simulatedeath")) {
					this.alive.remove(p.getUniqueId());
					this.dead.add(p.getUniqueId());
					p.sendMessage("§cSimulated Death!");
				} else if (args[0].equalsIgnoreCase("silentreload")) {
					this.silentRL = Boolean.valueOf(!this.silentRL.booleanValue());
					p.sendMessage("§7SilentRL: " + (this.silentRL.booleanValue() ? "§aAn" : "§cAus"));
				} else if (args[0].equalsIgnoreCase("setjoinable")) {
					if (p.hasPermission("dg.admin")) {
						this.joinable = (!this.joinable);
						p.sendMessage("§cjoinable §7set to §c" + this.joinable);
					} else {
						p.sendMessage(this.prefix + "Dazu hast du §ckeine Rechte§7!");
					}
				} else if (args[0].equalsIgnoreCase("status")) {
					sender.sendMessage("§cjoinable: §4" + this.joinable + "\n§ccanMove: §4" + this.canMove
							+ "\n§cisGrace: §4" + this.isGrace + "\n§cgraceCountdown: §4" + this.graceCountdown
							+ "\n§cteleportCountdown: §4" + this.teleportCountdown + "\n§cminPlayerCount: §4"
							+ this.minPlayerCount + "\n§calive: §4" + this.alive.size() + "\n§cdead: §4"
							+ this.dead.size() + "\n§ccratesOpened: §4" + this.crates.size() + "\n§cGameID: §4"
							+ this.ilc.seed);
				} else if (args[0].equalsIgnoreCase("seed")) {
					sender.sendMessage("§7Seed: §c" + this.ilc.seed);
				} else {
					p.sendMessage(this.prefix + "Invalid arguments!");
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("mpc")) {
					if (p.hasPermission("dg.admin")) {
						this.minPlayerCount = Integer.parseInt(args[1]);
						p.sendMessage("§cminPlayerCount §7set to §c" + this.minPlayerCount);
					} else {
						p.sendMessage(this.prefix + "Dazu hast du §ckeine Rechte§7!");
					}
				} else {
					p.sendMessage(this.prefix + "Unbekannte Argumente!");
				}
			}
		} else if ((cmd.getName().equalsIgnoreCase("start")) || (cmd.getName().equalsIgnoreCase("forcestart"))) {
			if (p.hasPermission("dg.start")) {
				if (this.teleportCountdown > 11)
					this.teleportCountdown = 11;
				SpawnCountdownConstructor mtp = new SpawnCountdownConstructor();
				mtp.init();
				p.sendMessage(this.prefix + "Die Runde wird gestartet...!");
			} else {
				p.sendMessage(this.prefix + "Dazu hast du §ckeine Rechte§7!");
			}
		} else if (cmd.getName().equalsIgnoreCase("stats")) {
			Player p2;

			if (args.length == 0) {
				p2 = p;
			} else if (args.length == 1) {
				p2 = Bukkit.getPlayer(args[0]);
				if (p2 == null) {
					p.sendMessage("§c" + args[0] + " §7ist nicht online!");
					return true;
				}
			} else {
				p.sendMessage("§c/info [Name]");
				return true;
			}

			CraftPlayer cp = (CraftPlayer) p2;
			p.sendMessage(this.prefix + "Spielerinfo: §c" + p2.getName());
			p.sendMessage("§7Ping: §c" + cp.getHandle().ping + "ms");
			p.sendMessage("§7K/D: §c" + getKD(p2));
			p.sendMessage("§7Kills: §c" + getKills(p2));
			p.sendMessage("§7Tode: §c" + getDeaths(p2));
			p.sendMessage("§7Gewonnene Spiele: §c" + getWonMatches(p2));
		}

		return true;
	}

	public void onDisable() {
		if (this.silentRL.booleanValue()) {
			return;
		}

		for (Player all : Bukkit.getOnlinePlayers()) {
			all.kickPlayer("§cDer Server startet neu!");
		}

		for (BukkitTask b : Bukkit.getScheduler().getPendingTasks()) {
			if (b.getOwner() == this) {
				b.cancel();
			}
		}
	}

	public void onEnable() {
		INSTANCE = this;

		this.joinable = true;
		this.isGrace = true;
		this.canMove = true;

		this.teleportCountdown = 60;
		this.graceCountdown = 30;
		this.teleportCountdownID = -1;
		this.graceCountdownID = -1;
		this.minPlayerCount = 2;

		this.maxMapSize = 300;
		this.maxItemsInCrate = 7;

		this.ilc.setupList();

		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new CrateManager(), this);
		pm.registerEvents(new BlockBreak(), this);
		pm.registerEvents(new BlockPlace(), this);
		pm.registerEvents(new PlayerDeath(), this);
		pm.registerEvents(new PlayerDropItem(), this);
		pm.registerEvents(new EntityDamage(), this);
		pm.registerEvents(new EntityDamageByEntity(), this);
		pm.registerEvents(new FoodLevelChange(), this);
		pm.registerEvents(new PlayerInteract(), this);
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new PlayerQuit(), this);
		pm.registerEvents(new PlayerLogin(), this);
		pm.registerEvents(new PlayerMove(), this);
		pm.registerEvents(new ChatFormat(), this);
		pm.registerEvents(new PlayerCommand(), this);
		pm.registerEvents(new PlayerPickupItem(), this);
		pm.registerEvents(new ServerListPing(), this);
	}
}
