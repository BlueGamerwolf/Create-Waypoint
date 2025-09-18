package net.blue_gamerwolf.waypoint.registry;

import net.blue_gamerwolf.waypoint.Waypoint;
import net.blue_gamerwolf.waypoint.blocks.blockentities.WaypointBlockEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Waypoint.MOD_ID);

    public static final RegistryObject<BlockEntityType<WaypointBlockEntity>> WAYPOINT_BLOCK_ENTITY =
            TILE_ENTITIES.register("waypoint_block_entity",
                    () -> BlockEntityType.Builder.of(
                            WaypointBlockEntity::new,
                            WaypointBlocks.WAYPOINTBLOCK.get() // ⚠️ make sure this matches your block registry class
                    ).build(null));

    public static void register(IEventBus modEventBus) {
        TILE_ENTITIES.register(modEventBus); // ✅ FIXED
    }
}
