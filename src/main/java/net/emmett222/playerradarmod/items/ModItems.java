package net.emmett222.playerradarmod.items;

import net.emmett222.playerradarmod.PlayerRadarMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            PlayerRadarMod.MOD_ID);

    public static final RegistryObject<Item> PLAYERTRACKER = ITEMS.register("playertracker",
            () -> new PlayerRadar(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
