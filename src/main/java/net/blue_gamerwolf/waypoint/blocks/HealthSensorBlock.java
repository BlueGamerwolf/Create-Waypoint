package net.blue_gamerwolf.waypoint.blocks;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.base.IRotate;
import net.blue_gamerwolf.waypoint.registry.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class HealthSensorBlock extends KineticBlock {

    public HealthSensorBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(net.minecraft.world.level.material.MapColor.METAL)
                .strength(3f)
                .noOcclusion());
    }

    @Override
    public Axis getRotationAxis(BlockState state) {
        return Axis.Y;
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HealthSensorTile(pos, state);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModTileEntities.HEALTH_SENSOR_TILE.get()
                ? (lvl, pos, st, te) -> {
                    if (te instanceof HealthSensorTile tile) {
                        HealthSensorTile.tick(lvl, pos, st, tile);
                    }
                }
                : null;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.NORTH; // ✅ SU input on north face only
    }
}
