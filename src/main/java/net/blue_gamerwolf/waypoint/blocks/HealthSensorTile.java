package net.blue_gamerwolf.waypoint.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAction;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HealthSensorTile extends BlockEntity {

    private static final int MAX_LAVA = 10000; // 10,000 mB

    private final FluidTank tank = new FluidTank(MAX_LAVA) {
        @Override
        protected void onContentsChanged() {
            setChanged(); // mark tile dirty so it saves
        }

        public boolean canFillFluidType(FluidStack stack) {
            // Only lava
            return stack.getFluid().getRegistryName().toString().equals("minecraft:lava");
        }
    };

    private final LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> new IFluidHandler() {

        @Override
        public int getTanks() {
            return 1;
        }

        @Nonnull
        @Override
        public FluidStack getFluidInTank(int tankIndex) {
            return tank.getFluid();
        }

        @Override
        public int getTankCapacity(int tankIndex) {
            return tank.getCapacity();
        }

        @Override
        public boolean isFluidValid(int tankIndex, @Nonnull FluidStack stack) {
            return tank.canFillFluidType(stack);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (resource.isEmpty()) return 0;

            // Only allow filling from bottom
            if (getBlockPos().above().equals(BlockPos.ZERO)) return 0; // dummy, see note below

            return tank.fill(resource, action);
        }

        @Nonnull
        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            return tank.drain(resource, action);
        }

        @Nonnull
        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            return tank.drain(maxDrain, action);
        }
    });

    public HealthSensorTile(BlockPos pos, BlockState state) {
        super(WaypointBlocks.HEALTH_SENSOR_TILE.get(), pos, state);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable net.minecraft.core.Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            // Only allow access from bottom
            if (side == net.minecraft.core.Direction.DOWN) return fluidHandler.cast();
            return LazyOptional.empty();
        }
        return super.getCapability(cap, side);
    }

    public void onRemove() {
        super.onRemove();
        tank.drain(tank.getFluidAmount(), FluidAction.EXECUTE);
        fluidHandler.invalidate();
    }

    public boolean hasEnoughFuel() {
        return tank.getFluidAmount() > 0;
    }

    public int getFuelAmount() {
        return tank.getFluidAmount();
    }
}
