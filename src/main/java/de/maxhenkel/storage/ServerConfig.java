package de.maxhenkel.storage;

import de.maxhenkel.corelib.config.ConfigBase;
import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig extends ConfigBase {

    public ForgeConfigSpec.IntValue storageBarrelSize;

    public ServerConfig(ForgeConfigSpec.Builder builder) {
        super(builder);
        storageBarrelSize = builder.comment("The amount of items that can be stored in a storage barrel").defineInRange("storage_barrel_size", 64 * 54, 1, Integer.MAX_VALUE);
    }

}
