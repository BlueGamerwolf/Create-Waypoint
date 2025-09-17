package net.blue_gamerwolf.waypoint.blocks;

import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import net.blue_gamerwolf.waypoint.registry.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HealthSensorBlock extends RotatedPillarKineticBlock implements IRotate {

    public HealthSensorBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(net.minecraft.world.level.material.MapColor.METAL)
                .strength(3f)
                .noOcclusion());
    }

    // Rotation axis is the same as a shaft’s axis
    @Override
    public Axis getRotationAxis(BlockState state) {
        return state.getValue(AXIS);
    }

    // Allow shafts to connect along the axis only
    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == getRotationAxis(state);
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HealthSensorTile(pos, state);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModTileEntities.HEALTH_SENSOR_TILE.get()
                ? (lvl, p, st, te) -> {
                    if (te instanceof HealthSensorTile tile) {
                        HealthSensorTile.tick(lvl, p, st, tile);
                    }
                }
                : null;
    }

    // ---- Shape ----
    private static final VoxelShape SHAPE;
    static {
        VoxelShape shape = Shapes.empty();

        // Base plate
        shape = Shapes.or(shape, Block.box(1, 0, 1, 15, 7, 15));

        // Arms
        shape = Shapes.or(shape, Block.box(0, 0, 0, 16, 14, 1));   // north
        shape = Shapes.or(shape, Block.box(0, 0, 15, 16, 14, 16)); // south
        shape = Shapes.or(shape, Block.box(0, 0, 0, 1, 14, 16));   // west
        shape = Shapes.or(shape, Block.box(15, 0, 0, 16, 14, 16)); // east

        // Heart spike
        shape = Shapes.or(shape, Block.box(7, 16, 7, 9, 18, 9));
        shape = Shapes.or(shape, Block.box(6, 18, 7, 8, 20, 9));
        shape = Shapes.or(shape, Block.box(8, 18, 7, 10, 20, 9));
        shape = Shapes.or(shape, Block.box(5, 20, 7, 7, 22, 9));
        shape = Shapes.or(shape, Block.box(9, 20, 7, 11, 22, 9));
        shape = Shapes.or(shape, Block.box(7, 20, 7, 9, 22, 9));
        shape = Shapes.or(shape, Block.box(6, 22, 7, 8, 24, 9));
        shape = Shapes.or(shape, Block.box(8, 22, 7, 10, 24, 9));
        shape = Shapes.or(shape, Block.box(4, 22, 7, 6, 24, 9));
        shape = Shapes.or(shape, Block.box(5, 24, 7, 7, 26, 9));
        shape = Shapes.or(shape, Block.box(9, 24, 7, 11, 26, 9));

        SHAPE = shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
