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

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ArrayList<CurrencyData> data = new ArrayList<>();
        data.add(new CurrencyData(CurrencyData.CURRENCY_ETH, CurrencyData.CURRENCY_USD));
        data.add(new CurrencyData(CurrencyData.CURRENCY_BTC, CurrencyData.CURRENCY_EUR));
        data.add(new CurrencyData("EUR", "CNY"));
        data.add(new CurrencyData(CurrencyData.CURRENCY_GBP, CurrencyData.CURRENCY_JPY));
        data.add(new CurrencyData("LTC", "ETH"));
        ListView list = (ListView)findViewById(R.id.list);
        CurrencyAdapter adapter = new CurrencyAdapter(this, data);
        list.setAdapter(adapter);
    }
}
