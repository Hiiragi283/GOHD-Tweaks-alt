package hiiragi283.gohd_tweaks.util;

import hiiragi283.gohd_tweaks.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockCommon extends Block {

    //Blockstateのプロパティの定義
    public static final PropertyInteger type4 = PropertyInteger.create("type", 0, 3);
    public static final PropertyInteger type8 = PropertyInteger.create("type", 0, 7);
    public static final PropertyInteger type16 = PropertyInteger.create("type", 0, 15);
    //メタデータの最大値の定義
    public final int maxMeta;

    //Blockを登録するメソッド
    public BlockCommon(Material Material, String ID, int max) {
        super(Material); //Materialを設定
        this.setRegistryName(Reference.MOD_ID, ID); //IDの設定
        this.setCreativeTab(CreativeTabs.DECORATIONS); //表示するクリエイティブタブの設定
        this.setDefaultState(this.blockState.getBaseState().withProperty(type16, 0)); //デフォルトのBlockstateをtype16の0番に設定
        this.setUnlocalizedName(ID); //翻訳キーをIDから取得
        this.maxMeta = max; //メタデータの最大値を代入
    }

    //メタデータの最大値を得るメソッド
    public int getMaxMeta() {
        //デフォルトは15が最大値
        return 15;
    }

    //Blockstateの登録をするメソッド
    @Override
    protected BlockStateContainer createBlockState() {
        //type16を使用すると、Blockstateは"type=0"から"type=15"までが登録される
        return new BlockStateContainer(this, type16);
    }

    //Blockstateからメタデータを得るメソッド
    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        //type16をもとにBlockstateからメタデータを返す
        i = state.getValue(type16);
        //メタデータが最大値よりも大きい場合、最大値を返す
        if (i > maxMeta) i = maxMeta;
        return i;
    }

    //メタデータからBlockstateを得るメソッド
    @Override
    public IBlockState getStateFromMeta(int meta) {
        //ビット演算子でメタデータを0~5までに絞り込む
        int i = meta & 15;
        //type16をもとに指定したメタデータからBlockstateを返す
        IBlockState state = this.getDefaultState().withProperty(type16, i);
        return state;
    }

    //Blockstateをもとにドロップするアイテムのメタデータを指定するメソッド
    @Override
    public int damageDropped(IBlockState state) {
        //type16をもとにBlockstateからメタデータを返す
        int i = state.getValue(type16);
        //メタデータが最大値よりも大きい場合、最大値を返す
        if (i > maxMeta) i = maxMeta;
        return i;
    }

    //ドロップするアイテムを得るメソッド
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        //Blockstateからブロックを取得し、更にそこからアイテムを取得して返す
        return Item.getItemFromBlock(state.getBlock());
    }

    //ドロップする確率を得るメソッド
    @Override
    public int quantityDropped(Random random) {
        //常にドロップさせるので1を返す
        return 1;
    }
}
