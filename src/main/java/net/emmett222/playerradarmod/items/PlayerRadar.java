package net.emmett222.playerradarmod.items;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Player Tracker item.
 * 
 * @author Emmett Grebe
 * @version 4-23-2026
 */
public class PlayerRadar extends Item {

    Player trackedPlayer;
    int nextTrackedPlayerIndex;

    /**
     * Explicit constructor. Just calls super.
     * 
     * @param pProperties The pProperties to be used.
     */
    public PlayerRadar(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        if (pPlayer.isCrouching()) {
            changePlayer(pPlayer, pLevel);
        } else {
            trackPlayer(this.trackedPlayer, pPlayer, pLevel);
        }

        return super.use(pLevel, pPlayer, pUsedHand);

    }

    /**
     * Changes the tracker to the next player in alphabetical order.
     */
    private void changePlayer(Player user, Level world) {
        // If there is nobody else to track.
        if (world.players().size() == 1) {
            Component failComponent = Component.translatable("There is nobody to track.");
            user.displayClientMessage(failComponent, true);
            return;
        }

        this.trackedPlayer = world.players().get(nextTrackedPlayerIndex);
        nextTrackedPlayerIndex += 1;
        if (nextTrackedPlayerIndex > world.players().size() + 1) {
            nextTrackedPlayerIndex = 0;
        }
        Component changeComponent = Component.translatable("Now tracking: " + this.trackedPlayer.getName());
        user.displayClientMessage(changeComponent, true);
    }

    /**
     * Tracks the selected player.
     */
    private void trackPlayer(Player player, Player user, Level world) {
        // If the user did not select a player first.
        if (player == null) {
            Component failComponent = Component.translatable("Shift + Right click to select a player first.");
            user.displayClientMessage(failComponent, true);
            return;
        }
        // If the player is no longer in the game.
        if (!world.players().contains(player)) {
            Component failComponent = Component.translatable("Tracked player is no longer in the game.");
            player.displayClientMessage(failComponent, true);
            return;
        }

    }
}
