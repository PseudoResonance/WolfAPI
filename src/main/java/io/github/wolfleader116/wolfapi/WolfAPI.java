package io.github.wolfleader116.wolfapi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import net.minecraft.server.v1_8_R2.EnumParticle;
import net.minecraft.server.v1_8_R2.PacketPlayOutWorldParticles;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
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
	
	public void message(String message, Player player, String prefix) {
		player.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.GREEN + message);
	}
	
	public void error(String error, Player player, String prefix) {
		switch (error) {
		case "permission":
			particle("fireworks_spark", true, (float) player.getLocation().getX(), (float) player.getLocation().getY() + 1, (float) player.getLocation().getZ(), 1, 1, 1, 0, 5, player);
			player.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + "You do not have permission to do this!");
			break;
		default:
			player.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + "There has been an issue! Please contact the server staff.");
			break;
		}
	}

	public static void particle(String particlename, boolean boo, float xcent, float ycent, float zcent, int xoff, int yoff, int zoff, int speed, int amount, Player player) {
		switch (particlename) {
		case "barrier":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.BARRIER, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "block_crack":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.BLOCK_CRACK, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "block_dust":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.BLOCK_DUST, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "cloud":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.CLOUD, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "crit":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.CRIT, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "crit_magic":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.CRIT_MAGIC, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "drip_lava":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.DRIP_LAVA, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "drip_water":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.DRIP_WATER, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "enchantment_table":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.ENCHANTMENT_TABLE, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "explosion_huge":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_HUGE, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "explosion_large":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_LARGE, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "explosion_normal":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_NORMAL, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "fireworks_spark":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.FIREWORKS_SPARK, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "flame":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.FLAME, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "footstep":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.FOOTSTEP, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "heart":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.HEART, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "item_crack":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.ITEM_CRACK, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "item_take":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.ITEM_TAKE, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "lava":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.LAVA, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "mob_appearance":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.MOB_APPEARANCE, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "note":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.NOTE, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "portal":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.PORTAL, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "redstone":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "slime":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.SLIME, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "smoke_large":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.SMOKE_LARGE, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "smoke_normal":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.SMOKE_NORMAL, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "snow_shovel":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.SNOW_SHOVEL, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "snowball":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.SNOWBALL, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "spell":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.SPELL, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "spell_instant":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.SPELL_INSTANT, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "spell_mob":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.SPELL_MOB, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "spell_mob_ambient":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.SPELL_MOB_AMBIENT, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "spell_witch":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.SPELL_WITCH, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "suspended":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.SUSPENDED, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "suspended_depth":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.SUSPENDED_DEPTH, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "town_aura":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.TOWN_AURA, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "villager_angry":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.VILLAGER_ANGRY, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "villager_happy":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.VILLAGER_HAPPY, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "water_bubble":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.WATER_BUBBLE, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "water_drop":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.WATER_DROP, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "water_splash":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.WATER_SPLASH, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		case "water_wake":
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.WATER_WAKE, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount));
			break;
		default:
			break;
		}
	}
	
	public static String getItemName(String input) {
		Config dab = new Config("itemdatabase", WolfAPI.plugin);
		String output = dab.getConfig().getString("Items." + input);
		return output;
	}
	
}
