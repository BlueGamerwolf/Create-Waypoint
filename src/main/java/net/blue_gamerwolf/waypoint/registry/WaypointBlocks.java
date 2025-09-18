package net.blue_gamerwolf.waypoint.registry;

import net.blue_gamerwolf.waypoint.Waypoint;
import net.blue_gamerwolf.waypoint.blocks.WaypointBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WaypointBlocks {

    public static final String MOD_ID = Waypoint.MOD_ID != null ? Waypoint.MOD_ID : "waypoint";

    // Block & Item registries
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    // --- Register your waypoint block + block item ---
    public static final RegistryObject<WaypointBlock> WAYPOINTBLOCK =
            registerBlock("waypoint_block", WaypointBlock::new);

    // Helper method for Block + BlockItem registration
    private static <T extends Block> RegistryObject<T> registerBlock(String name, java.util.function.Supplier<T> blockSupplier) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);

        // Always register a BlockItem for it
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));

        return block;
    }

    // Call this from your main mod class
    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
    }
}
