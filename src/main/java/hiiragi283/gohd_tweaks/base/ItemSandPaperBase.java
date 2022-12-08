package hiiragi283.gohd_tweaks.base;

import com.google.common.collect.Sets;
import hiiragi283.gohd_tweaks.Reference;
import hiiragi283.gohd_tweaks.util.RagiMap;
import hiiragi283.gohd_tweaks.util.RagiUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.Set;

//Sandpaper系の継承用のクラス
public class ItemSandPaperBase extends ItemTool {

    public static final Set<Block> BLOCKS = Sets.newHashSet(RagiUtils.getBlock("minecraft", "air"));

    public ItemSandPaperBase(String ID, int maxDamage) {
        super(ToolMaterial.WOOD, BLOCKS); //ToolMaterialはWOODを継承，採掘は行わないので対象のブロックは適当に空気を設定
        this.setRegistryName(Reference.MOD_ID, ID); //IDの設定
        this.setCreativeTab(CreativeTabs.TOOLS); //表示するクリエイティブタブの設定
        this.setMaxDamage(maxDamage); //最大耐久地を1024に設定
        this.setUnlocalizedName(ID); //翻訳キーをIDから取得
    }

    //アイテムを右クリックすると呼ばれるイベント
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        //保持しているアイテムをItemStack型で取得
        ItemStack stack = player.getHeldItem(hand);
        //サーバー側のみで実行
        if (!world.isRemote) {
            //playerの視線を取得
            RayTraceResult ray = this.rayTrace(world, player, false);
            //視線の先がブロックの場合
            if (ray.typeOfHit == RayTraceResult.Type.BLOCK) {
                //ブロックまわりの値の取得
                BlockPos pos = ray.getBlockPos();
                IBlockState state = world.getBlockState(pos);
                Block block = state.getBlock();
                //MapSandpaperから対応する組み合わせがある場合
                if (Objects.nonNull(RagiMap.MapSandpaper.get(state))) {
                    //対応するstateで置き換える
                    world.setBlockState(pos, RagiMap.MapSandpaper.get(state));
                    //stackの耐久地を1減らす
                    stack.damageItem(1, player);
                    //とりあえず音鳴らすか
                    world.playSound(null, player.getPosition(), RagiUtils.getSound("minecraft", "block.gravel.hit"), SoundCategory.BLOCKS, 1.0F, 0.1F);
                }
                //MapSandpaperBlockから対応する組み合わせがある場合
                if (Objects.nonNull(RagiMap.MapSandpaperBlock.get(block))) {
                    //対応するstateで置き換える
                    world.setBlockState(pos, RagiMap.MapSandpaperBlock.get(block));
                    //stackの耐久地を1減らす
                    stack.damageItem(1, player);
                    //とりあえず音鳴らすか
                    world.playSound(null, player.getPosition(), RagiUtils.getSound("minecraft", "block.gravel.hit"), SoundCategory.BLOCKS, 1.0F, 0.1F);
                }
            }
            //実験は成功だ!
            return new ActionResult(EnumActionResult.SUCCESS, stack);
        }
        //何がいけなかったのだろうか
        return new ActionResult(EnumActionResult.FAIL, stack);
    }
}
