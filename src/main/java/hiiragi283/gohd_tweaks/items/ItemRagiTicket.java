package hiiragi283.gohd_tweaks.items;

import hiiragi283.gohd_tweaks.base.ItemBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemRagiTicket extends ItemBase {

    //Itemの定義
    public ItemRagiTicket() {
        super("ragi_ticket"); //IDの設定
    }


    //メタデータの最大値を得るメソッド
    public int getMaxMeta() {
        //0を返す
        return 0;
    }

    //Rarityを得るメソッド
    public EnumRarity getRarity(ItemStack item) {
        //EPICを返す
        return EnumRarity.EPIC;
    }
}
