package net.emmett222.playerradarmod.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.PacketTarget;
import net.minecraftforge.network.simple.SimpleChannel;
import net.emmett222.playerradarmod.networking.packet.SyncPlayerPosPacket;

public class ModMessages {
    private static SimpleChannel sc;

    // Use a unique ID for the packet (Primary Key logic)
    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation("playerradarmod", "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        sc = net;

        // Remove the NetworkDirection from the initial call
        net.messageBuilder(SyncPlayerPosPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncPlayerPosPacket::new) // The Constructor-based decoder
                .encoder(SyncPlayerPosPacket::toBytes) // The Encoding method
                .consumerMainThread(SyncPlayerPosPacket::handle) // The Logic handler
                .add();
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        sc.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
