package net.emmett222.playerradarmod.configs;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Configs for Player Radar Mod.
 * 
 * @author Emmett Grebe
 * @version 4-22-2026
 */
public class ModConfigs {
        // Builder
        public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        public static final ForgeConfigSpec SPEC;

        // Item
        public static final ForgeConfigSpec.ConfigValue<Double> TOO_FAR_DISTANCE;
        public static final ForgeConfigSpec.ConfigValue<Boolean> HAS_COOLDOWN;
        public static final ForgeConfigSpec.ConfigValue<Integer> COOLDOWN;

        // Radar
        public static final ForgeConfigSpec.ConfigValue<Double> RADAR_WIDTH;
        public static final ForgeConfigSpec.ConfigValue<Double> RADAR_HEIGHT;

        // Cache
        // --- STATIC CACHE ---
        public static double tooFarDistance, radarWidth, radarHeight;
        public static boolean hasCooldown;
        public static int cooldown;

        /**
         * Static initializer. Runs exactly once.
         */
        static {
                BUILDER.push("Tracker item settings");

                TOO_FAR_DISTANCE = BUILDER
                                .comment("How far from the tracked player you have to be for you to track them.")
                                .defineInRange("tooFarDistance", 300.0, 0.0, 72000.0);

                HAS_COOLDOWN = BUILDER.comment("Give the tracker a cooldown").define("hasCooldown",
                                true);
                COOLDOWN = BUILDER
                                .comment("How long of a cooldown will the tracker have before it can be used again")
                                .defineInRange("cooldownTime", 60, 0, 2048);
               
                BUILDER.pop();



                BUILDER.push("Radar settings");

                RADAR_WIDTH = BUILDER
                                .comment("How wide the radar will be around the tracked player (X width)")
                                .defineInRange("radarWidth", 100.0, 1.0, 72000.0);

                RADAR_HEIGHT = BUILDER
                                .comment("How tall the radar will be around the tracked player (Z width")
                                .defineInRange("radarHeight", 100.0, 1.0, 72000.0);
               
                BUILDER.pop();

                SPEC = BUILDER.build();
        }

        /**
         * Takes the configs and puts them in static variables. Much faster than calling
         * this method every time a config is needed.
         * Here we also make the minimum be 0 for number configs to prevent crashing if
         * the user puts a negative for the level.
         */
        public static void cacheConfigs() {
                // Item
                tooFarDistance = Math.max(1.0, TOO_FAR_DISTANCE.get());
                hasCooldown = HAS_COOLDOWN.get();
                cooldown = Math.max(0, COOLDOWN.get());

                // Radar
                radarWidth = Math.max(1.0, RADAR_WIDTH.get());
                radarHeight = Math.max(1.0, RADAR_HEIGHT.get());

        }
}
