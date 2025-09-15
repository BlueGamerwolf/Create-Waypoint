package net.blue_gamerwolf.waypoint.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Knife extends Item {

    public Knife(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            double currentMaxHealth = player.getAttributeValue(Attributes.MAX_HEALTH);

            if (currentMaxHealth > 2) { // Ensure player doesn't go below 1 heart
                // Reduce max health by 2
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(currentMaxHealth - 2);

                // Give a Heart item
                ItemStack heartStack = new ItemStack(net.blue_gamerwolf.waypoint.registry.WaypointItems.HEART.get());

                if (!player.addItem(heartStack)) {
                    // Drop on ground if inventory full
                    player.drop(heartStack, false);
                }
            }
        }

        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), world.isClientSide);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }
}
