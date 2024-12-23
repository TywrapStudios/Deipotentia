package net.tywrapstudios.deipotentia.item.sickles;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NymphSickleItem extends HoeItem {
    public NymphSickleItem(Settings settings) {
        super(ToolMaterials.DIAMOND, 3, -2f, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        stack.removeCustomName();
        stack.setCustomName(Text.translatable(this.getTranslationKey()).setStyle(Style.EMPTY.withColor(
                Formatting.GREEN).withItalic(false)));
        return stack.getName();
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity user = context.getPlayer();
        Hand hand = context.getHand();
        if (!context.getWorld().isClient()) {
            boolean foundBlock = false;
            if (isAcceptAbleBlock(state)) {
                breakCircle(world, pos, user);
                assert user != null;
                context.getStack().damage(1, user, playerEntity -> playerEntity.sendToolBreakStatus(hand));
                user.swingHand(hand);
                foundBlock = true;
            }
            if (!foundBlock) {
                return super.useOnBlock(context);
            }
        }
        return super.useOnBlock(context);
    }

    private static boolean isAcceptAbleBlock(BlockState state) {
        return state.isIn(BlockTags.CROPS) && ((CropBlock) state.getBlock()).isMature(state);
    }

    private static void breakCircle(World world, BlockPos center, Entity entity) {
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                if (Math.sqrt(x * x + z * z) <= 2) {
                    BlockPos pos = center.add(x, 0, z);
                    if (isAcceptAbleBlock(world.getBlockState(pos))) {
                        world.breakBlock(pos, true, entity);
                    }
                }
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
        tooltip.add(Text.translatable("tooltip.deipotentia.sickle.nymph_sickle").formatted(Formatting.ITALIC, Formatting.GOLD));
        tooltip.add(Text.translatable("tooltip.deipotentia.sickle.nymph_sickle.sec").formatted(Formatting.ITALIC, Formatting.DARK_GRAY));
        } else {
            Text shift = Text.literal("[").formatted(Formatting.GOLD)
                    .append(Text.translatable("tooltip.deipotentia.misc.hold_shift").formatted(Formatting.GRAY, Formatting.ITALIC))
                    .append(Text.literal("]").formatted(Formatting.GOLD));
            tooltip.add(shift);
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}