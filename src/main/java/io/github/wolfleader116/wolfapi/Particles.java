package io.github.wolfleader116.wolfapi;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public enum Particles {

	EXPLOSION_NORMAL("explode"),
	EXPLOSION_LARGE("largeexplode"),
	EXPLOSION_HUGE("hugeexplosion"),
	FIREWORKS_SPARK("fireworksSpark"),
	WATER_BUBBLE("bubble"),
	WATER_SPLASH("splash"),
	WATER_WAKE("wake"),
	SUSPEND("suspended"),
	SUSPENDED_DEPTH("depthSuspend"),
	CRIT("crit"),
	CRIT_MAGIC("magicCrit"),
	SMOKE_NORMAL("smoke"),
	SMOKE_LARGE("largesmoke"),
	SPELL("spell"),
	SPELL_INSTANT("instantSpell"),
	SPELL_MOB("mobSpell"),
	SPELL_MOB_AMBIENT("mobSpellAmbient"),
	SPELL_WITCH("witchMagic"),
	DRIP_WATER("dripWater"),
	DRIP_LAVA("dripLava"),
	VILLAGER_ANGRY("angryVillager"),
	VILLAGER_HAPPY("happyVillager"),
	TOWN_AURA("townaura"),
	NOTE("note"),
	PORTAL("portal"),
	ENCHANTMENT_TABLE("enchantmenttable"),
	FLAME("flame"),
	LAVA("lava"),
	FOOTSTEP("footstep"),
	CLOUD("cloud"),
	REDSTONE("reddust"),
	SNOWBALL("snowballpoof"),
	SNOW_SHOVEL("snowshovel"),
	SLIME("slime"),
	HEART("heart"),
	BARRIER("barrier"),
	ITEM_CRACK("iconcrack"),
	BLOCK_CRACK("blockcrack"),
	BLOCK_DUST("blockdust"),
	WATER_DROP("droplet"),
	ITEM_TAKE("take"),
	MOB_APPEARANCE("mobappearance");
	
	String name;
	
	Particles(String name) {
		this.name=name;
	}
	
    public String getName(){
        return name;
    }
	
	public void sendParticles(Player player, Particles particle, float x, float y, float z, float xOffset, float yOffset, float zOffset, float data, int amount) {
		try {
			Class<?> packetClass = this.getNMSClass("PacketPlayOutWorldParticles");
			Constructor<?> packetConstructor = packetClass.getConstructor(String.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class);
			Object packet = packetConstructor.newInstance(particle.getName(), x, y, z, xOffset, yOffset, zOffset, data, amount);
			Method sendPacket = getNMSClass("PlayerConnection").getMethod("sendPacket", this.getNMSClass("Packet"));
			sendPacket.invoke(this.getConnection(player), packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private Class<?> getNMSClass(String nmsClassString) throws ClassNotFoundException {
	    String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
	    String name = "net.minecraft.server." + version + nmsClassString;
	    Class<?> nmsClass = Class.forName(name);
	    return nmsClass;
	}
	private Object getConnection(Player player) throws SecurityException, NoSuchMethodException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
	    Method getHandle = player.getClass().getMethod("getHandle");
	    Object nmsPlayer = getHandle.invoke(player);
	    Field conField = nmsPlayer.getClass().getField("playerConnection");
	    Object con = conField.get(nmsPlayer);
	    return con;
	}
	
	public static Particles plugin;
	
}