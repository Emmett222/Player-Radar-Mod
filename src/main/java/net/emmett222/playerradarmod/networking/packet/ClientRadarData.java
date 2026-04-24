package net.emmett222.playerradarmod.networking.packet;

public class ClientRadarData {
    private static double targetX;
    private static double targetZ;

    public static void setTargetPos(double x, double z) {
        ClientRadarData.targetX = x;
        ClientRadarData.targetZ = z;
    }

    public static double getTargetX() {
        return targetX;
    }

    public static double getTargetZ() {
        return targetZ;
    }
}
