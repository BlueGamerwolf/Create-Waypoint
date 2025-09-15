package net.blue_gamerwolf.waypoint.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Heart extends Item {

    public Heart(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {

            // Get the MAX_HEALTH attribute safely
            var maxHealthAttr = player.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealthAttr != null) {
                double currentMaxHealth = maxHealthAttr.getBaseValue();

                // Optional: cap max health to 40 (20 hearts)
                if (currentMaxHealth < 40) {
                    maxHealthAttr.setBaseValue(currentMaxHealth + 2);

                    // Heal player by 2 to match the new max
                    player.setHealth(player.getHealth() + 2);

                    // Consume the heart
                    player.getItemInHand(hand).shrink(1);
                }
            }
        }

        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), world.isClientSide);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }
}
