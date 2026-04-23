package net.emmett222.playerradarmod.events;

import net.emmett222.playerradarmod.items.PlayerRadar;
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
            
            // 2. Check if the player is holding the radar
            if (user.getMainHandItem().getItem() instanceof PlayerRadar) {
                // Here is where you'll find your target and send the packet
                // Example:
                // ServerPlayer serverUser = (ServerPlayer) user;
                // ModMessages.sendToPlayer(new SyncPlayerPosPacket(x, z), serverUser);
            }
        }
    }
}
