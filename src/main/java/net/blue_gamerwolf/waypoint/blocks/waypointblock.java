package net.blue_gamerwolf.waypoint.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

public class WaypointBlock extends Block {

    public WaypointBlock() {
        super(BlockBehaviour.Properties.of().strength(3.0f)); // Example properties
    }

    // Tell Minecraft this block has a BlockEntity
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new net.blue_gamerwolf.waypoint.blocks.blockentities.WaypointBlockEntity(pos, state);
    }
}
