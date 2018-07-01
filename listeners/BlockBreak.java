package listeners;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import main.DragonGames;

public class BlockBreak implements Listener {

	private DragonGames plugin = DragonGames.INSTANCE;

	@org.bukkit.event.EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Material type = e.getBlock().getType();

		Boolean canBuild = !plugin.dead.contains(e.getPlayer().getUniqueId());
		canBuild = canBuild && (type == Material.LOG || type == Material.LOG_2 || type == Material.LEAVES
				|| type == Material.LEAVES_2 || type == Material.WORKBENCH);

		if (!canBuild)
			e.setCancelled(true);
	}

}
