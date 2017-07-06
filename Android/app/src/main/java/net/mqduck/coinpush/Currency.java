/*
 * Copyright 2017 Jeffrey Thomas Piercy
 *
 * This file is part of CoinPush.
 *
 * CoinPush is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CoinPush is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CoinPush.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.mqduck.coinpush;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

/**
 * Created by mqduck on 7/4/17.
 */

class Currency
{
    final String code;
    final String name;
    final String symbol;
    @DrawableRes final int icon;
    final String emoji;
    
    Currency(final String code, final String name, final String symbol, @DrawableRes final int icon)
    {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
        this.icon = icon;
        emoji = "";
    }
    
    Currency(final String code, final String name, final String symbol, final String emoji)
    {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
        icon = R.mipmap.ic_empty;
        this.emoji = emoji;
    }
    
    Currency(final String code, final String name, final String symbol)
    {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
        icon = R.mipmap.ic_empty;
        emoji = "";
    }
}
