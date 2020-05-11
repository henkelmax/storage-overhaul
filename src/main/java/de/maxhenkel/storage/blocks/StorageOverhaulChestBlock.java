package de.maxhenkel.storage.blocks;

import de.maxhenkel.storage.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class StorageOverhaulChestBlock extends ChestBlock implements IItemBlock {

    private Callable<ItemStackTileEntityRenderer> renderer;

    private static final TileEntityMerger.ICallback<ChestTileEntity, Optional<INamedContainerProvider>> CALLBACK = new TileEntityMerger.ICallback<ChestTileEntity, Optional<INamedContainerProvider>>() {
        @Override
        public Optional<INamedContainerProvider> func_225539_a_(final ChestTileEntity iinventory1, final ChestTileEntity iinventory2) {
            final IInventory iinventory = new DoubleSidedInventory(iinventory1, iinventory2);
            return Optional.of(new INamedContainerProvider() {
                @Nullable
                public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
                    if (iinventory1.canOpen(player) && iinventory2.canOpen(player)) {
                        iinventory1.fillWithLoot(playerInventory.player);
                        iinventory2.fillWithLoot(playerInventory.player);
                        return ChestContainer.createGeneric9X6(id, playerInventory, iinventory);
                    } else {
                        return null;
                    }
                }

                public ITextComponent getDisplayName() {
                    if (iinventory1.hasCustomName()) {
                        return iinventory1.getDisplayName();
                    } else {
                        return iinventory2.hasCustomName() ? iinventory2.getDisplayName() : new TranslationTextComponent("container.storage_overhaul.generic_double_chest", iinventory1.getDisplayName());
                    }
                }
            });
        }

        public Optional<INamedContainerProvider> func_225538_a_(ChestTileEntity p_225538_1_) {
            return Optional.of(p_225538_1_);
        }

        public Optional<INamedContainerProvider> func_225537_b_() {
            return Optional.empty();
        }
    };

    protected StorageOverhaulChestBlock(String name, Supplier<TileEntityType<? extends ChestTileEntity>> tileEntityTypeIn, Callable<ItemStackTileEntityRenderer> renderer) {
        super(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5F).sound(SoundType.WOOD), tileEntityTypeIn);
        this.renderer = renderer;
        setRegistryName(new ResourceLocation(Main.MODID, name));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ItemGroup.DECORATIONS).setISTER(() -> renderer)).setRegistryName(getRegistryName());
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return tileEntityType.get().create();
    }

    @Override
    public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
        return func_225536_a_(state, worldIn, pos, false).apply(CALLBACK).orElse(null);
    }
}
