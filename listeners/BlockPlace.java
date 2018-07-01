package listeners;

import org.bukkit.event.block.BlockPlaceEvent;

import main.DragonGames;

public class BlockPlace implements org.bukkit.event.Listener {
	private DragonGames plugin = DragonGames.INSTANCE;

	@org.bukkit.event.EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getBlock().getType() != org.bukkit.Material.WORKBENCH
				|| plugin.dead.contains(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}
}