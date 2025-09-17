package net.blue_gamerwolf.waypoint.client;

import net.blue_gamerwolf.waypoint.client.render.HealthSensorBlockEntityRenderer;
import net.blue_gamerwolf.waypoint.registry.ModTileEntities;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModTileEntities.HEALTH_SENSOR_TILE.get(),
                context -> new HealthSensorBlockEntityRenderer(context));
    }
}
