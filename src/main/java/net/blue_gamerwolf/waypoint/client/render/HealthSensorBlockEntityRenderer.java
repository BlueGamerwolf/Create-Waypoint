package net.blue_gamerwolf.waypoint.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blue_gamerwolf.waypoint.blocks.HealthSensorTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import com.simibubi.create.content.contraptions.goggles.CreateItems;
import net.minecraft.world.level.block.Block;
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
        matrixStack.translate(0.5, 0.0, 0.5); // center block on origin

        // Render base block (everything except the heart)
        BakedModel baseModel = blockRenderer.getBlockModel(state);
        blockRenderer.getModelRenderer().renderModel(
                matrixStack.last(),
                vertexConsumer,
                state,
                baseModel,
                1f, 1f, 1f,
                combinedLight,
                OverlayTexture.NO_OVERLAY
        );

        // Only render heart if powered AND player has Create goggles
        if (tile.isPowered()) {
            Player player = Minecraft.getInstance().player;
            if (player != null && player.getItemBySlot(EquipmentSlot.HEAD).getItem() == CreateItems.ENGINEER_GOGGLES.get()) {
                Block heartBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("waypoint:heart"));
                BlockState heartState = heartBlock.defaultBlockState();
                BakedModel heartModel = blockRenderer.getBlockModel(heartState);

                blockRenderer.getModelRenderer().renderModel(
                        matrixStack.last(),
                        vertexConsumer,
                        heartState,
                        heartModel,
                        1f, 1f, 1f,
                        combinedLight,
                        OverlayTexture.NO_OVERLAY
                );
            }
        }

        matrixStack.popPose();
    }
}
