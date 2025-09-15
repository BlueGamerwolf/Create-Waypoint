package net.blue_gamerwolf.waypoint.blocks;

import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.blue_gamerwolf.waypoint.items.HealthSensorCurio;
import net.blue_gamerwolf.waypoint.registry.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HealthSensorTile extends KineticBlockEntity {

    private static final float REQUIRED_SU = 64f;
    private static final int COOLDOWN_TICKS = 100;
    private final Map<UUID, Integer> cooldownMap = new HashMap<>();

    public HealthSensorTile(BlockPos pos, BlockState state) {
        super(ModTileEntities.HEALTH_SENSOR_TILE.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, HealthSensorTile tile) {
        if (level.isClientSide) return;
        if (!(level instanceof ServerLevel serverLevel)) return;
        if (serverLevel.getGameTime() % 20 != 0) return; // every second
        if (tile.getSpeed() == 0) return; // no rotation, no power

        List<Player> players = level.getEntitiesOfClass(Player.class,
                tile.getRenderBoundingBox().inflate(1, 2, 1)); // slightly bigger radius

        for (Player player : players) {
            boolean hasLinkedCurio = player.getInventory().items.stream()
                    .filter(stack -> stack.getItem() instanceof HealthSensorCurio)
                    .anyMatch(stack -> {
                        UUID linkedId = HealthSensorCurio.getLinkedPlayer(stack);
                        return linkedId != null && linkedId.equals(player.getUUID());
                    });

            if (!hasLinkedCurio) continue;

            Integer cooldown = tile.cooldownMap.getOrDefault(player.getUUID(), 0);
            if (cooldown > 0) {
                tile.cooldownMap.put(player.getUUID(), cooldown - 20);
                continue;
            }

            if (player.getHealth() <= player.getMaxHealth() / 4f) {
                // Only teleport if enough SU is available
                if (tile.getSpeed() > 0) { // placeholder for SU check
                    double x = pos.getX() + 0.5;
                    double y = pos.getY() + 1.1;
                    double z = pos.getZ() + 0.5;
                    player.teleportTo(x, y, z);
                    tile.cooldownMap.put(player.getUUID(), COOLDOWN_TICKS);
                }
            }
        }
    }

    @Override
    public float calculateStressApplied() {
        return REQUIRED_SU;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
    }
}
