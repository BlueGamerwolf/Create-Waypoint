package net.blue_gamerwolf.waypoint;

import com.mojang.logging.LogUtils;
import net.blue_gamerwolf.waypoint.registry.WaypointBlocks;
import net.blue_gamerwolf.waypoint.registry.WaypointItems;
import net.blue_gamerwolf.waypoint.registry.ModTileEntities;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Waypoint.MOD_ID)
public class Waypoint {
    public static final String MOD_ID = "waypoint";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Waypoint() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);

        WaypointBlocks.BLOCKS.register(modEventBus);
        WaypointItems.ITEMS.register(modEventBus);
        ModTileEntities.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            // event.accept(WaypointBlocks.HEALTH_SENSOR_BLOCK.get());
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = Waypoint.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onRegisterItemColors(net.minecraftforge.client.event.RegisterColorHandlersEvent.Item event) {
            event.register(
                (stack, tintIndex) -> {
                    if (stack.hasTag() && stack.getTag().getBoolean("RainbowDye")) {
                        return net.blue_gamerwolf.waypoint.items.RainbowDyeItem.getRainbowColor(System.currentTimeMillis() / 20);
                    }
                    return 0xFFFFFF;
                },
                WaypointItems.RAINBOW_DYE.get()
            );

            WaypointItems.ITEMS.getEntries().forEach(regObj -> {
                event.register(
                    (stack, tintIndex) -> {
                        if (stack.hasTag() && stack.getTag().getBoolean("RainbowDye")) {
                            return net.blue_gamerwolf.waypoint.items.RainbowDyeItem.getRainbowColor(System.currentTimeMillis() / 20);
                        }
                        return 0xFFFFFF;
                    },
                    regObj.get()
                );
            });
        }
    }

    public static Object id(String string) {
        throw new UnsupportedOperationException("Unimplemented method 'id'");
    }
}
