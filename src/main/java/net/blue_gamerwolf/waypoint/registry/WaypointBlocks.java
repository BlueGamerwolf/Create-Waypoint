package net.blue_gamerwolf.waypoint.registry;

import net.blue_gamerwolf.waypoint.Waypoint;
import net.blue_gamerwolf.waypoint.blocks.HealthSensorBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.common.CreativeModeTabRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class WaypointBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Waypoint.MOD_ID);

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Waypoint.MOD_ID);

    // === Heart Sensor Block ===
    public static final RegistryObject<Block> HEALTH_SENSOR = registerBlock("health_sensor",
            HealthSensorBlock::new);

    public static final String HEALTH_SENSOR_BLOCK = null;

    // === Helper Method for Block + BlockItem ===
    private static <T extends Block> RegistryObject<T> registerBlock(String name, java.util.function.Supplier<T> block) {
        RegistryObject<T> registeredBlock = BLOCKS.register(name, block);
        ITEMS.register(name, () -> new BlockItem(registeredBlock.get(), new Item.Properties()));
        return registeredBlock;
    }

    // === Register with Mod Event Bus ===
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}
