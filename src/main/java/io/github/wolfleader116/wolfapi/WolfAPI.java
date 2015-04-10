package io.github.wolfleader116.wolfapi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import net.minecraft.server.v1_8_R2.EnumParticle;
import net.minecraft.server.v1_8_R2.PacketPlayOutWorldParticles;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class WolfAPI extends JavaPlugin {

	public static WolfAPI plugin;
	
	@Override
	public void onEnable() {
		File db = new File(getDataFolder(), "itemdatabase.yml");
		if(!db.exists()) {
			db.getParentFile().mkdirs();
			copy(getResource("itemdatabase.yml"), db);
		} else {
			Config dab = new Config("itemdatabase", this);
			if (dab.getConfig().getInt("Version") != 1) {
				db.delete();
				db.getParentFile().mkdirs();
				copy(getResource("itemdatabase.yml"), db);
			}
		}
		plugin = this;
	}
	
	@Override
	public void onDisable() {
		plugin = null;
	}

	private void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while((len=in.read(buf))>0) {
				out.write(buf,0,len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void particle(EnumParticle particle, boolean boo, float xcent, float ycent, float zcent, int xoff, int yoff, int zoff, int speed, int amount, Player player) {
		PacketPlayOutWorldParticles particlepacket = new PacketPlayOutWorldParticles(particle, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(particlepacket);
	}

	public static ItemStack createItem(Material material, int amount, short data, String name, String lore, String ench) {
		ItemStack item = new ItemStack(material, amount, data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		String[] lors = lore.split("\n");
		List<String> lores = Arrays.asList(lors);
		meta.setLore(lores);
		item.setItemMeta(meta);
		String[] enchs = ench.split(",");
		for(int i = 0; i < enchs.length; i++) {
			String[] enchinfo = enchs[i].split(":");
			Enchantment enchdata = Enchantment.getByName(enchinfo[0].toUpperCase());
			item.addUnsafeEnchantment(enchdata, Integer.parseInt(enchinfo[1]));
		}
		return item;
	}
	
	public static String getItemName(String input) {
		Config dab = new Config("itemdatabase", WolfAPI.plugin);
		String output = dab.getConfig().getString("Items." + input);
		return output;
	}
	
}
