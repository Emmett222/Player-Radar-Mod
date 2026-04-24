package net.emmett222.playerradarmod.events;

import net.emmett222.playerradarmod.items.PlayerRadar;
import net.emmett222.playerradarmod.networking.ModMessages;
import net.emmett222.playerradarmod.networking.packet.SyncPlayerPosPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "playerradarmod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // 1. The Logic Guard (Check the side)
        if (event.side.isServer() && event.phase == TickEvent.Phase.END) {
            Player user = event.player;
            if (user.tickCount % 5 == 0) {
                // 2. Check if the player is holding the radar
                if (user.getMainHandItem().getItem() instanceof PlayerRadar pr) {
                    // Here is where you'll find your target and send the packet
                    // Example:
                    if (pr.trackedPlayer == null) {
                        return;
                    }

                    ServerPlayer serverUser = (ServerPlayer) user;

                    int trackedX = pr.trackedPlayer.getBlockX();
                    int trackedZ = pr.trackedPlayer.getBlockZ();
                    ModMessages.sendToPlayer(new SyncPlayerPosPacket(trackedX, trackedZ), serverUser);
                }
            }
        }
    }
}
