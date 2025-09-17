package net.blue_gamerwolf.waypoint.registry;

import net.blue_gamerwolf.waypoint.Waypoint;
import net.blue_gamerwolf.waypoint.blocks.HealthSensorBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WaypointBlocks {

    public static final String MOD_ID = Waypoint.MOD_ID != null ? Waypoint.MOD_ID : "waypoint";

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    // === Health Sensor Block ===
    public static final RegistryObject<Block> HEALTH_SENSOR_BLOCK =
        BLOCKS.register("health_sensor", HealthSensorBlock::new);


    // Helper method for Block + BlockItem registration
    private static <T extends Block> RegistryObject<T> registerBlock(String name, java.util.function.Supplier<T> blockSupplier) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    // Register with the mod event bus
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }

    // === Heart Block ===
    public static final RegistryObject<Block> HEART_BLOCK =
        BLOCKS.register("heart", () -> new Block(Block.Properties.of()
                .strength(0.5f)
                .noOcclusion()
        )
    );
}
