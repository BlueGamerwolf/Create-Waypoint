package net.blue_gamerwolf.waypoint.blocks;

import net.blue_gamerwolf.waypoint.registry.WaypointBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

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
            public boolean isFluidValid(@Nonnull FluidStack stack) {
                return stack.getFluid().isSame(Fluids.LAVA);
            }

            @Override
            protected void onContentsChanged() {
                setChanged();
            }
        };

        this.fluidHandler = LazyOptional.of(() -> tank);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER && side == Direction.DOWN) {
            return fluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        tank.drain(tank.getCapacity(), IFluidHandler.FluidAction.EXECUTE);
        fluidHandler.invalidate();
    }

    public boolean isPowered() {
        return !tank.isEmpty();
    }
}
