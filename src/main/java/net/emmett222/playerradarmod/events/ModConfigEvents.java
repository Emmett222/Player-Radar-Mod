package net.emmett222.playerradarmod.events;

import net.emmett222.playerradarmod.PlayerRadarMod;
import net.emmett222.playerradarmod.configs.ModConfigs;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

/**
 * Handles putting the config information into a cache for quick use.
 */
@Mod.EventBusSubscriber(modid = PlayerRadarMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConfigEvents {
    @SubscribeEvent
    public static void onConfigLoading(ModConfigEvent.Loading event) {
        ModConfigs.cacheConfigs();
    }
}
