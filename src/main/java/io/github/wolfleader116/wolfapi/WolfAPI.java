package io.github.wolfleader116.wolfapi;

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

public class WolfAPI {

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
	
}
