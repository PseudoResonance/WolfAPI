package io.github.wolfleader116.wolfapi;

import net.minecraft.server.v1_8_R2.EnumParticle;
import net.minecraft.server.v1_8_R2.PacketPlayOutWorldParticles;

import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class WolfAPI {

	public static void Particle(EnumParticle particle, boolean boo, float xcent, float ycent, float zcent, int xoff, int yoff, int zoff, int speed, int amount, Player player) {
		PacketPlayOutWorldParticles particlepacket = new PacketPlayOutWorldParticles(particle, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(particlepacket);
	}
	
}
