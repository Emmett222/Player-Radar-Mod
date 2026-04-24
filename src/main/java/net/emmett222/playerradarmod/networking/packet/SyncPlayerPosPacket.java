package net.emmett222.playerradarmod.networking.packet;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class SyncPlayerPosPacket {
    private final double x;
    private final double z;

    // Constructor for creating the packet on the Server
    public SyncPlayerPosPacket(double x, double z) {
        this.x = x;
        this.z = z;
    }

    // Decoder: Reads the bytes from the buffer (MUST be in the same order as encoder)
    public SyncPlayerPosPacket(FriendlyByteBuf buffer) {
        this.x = buffer.readDouble();
        this.z = buffer.readDouble();
    }

    // Encoder: Writes the data to the buffer
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeDouble(x);
        buffer.writeDouble(z);
    }

    // Handler: This is what the CLIENT does when it receives the data
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Update your radar's client-side variables
            ClientRadarData.setTargetPos(x, z);
        });
        return true;
    }
}
