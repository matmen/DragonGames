package listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import main.DragonGames;

public class PlayerInteract implements org.bukkit.event.Listener {
	private DragonGames plugin = DragonGames.INSTANCE;
	Random r = new Random();

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (this.plugin.dead.contains(p.getUniqueId()) || this.plugin.joinable) {

			e.setCancelled(true);

		} else {

			if ((p.getItemInHand().getType() == Material.COMPASS)
					&& ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))) {
				Player t = null;
				int dis = 200;

				for (Player all : Bukkit.getOnlinePlayers()) {
					if ((dis > all.getLocation().distance(p.getLocation())) && (all != p)
							&& (!this.plugin.dead.contains(all.getUniqueId())) && (!all.isSneaking())) {
						t = all;
						dis = (int) all.getLocation().distance(p.getLocation());
					}
				}

				if (t == null) {
					p.sendMessage(this.plugin.prefix + "Niemand in deiner Nähe!");
				} else {
					p.sendMessage(this.plugin.prefix + "Getrackt: §a" + t.getName() + " §c(" + dis + " Blöcke)");
					double x = t.getLocation().getX();
					double z = t.getLocation().getZ();
					int toAddX = this.r.nextInt(20);
					int toAddZ = this.r.nextInt(20);

					x += toAddX - toAddX / 2;
					z += toAddZ - toAddZ / 2;

					Location loc = new Location(t.getWorld(), x, 0.0D, z);

					p.setCompassTarget(loc);
				}
			} else if ((p.getItemInHand().getType() == Material.NETHER_STAR)
					&& ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))) {
				if (p.getItemInHand().getAmount() > 1) {
					p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
				} else {
					p.getInventory().remove(p.getItemInHand());
				}

				p.updateInventory();

				for (Player all : Bukkit.getOnlinePlayers()) {
					if ((all.getLocation().distance(p.getLocation()) <= 10.0D) && (all != p)
							&& (!this.plugin.dead.contains(all.getUniqueId()))) {
						all.getLocation().getWorld().createExplosion(all.getLocation(), 0.0F);
						all.setVelocity(all.getVelocity().multiply(-20).setY(1));
					}

					all.playSound(p.getLocation(), org.bukkit.Sound.WITHER_SHOOT,
							(float) (100.0D / all.getLocation().distance(p.getLocation())), 1.0F);
				}
			} else if ((p.getItemInHand().getType() == Material.NAME_TAG)
					&& ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))) {
				if (p.getItemInHand().getAmount() > 1) {
					p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
				} else {
					p.getInventory().remove(p.getItemInHand());
				}

				p.updateInventory();

				ItemStack rItem = (ItemStack) this.plugin.ilc.getItems(1).get(0);
				p.sendMessage(this.plugin.prefix + "Du hast §c" + (rItem.getItemMeta().getDisplayName() == null
						? rItem.getType().name() : rItem.getItemMeta().getDisplayName()) + " §7erhalten!");
				p.getInventory().addItem(new ItemStack[] { rItem });

				p.updateInventory();
			}

			if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.OBSIDIAN)) {
				p.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.SPEED, 60, 2));
				e.getClickedBlock().setType(Material.EMERALD_BLOCK);
				e.getClickedBlock().getLocation().getWorld().createExplosion(e.getClickedBlock().getLocation(), 0.0F);
			}
		}
	}
}