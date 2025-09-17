package net.blue_gamerwolf.waypoint.blocks;

import net.blue_gamerwolf.waypoint.items.HealthSensorItem;
import net.blue_gamerwolf.waypoint.registry.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HealthSensorTile extends BlockEntity {

    private static final float REQUIRED_SU = 64f; // for any stress logic if needed
    private static final int COOLDOWN_TICKS = 100;
    private final Map<UUID, Integer> cooldownMap = new HashMap<>();

    public HealthSensorTile(BlockPos pos, BlockState state) {
        super(ModTileEntities.HEALTH_SENSOR_TILE.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, HealthSensorTile tile) {
        if (level.isClientSide) return; // only server-side
        if (!(level instanceof ServerLevel serverLevel)) return;

        if (serverLevel.getGameTime() % 20 != 0) return; // every second

        List<Player> players = level.getEntitiesOfClass(Player.class,
                tile.getRenderBoundingBox().inflate(1, 2, 1));

        for (Player player : players) {
            boolean hasLinkedCurio = player.getInventory().items.stream()
                    .filter(stack -> stack.getItem() instanceof HealthSensorItem)
                    .anyMatch(stack -> {
                        UUID linkedId = HealthSensorItem.getLinkedPlayer(stack);
                        return linkedId != null && linkedId.equals(player.getUUID());
                    });

            if (!hasLinkedCurio) continue;

            Integer cooldown = tile.cooldownMap.getOrDefault(player.getUUID(), 0);
            if (cooldown > 0) {
                tile.cooldownMap.put(player.getUUID(), cooldown - 20);
                continue;
            }

            if (player.getHealth() <= player.getMaxHealth() / 4f) {
                double x = pos.getX() + 0.5;
                double y = pos.getY() + 1.1;
                double z = pos.getZ() + 0.5;
                player.teleportTo(x, y, z);
                tile.cooldownMap.put(player.getUUID(), COOLDOWN_TICKS);
            }
        }
    }

    public float calculateStressApplied() {
        return REQUIRED_SU;
    }

    public boolean hasStressImpact() {
        return true;
    }
}
