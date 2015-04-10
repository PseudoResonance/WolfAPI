package io.github.wolfleader116.wolfapi;

import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R2.EnumParticle;
import net.minecraft.server.v1_8_R2.PacketPlayOutWorldParticles;

public class Particle {

	public Particle(EnumParticle particle, boolean boo, float xcent, float ycent, float zcent, float xoff, float yoff, float zoff, int speed, int amount, Player player) {
		PacketPlayOutWorldParticles particlepacket = new PacketPlayOutWorldParticles(particle, boo, xcent, ycent, zcent, xoff, yoff, zoff, speed, amount);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(particlepacket);
	}
	
}
