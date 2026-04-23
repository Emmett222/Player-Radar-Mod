package net.emmett222.playerradarmod.items;

import net.emmett222.playerradarmod.PlayerRadarMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PlayerRadarMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> PLAYER_TRACKER_MOD = CREATIVE_MODE_TABS.register("player_radar_tab",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.TABICON.get()))
        .title(Component.translatable("creativetab.playerradarmod"))
        .displayItems((pParameters, pOutput) -> {
            pOutput.accept(ModItems.PLAYERTRACKER.get());
        })
        .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
