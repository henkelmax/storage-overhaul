package de.maxhenkel.storage;

import net.minecraft.block.Block;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraftforge.registries.ForgeRegistries;

public class ModDataSerializers {

    public static final IDataSerializer<Block> BLOCK = new IDataSerializer<Block>() {

        @Override
        public void write(PacketBuffer buf, Block value) {
            buf.writeResourceLocation(value.getRegistryName());
        }

        @Override
        public Block read(PacketBuffer buf) {
            return ForgeRegistries.BLOCKS.getValue(buf.readResourceLocation());
        }

        @Override
        public Block copy(Block value) {
            return value;
        }

    };

}