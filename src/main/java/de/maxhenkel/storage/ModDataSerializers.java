package de.maxhenkel.storage;

import net.minecraft.block.Block;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.registry.Registry;

public class ModDataSerializers {
    public static final IDataSerializer<Block> BLOCK = new IDataSerializer<Block>() {
        public void write(PacketBuffer buf, Block value) {
            buf.writeResourceLocation(value.getRegistryName());
        }

        public Block read(PacketBuffer buf) {
            return Registry.BLOCK.getOrDefault(buf.readResourceLocation());
        }

        public Block copyValue(Block value) {
            return value;
        }
    };
}
