package hiiragi283.gohd_tweaks.items;

import hiiragi283.gohd_tweaks.GOHDInit;
import hiiragi283.gohd_tweaks.base.ItemBlockBase;

public class ItemGroutFormed extends ItemBlockBase {

    //Itemの定義
    public ItemGroutFormed() {
        super(GOHDInit.BlockGroutFormed); //BlockからItemを生成する
    }

    //メタデータの最大値を得るメソッド
    public int getMaxMeta() {
        //2を返す
        return 2;
    }
}
