package net.blue_gamerwolf.waypoint.client;

import net.blue_gamerwolf.waypoint.blocks.HealthSensorTile;
import net.blue_gamerwolf.waypoint.client.render.HealthSensorBlockEntityRenderer;
import net.blue_gamerwolf.waypoint.registry.ModTileEntities;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "waypoint", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Use the BlockEntityType from ModTileEntities
        event.registerBlockEntityRenderer(ModTileEntities.HEALTH_SENSOR_TILE.get(), HealthSensorBlockEntityRenderer::new);
    }
}
