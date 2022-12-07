package hiiragi283.gohd_tweaks.util;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBlockCommon extends ItemBlock {

    //Itemの定義
    public ItemBlockCommon(Block block) {
        super(block); //BlockからItemを生成する
        this.setRegistryName(block.getRegistryName()); //翻訳キーはblockのものから取得
        this.setHasSubtypes(true); //メタデータを使用する
    }

    //メタデータの最大値を得るメソッド
    public int getMaxMeta() {
        //デフォルトは15が最大値
        return 15;
    }

    //メタデータを得るメソッド
    @Override
    public int getMetadata(int damage) {
        //ブロックのダメージ値を返す
        return damage;
    }

    //翻訳キーを得るメソッド
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        //取得した値とメタデータの最大値を比較し、小さい方を返す
        int j = Math.min(stack.getMetadata(), getMaxMeta());
        //メタデータごとに異なる翻訳キーを返す
        return super.getUnlocalizedName() + "." + j;
    }

    //メタデータ付きアイテムをクリエイティブタブに登録するメソッド
    @Override
    @SideOnly(Side.CLIENT) //Client側のみ
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (this.isInCreativeTab(tab)) {
            //listの定義
            List<ItemStack> list = Lists.newArrayList();
            //メタデータの最大値まで処理を繰り返す
            for (int i = 0; i < getMaxMeta() + 1; i++) {
                list.add(new ItemStack(this, 1, i));
            }
            //list内のすべてのアイテムをクリエイティブタブに登録
            subItems.addAll(list);
        }
    }
}
