package net.blue_gamerwolf.waypoint.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.block.render.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.block.render.SuperByteBufferCache;
import com.simibubi.create.foundation.utility.VecHelper;
import net.blue_gamerwolf.waypoint.blocks.HealthSensorTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class HealthSensorBlockEntityRenderer extends SafeBlockEntityRenderer<HealthSensorTile> {

    private final BlockRenderDispatcher blockRenderer;

    public HealthSensorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = Minecraft.getInstance().getBlockRenderer();
    }

    
    protected void renderSafe(@Nonnull HealthSensorTile tile, float partialTicks, @Nonnull PoseStack matrixStack,
                              @Nonnull MultiBufferSource buffer, int combinedLight, int combinedOverlay) {

        BlockState state = tile.getBlockState();

        matrixStack.pushPose();
        matrixStack.translate(0.5, 0, 0.5); // center the block

        // Render the main block
        blockRenderer.renderSingleBlock(state, matrixStack, buffer, combinedLight, OverlayTexture.NO_OVERLAY);

        // Render a kinetic shaft at the bottom
        renderKineticShaft(matrixStack, buffer, combinedLight, combinedOverlay);

        matrixStack.popPose();
    }

    private void renderKineticShaft(PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        // A simple way is to render the Create shaft block model
        BlockState shaft = net.minecraft.world.level.block.Blocks.OAK_LOG.defaultBlockState(); // replace with Create shaft if available
        blockRenderer.renderSingleBlock(shaft, ms, buffer, light, overlay);
    }
}
