package net.blue_gamerwolf.waypoint.blocks;

import net.blue_gamerwolf.waypoint.registry.WaypointBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HealthSensorTile extends BlockEntity {

    private final FluidTank tank;
    private final LazyOptional<IFluidHandler> fluidHandler;

    public HealthSensorTile(BlockPos pos, BlockState state) {
        super(WaypointBlocks.HEALTH_SENSOR_TILE.get(), pos, state);

        // 10,000mb lava tank
        this.tank = new FluidTank(10_000) {
            @Override
            public boolean isFluidValid(FluidStack stack) {
                // Only accept lava
                return stack.getFluid().getFluidType().isSame(FluidType.LAVA);
            }

            @Override
            protected void onContentsChanged() {
                setChanged();
            }
        };

        this.fluidHandler = LazyOptional.of(() -> tank);
    }

    /** Expose capability only on bottom side */
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && side == Direction.DOWN) {
            return fluidHandler.cast();
        }
        return super.getCapability(capability, side);
    }

    /** Clear tank and invalidate capability when block is removed */
    @Override
    public void setRemoved() {
        super.setRemoved();
        fluidHandler.invalidate();
        tank.drain(tank.getCapacity(), IFluidHandler.FluidAction.EXECUTE);
    }

    /** Utility method to check if powered (tank has lava) */
    public boolean isPowered() {
        return !tank.isEmpty();
    }
}
