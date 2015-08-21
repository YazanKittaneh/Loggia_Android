package com.loggia.Helpers;

import java.util.Random;
import com.loggia.R;

/**
 * Created by L7 on 8/16/15.
 */
public class StockImageRandomizer {


    private static final Random RANDOM = new Random();

    public int getRandomStockDrawable() {
        switch (RANDOM.nextInt(3)) {
            default:
            case 0:
                return R.drawable.stock_1;
            case 1:
                return R.drawable.stock_2;
            case 2:
                return R.drawable.stock_3;
        }
    }
}
