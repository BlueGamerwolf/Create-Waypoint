package net.blue_gamerwolf.waypoint.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.blue_gamerwolf.waypoint.blocks.HealthSensorTile;
import net.blue_gamerwolf.waypoint.registry.WaypointBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class HealthSensorBlockEntityRenderer implements BlockEntityRenderer<HealthSensorTile> {

    private final BlockRenderDispatcher blockRenderer;

    public HealthSensorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = Minecraft.getInstance().getBlockRenderer();
    }

    @Override
    public void render(@Nonnull HealthSensorTile tile, float partialTicks, @Nonnull PoseStack matrixStack,
                       @Nonnull MultiBufferSource buffer, int combinedLight, int combinedOverlay) {

        BlockState state = tile.getBlockState();

        matrixStack.pushPose();
        matrixStack.translate(0.5, 0.0, 0.5); // center the block

        // Render the main block (base + shaft + other elements)
        blockRenderer.renderSingleBlock(state, matrixStack, buffer, combinedLight, OverlayTexture.NO_OVERLAY);

        // Render the heart block only if powered
        if (tile.isPowered()) {
            BlockState heartState = WaypointBlocks.HEART_BLOCK.get().defaultBlockState();
            blockRenderer.renderSingleBlock(heartState, matrixStack, buffer, combinedLight, OverlayTexture.NO_OVERLAY);
        }

        matrixStack.popPose();
    }
}
