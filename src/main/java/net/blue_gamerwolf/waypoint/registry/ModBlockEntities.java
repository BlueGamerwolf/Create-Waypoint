package net.blue_gamerwolf.waypoint.registry;

import net.blue_gamerwolf.waypoint.Waypoint;
import net.blue_gamerwolf.waypoint.blocks.blockentities.WaypointBlockEntity;
import net.blue_gamerwolf.waypoint.blocks.WaypointBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Waypoint.MOD_ID);

    public static final RegistryObject<BlockEntityType<WaypointBlockEntity>> WAYPOINT_BLOCK_ENTITY =
            TILE_ENTITIES.register("waypoint_block_entity",
                    () -> BlockEntityType.Builder.of(
                            WaypointBlockEntity::new,
                            WaypointBlocks.WAYPOINTBLOCK.get()
                    ).build(null));

    public static void register(IEventBus modEventBus) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }
}
