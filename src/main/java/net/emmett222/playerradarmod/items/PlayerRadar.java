package net.emmett222.playerradarmod.items;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.emmett222.playerradarmod.networking.ModMessages;
import net.emmett222.playerradarmod.networking.packet.SyncPlayerPosPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Player Tracker item.
 * * @author Emmett Grebe
 * 
 * @version 4-24-2026
 */
public class PlayerRadar extends Item {

    public Player trackedPlayer;
    private int nextTrackedPlayerIndex;
    private Random rand = new Random();

    public PlayerRadar(Properties pProperties) {
        super(pProperties.durability(4));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        // 1. THE SIDE GUARD: Logic only runs on the Server.
        // We use sidedSuccess to stop the "double-flicker" on the client side.
        if (pLevel.isClientSide()) {
            return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pUsedHand), pLevel.isClientSide());
        }

        if (pPlayer.isCrouching()) {
            changePlayer(pPlayer, pLevel);
        } else {
            trackPlayer(this.trackedPlayer, pPlayer, pLevel);
        }

        return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pUsedHand), pLevel.isClientSide());
    }

    private void changePlayer(Player user, Level world) {
        if (world.players().size() <= 1) {
            user.displayClientMessage(Component.translatable("There is nobody to track."), true);
            return;
        }

        // 2. DATA SAFETY: Always sort a copy, never the live server list.
        List<ServerPlayer> players = new ArrayList<>(world.getServer().getPlayerList().getPlayers());
        players.sort(Comparator.comparing(p -> p.getName().getString().toLowerCase()));

        nextTrackedPlayerIndex = (nextTrackedPlayerIndex + 1) % players.size();
        this.trackedPlayer = players.get(nextTrackedPlayerIndex);

        // 3. INSTANT FEEDBACK: Send a packet immediately so the UI snaps to the new
        // target.
        syncTargetCoordinates(user);

        Component changeComponent = Component.translatable("Now tracking: " + this.trackedPlayer.getName().getString());
        user.displayClientMessage(changeComponent, true);
    }

    private void trackPlayer(Player target, Player user, Level world) {
        if (target == null) {
            user.displayClientMessage(Component.translatable("Shift + Right click to select a player first."), true);
            return;
        }

        // 4. PERSISTENCE CHECK: Ensure the target hasn't logged out.
        if (world.getServer().getPlayerList().getPlayer(target.getUUID()) == null) {
            user.displayClientMessage(Component.translatable("Tracked player is no longer in the game."), true);
            this.trackedPlayer = null;
            return;
        }

        int newX;
        int newZ;
        
        newX = this.trackedPlayer.getX() + (rand.nextInt(101) - 50);
        newZ = this.trackedPlayer.getZ() + (rand.nextInt(101) - 50);

        Point topLeftCorner = new Point(newX-50, newZ + 50);
        Point bottomRightCorner = new Point(newX+50, newZ-50);


        String chatMessage = String.format(this.trackedPlayer.getName().getString() + "is somewhere withing zone: X(%d to %d) | Z(%d to %d)", topLeftCorner.getX(), bottomRightCorner.getX(), topLeftCorner.getY(), bottomRightCorner.getY());
        user.displayClientMessage(Component.translatable(chatMessage, true));
        pPlayer.getItemInHand(pUsedHand).hurtAndBreak(1, pPlayer, (p) -> p.broadcastBreakEvent(pUsedHand));
        // Manual refresh if the user just right-clicks without shifting.
        syncTargetCoordinates(user);
    }

    /**
     * Helper to bridge the Item logic to your Networking logic.
     */
    private void syncTargetCoordinates(Player user) {
        if (this.trackedPlayer != null && user instanceof ServerPlayer serverUser) {
            ModMessages.sendToPlayer(
                    new SyncPlayerPosPacket(this.trackedPlayer.getX(), this.trackedPlayer.getZ()),
                    serverUser);
        }
    }

    public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents,
            TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        Component tooltipComp = Component.translatable("tooltip.playerradarmod.playertracker")
                .withStyle(ChatFormatting.GRAY);
        pTooltipComponents.add(tooltipComp);
    }
}