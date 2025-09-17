package net.blue_gamerwolf.waypoint.registry;

import net.blue_gamerwolf.waypoint.Waypoint;
import net.blue_gamerwolf.waypoint.items.Heart;
import net.blue_gamerwolf.waypoint.items.RainbowDyeItem;
import net.blue_gamerwolf.waypoint.items.Knife;
import net.blue_gamerwolf.waypoint.items.HealthSensorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

public class WaypointItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Waypoint.MOD_ID);

    public static final RegistryObject<Item> RAINBOW_DYE = ITEMS.register("rainbow_dye",
            () -> new RainbowDyeItem(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> HEART = ITEMS.register("heart",
            () -> new Heart(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> KNIFE = ITEMS.register("knife",
            () -> new Knife(new Item.Properties().stacksTo(1)));


    public static void register(IEventBus bus) {
        ITEMS.register(bus);

        // Optional Curios version
        if (ModList.get().isLoaded("curios")) {
            ITEMS.register("health_sensor_item",
                    () -> new HealthSensorItem(new Item.Properties().stacksTo(1)));
        }
    }
}
