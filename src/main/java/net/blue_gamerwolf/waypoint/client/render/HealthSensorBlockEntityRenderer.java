package net.blue_gamerwolf.waypoint.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.blue_gamerwolf.waypoint.blocks.HealthSensorTile;
import net.blue_gamerwolf.waypoint.registry.WaypointBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

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
    VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.solid());

    matrixStack.pushPose();
    matrixStack.translate(0.5, 0.0, 0.5);

    // Render the base block
    blockRenderer.renderBlock(state, matrixStack, buffer, combinedLight, combinedOverlay);

    // Only render heart if powered AND player has Create goggles
    boolean showHeart = tile.isPowered();
    if (Minecraft.getInstance().player != null) {
        var player = Minecraft.getInstance().player;
        showHeart &= player.getInventory().items.stream()
                .anyMatch(stack -> stack.is(ForgeRegistries.ITEMS.getValue(new ResourceLocation("create:goggles"))));
    }

    if (showHeart) {
        // Render the heart block on top
        BlockState heartState = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("waypoint:heart")).defaultBlockState();
        blockRenderer.renderBlock(heartState, matrixStack, buffer, combinedLight, combinedOverlay);
    }

    matrixStack.popPose();
}

}
