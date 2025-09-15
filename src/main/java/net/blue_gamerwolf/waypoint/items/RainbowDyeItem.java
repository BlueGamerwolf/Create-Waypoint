package net.blue_gamerwolf.waypoint.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;

public class RainbowDyeItem extends Item {

    public RainbowDyeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack dye = player.getItemInHand(hand);
        ItemStack target = player.getMainHandItem(); // The item to dye

        if (isDyeableTool(target)) {
            CompoundTag tag = target.getOrCreateTag();
            tag.putBoolean("RainbowDye", true);
            target.setTag(tag);

            if (!player.isCreative()) {
                dye.shrink(1);
            }

            return InteractionResultHolder.sidedSuccess(dye, level.isClientSide());
        }

        return InteractionResultHolder.pass(dye);
    }

    private boolean isDyeableTool(ItemStack stack) {
        Item item = stack.getItem();
        // Catch-all for tools & weapons
        return (item instanceof TieredItem) // pickaxe, sword, axe, shovel, hoe
                || item instanceof BowItem
                || item instanceof CrossbowItem
                || item instanceof TridentItem;
    }

    // Utility for rainbow colors
    public static int getRainbowColor(long ticks) {
        float hue = (ticks % 360) / 360f;
        return java.awt.Color.HSBtoRGB(hue, 1.0F, 1.0F);
    }
}
