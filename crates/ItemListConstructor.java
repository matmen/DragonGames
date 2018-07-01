package crates;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemListConstructor {

	public long seed;

	private ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	Random r = new Random();

	public void setupList() {
		this.seed = System.currentTimeMillis();
		this.r.setSeed(this.seed);
		Bukkit.getLogger().info("Using Seed: " + this.seed);

		long start = System.currentTimeMillis();

		ItemStack schlagstock = new ItemStack(Material.STICK);
		ItemMeta schlagIM = schlagstock.getItemMeta();
		schlagIM.setDisplayName("§cSchlagstock");
		schlagIM.addEnchant(org.bukkit.enchantments.Enchantment.KNOCKBACK, 2, true);
		schlagstock.setItemMeta(schlagIM);

		ItemStack aura = new ItemStack(Material.NETHER_STAR);
		ItemMeta auraIM = schlagstock.getItemMeta();
		auraIM.setDisplayName("§cKraftfeld");
		aura.setItemMeta(auraIM);

		ItemStack random = new ItemStack(Material.NAME_TAG);
		ItemMeta randomIM = random.getItemMeta();
		randomIM.setDisplayName("§cZufallsitem");
		random.setItemMeta(randomIM);

		ItemStack tracker = new ItemStack(Material.COMPASS);
		ItemMeta trackIM = tracker.getItemMeta();
		trackIM.setDisplayName("§cTracker");
		tracker.setItemMeta(trackIM);

		for (int i = 595; i >= 0; i--) {
			this.items.add(new ItemStack(Material.WOOD_SWORD));
		}
		for (int i = 198; i >= 0; i--) {
			this.items.add(new ItemStack(Material.IRON_SWORD));
		}
		for (int i = 20; i >= 0; i--) {
			this.items.add(new ItemStack(Material.DIAMOND_SWORD));
		}
		for (int i = 99; i >= 0; i--) {
			this.items.add(new ItemStack(Material.DIAMOND_AXE));
		}
		for (int i = 496; i >= 0; i--) {
			this.items.add(new ItemStack(Material.BOW));
		}
		for (int i = 694; i >= 0; i--) {
			this.items.add(new ItemStack(Material.ARROW, 4));
		}
		for (int i = 397; i >= 0; i--) {
			this.items.add(new ItemStack(Material.STONE_SWORD));
		}

		for (int i = 78; i >= 0; i--) {
			this.items.add(new ItemStack(Material.DIAMOND_HELMET));
		}
		for (int i = 20; i >= 0; i--) {
			this.items.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
		}
		for (int i = 23; i >= 0; i--) {
			this.items.add(new ItemStack(Material.DIAMOND_LEGGINGS));
		}
		for (int i = 62; i >= 0; i--) {
			this.items.add(new ItemStack(Material.DIAMOND_BOOTS));
		}
		for (int i = 133; i >= 0; i--) {
			this.items.add(new ItemStack(Material.IRON_HELMET));
		}
		for (int i = 156; i >= 0; i--) {
			this.items.add(new ItemStack(Material.IRON_CHESTPLATE));
		}
		for (int i = 117; i >= 0; i--) {
			this.items.add(new ItemStack(Material.IRON_LEGGINGS));
		}
		for (int i = 78; i >= 0; i--) {
			this.items.add(new ItemStack(Material.IRON_BOOTS));
		}
		for (int i = 156; i >= 0; i--) {
			this.items.add(new ItemStack(Material.GOLD_HELMET));
		}
		for (int i = 234; i >= 0; i--) {
			this.items.add(new ItemStack(Material.GOLD_CHESTPLATE));
		}
		for (int i = 273; i >= 0; i--) {
			this.items.add(new ItemStack(Material.GOLD_LEGGINGS));
		}
		for (int i = 312; i >= 0; i--) {
			this.items.add(new ItemStack(Material.GOLD_BOOTS));
		}
		for (int i = 156; i >= 0; i--) {
			this.items.add(new ItemStack(Material.LEATHER_HELMET));
		}
		for (int i = 273; i >= 0; i--) {
			this.items.add(new ItemStack(Material.LEATHER_CHESTPLATE));
		}
		for (int i = 273; i >= 0; i--) {
			this.items.add(new ItemStack(Material.LEATHER_LEGGINGS));
		}
		for (int i = 156; i >= 0; i--) {
			this.items.add(new ItemStack(Material.LEATHER_BOOTS));
		}

		for (int i = 446; i >= 0; i--) {
			this.items.add(new ItemStack(Material.BREAD, this.r.nextInt(3) + 1));
		}
		for (int i = 335; i >= 0; i--) {
			this.items.add(new ItemStack(Material.COOKED_BEEF, this.r.nextInt(3) + 1));
		}
		for (int i = 446; i >= 0; i--) {
			this.items.add(new ItemStack(Material.MUSHROOM_SOUP));
		}
		for (int i = 558; i >= 0; i--) {
			this.items.add(new ItemStack(Material.MELON, this.r.nextInt(3) + 1));
		}
		for (int i = 335; i >= 0; i--) {
			this.items.add(new ItemStack(Material.GRILLED_PORK, this.r.nextInt(3) + 1));
		}
		for (int i = 156; i >= 0; i--) {
			this.items.add(new ItemStack(Material.GOLDEN_APPLE, this.r.nextInt(2) + 1));
		}
		for (int i = 223; i >= 0; i--) {
			this.items.add(new ItemStack(Material.GOLDEN_CARROT));
		}

		for (int i = 625; i >= 0; i--) {
			this.items.add(tracker);
			this.items.add(schlagstock);
			this.items.add(aura);
			this.items.add(random);
		}

		long end = System.currentTimeMillis();

		Bukkit.getLogger().info(this.items.size() + " Items added to System! Took " + (end - start) + "ms");
	}

	public ArrayList<ItemStack> getItems() {
		return this.items;
	}

	public ArrayList<ItemStack> getItems(int n) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();

		int count = this.r.nextInt(n);

		while (count >= 0) {
			int randomItem = this.r.nextInt(getItems().size());
			ItemStack itemToAdd = (ItemStack) getItems().get(randomItem);
			items.add(itemToAdd);
			count--;
		}

		return items;
	}

	public Inventory getInventory(int itemsToAdd) {
		Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST);

		ArrayList<ItemStack> items = getItems(itemsToAdd);

		for (ItemStack i : items) {
			inventory.setItem(this.r.nextInt(InventoryType.CHEST.getDefaultSize()), i);
		}

		return inventory;
	}
}
