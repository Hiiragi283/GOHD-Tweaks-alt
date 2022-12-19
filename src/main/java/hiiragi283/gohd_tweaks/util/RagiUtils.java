package hiiragi283.gohd_tweaks.util;

import defeatedcrow.hac.core.util.DCUtil;
import hiiragi283.gohd_tweaks.GOHDInit;
import hiiragi283.gohd_tweaks.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Objects;

public class RagiUtils {

    //コマンドを実行するメソッド
    public static void executeCommand(ICommandSender sender, String command) {
        Objects.requireNonNull(Reference.server).getCommandManager().executeCommand(sender, command);
    }

    //ResourceLocationからBlockを取得するメソッド
    //Blockがnullの場合は岩盤を返す
    public static Block getBlock(String domain, String path) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(domain, path));
        if (Objects.nonNull(block)) return block;
        else return ForgeRegistries.BLOCKS.getValue(new ResourceLocation("mimecraft", "bedrock"));
    }

    //液体名からFluidを取得するメソッド
    //Fluidがnullの場合は水を返す
    public static Fluid getFluid(String name) {
        Fluid fluid = net.minecraftforge.fluids.FluidRegistry.getFluid(name);
        if (Objects.nonNull(fluid)) return fluid;
        else return net.minecraftforge.fluids.FluidRegistry.getFluid("water");
    }

    //ResourceLocationからItemを取得するメソッド
    //Itemがnullの場合はらぎチケットを返す
    public static Item getItem(String domain, String path) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(domain, path));
        if (Objects.nonNull(item)) return item;
        else return GOHDInit.ItemRagiTicket;
    }

    //ResourceLocationなどからItemStackを取得するメソッド
    //ItemStackがnullの場合はEMPTY1を返す
    public static ItemStack getStack(String domain, String path, int amount, int meta) {
        ItemStack stack = new ItemStack(getItem(domain, path), amount, meta);
        if (DCUtil.isEmpty(stack)) return ItemStack.EMPTY;
        else return stack;
    }

    //ResourceLocationなどからIBlockStateを取得するメソッド
    //IBLockStateがnullの場合はデフォルトのblockstateを返す
    public static IBlockState getState(String domain, String path, int meta) {
        Block block = getBlock(domain, path);
        IBlockState state = block.getStateFromMeta(meta);
        if (Objects.nonNull(state)) return state;
        else return getBlock(domain, path).getDefaultState();
    }

    public static IBlockState getState(Block block, int meta) {
        IBlockState state = block.getStateFromMeta(meta);
        if (Objects.nonNull(state)) return state;
        else return block.getDefaultState();
    }

    //ResourceLocationからPotionを取得するメソッド
    //Potionがnullの場合は不運を返す
    public static Potion getPotion(String domain, String path) {
        Potion potion = ForgeRegistries.POTIONS.getValue(new ResourceLocation(domain, path));
        if (Objects.nonNull(potion)) return potion;
        else return ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "unluck"));
    }

    //ResourceLocationなどからPotionEffectを取得するメソッド
    public static PotionEffect getPotionEffect(String domain, String path, int time, int level) {
        return new PotionEffect(getPotion(domain, path), time, level);
    }

    //ResourceLocationからSoundEventを取得するメソッド
    //SoundEventがnullの場合はレベルアップの音を返す
    public static SoundEvent getSound(String domain, String path) {
        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(domain, path));
        if (Objects.nonNull(sound)) return sound;
        else return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.player.levelup"));
    }

    //titleコマンドをより簡潔に実行するメソッド
    public static void setTitle(EntityPlayer player, String title, String subtitle) {
        //コマンドの実行結果を出力しないようにする
        RagiUtils.executeCommand(player, "gamerule sendCommandFeedback false");
        //titleの設定
        RagiUtils.executeCommand(player, "title @p times 20 60 20");
        RagiUtils.executeCommand(player, "title @p title [{\"translate\":\"" + title + "\"}]");
        RagiUtils.executeCommand(player, "title @p subtitle {\"translate\":\"" + subtitle + "\"}");
    }

    //Hypixelで慣れ親しんだこの音声を再び聞いたとき，感動で泣きそうになりました
    public static void soundHypixel(World world, BlockPos pos) {
        world.playSound(null, pos, RagiUtils.getSound("minecraft", "entity.player.levelup"), SoundCategory.BLOCKS, 1.0F, 0.5F);
    }
}
