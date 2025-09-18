package net.blue_gamerwolf.waypoint.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.blue_gamerwolf.waypoint.registry.ModBlockEntities;

public class WaypointBlockEntity extends BlockEntity {

    private String waypointName = "New Waypoint";
    private int color = 0xFFFFFF; // Default white (RGB hex)
    private String owner = "";

    public WaypointBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WAYPOINT_BLOCK_ENTITY.get(), pos, state);
    }

    // Getters & Setters
    public String getWaypointName() {
        return waypointName;
    }

    public void setWaypointName(String name) {
        this.waypointName = name;
        setChanged();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int rgb) {
        this.color = rgb;
        setChanged();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String playerName) {
        this.owner = playerName;
        setChanged();
    }

    // Save data
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("WaypointName", waypointName);
        tag.putInt("Color", color);
        tag.putString("Owner", owner);
    }

    // Load data
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.waypointName = tag.getString("WaypointName");
        this.color = tag.getInt("Color");
        this.owner = tag.getString("Owner");
    }
}
