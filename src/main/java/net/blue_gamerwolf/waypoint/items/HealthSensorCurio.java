package net.blue_gamerwolf.waypoint.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;

public class HealthSensorCurio extends Item implements ICurio {

    private static final String TAG_LINKED_PLAYER = "LinkedPlayer";

    public HealthSensorCurio(Properties properties) {
        super(properties);
    }

    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity.level().isClientSide()) return;

        if (isPlayerLowHP(entity, stack)) {
            entity.teleportTo(entity.getX(), entity.getY() + 2, entity.getZ());
        }
    }

    public static void linkToPlayer(ItemStack stack, UUID playerUUID) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putUUID(TAG_LINKED_PLAYER, playerUUID);
    }

    public static UUID getLinkedPlayer(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.hasUUID(TAG_LINKED_PLAYER)) {
            return tag.getUUID(TAG_LINKED_PLAYER);
        }
        return null;
    }

    public static boolean isPlayerLowHP(LivingEntity entity, ItemStack stack) {
        UUID linked = getLinkedPlayer(stack);
        if (linked == null) return false;
        if (!entity.getUUID().equals(linked)) return false;
        return entity.getHealth() <= (entity.getMaxHealth() / 4f);
    }

    @Override
    public ItemStack getStack() {
        throw new UnsupportedOperationException("Unimplemented method 'getStack'");
    }
}
