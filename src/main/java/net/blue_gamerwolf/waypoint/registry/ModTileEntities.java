package net.blue_gamerwolf.waypoint.registry;

import net.blue_gamerwolf.waypoint.Waypoint;
import net.blue_gamerwolf.waypoint.blocks.HealthSensorTile;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTileEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Waypoint.MOD_ID);

    public static final RegistryObject<BlockEntityType<HealthSensorTile>> HEALTH_SENSOR_TILE =
            BLOCK_ENTITIES.register("health_sensor_tile",
                    () -> BlockEntityType.Builder.of(
                            HealthSensorTile::new,
                            WaypointBlocks.HEALTH_SENSOR.get() // reference your block here
                    ).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
