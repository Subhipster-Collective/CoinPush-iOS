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

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mqduck on 7/4/17.
 */

class Conversion
{
    private final static String DATUM_VALUE = "PRICE";
    private final static String DATUM_CHANGE = "CHANGEPCT24HOUR";
    
    private final static String INTENT_KEY_FROM = "net.mqduck.coinpush.Conversion.currencyFrom";
    private final static String INTENT_KEY_TO = "net.mqduck.coinpush.Conversion.currencyTo";
    private final static String INTENT_KEY_VALUE = "net.mqduck.coinpush.Conversion.value";
    private final static String INTENT_KEY_CHANGE = "net.mqduck.coinpush.Conversion.change";
    
    final Currency currencyFrom, currencyTo;
    private Double value = 0.0, change = 0.0;
    
    Conversion(final Currency.Code codeFrom, final Currency.Code codeTo)
    {
        currencyFrom = Currency.currencies.get(codeFrom);
        currencyTo = Currency.currencies.get(codeTo);
    }
    
    Conversion(final Intent intent)
    {
        Currency.Code codeFrom = Currency.Code.valueOf(intent.getStringExtra(INTENT_KEY_FROM));
        Currency.Code codeTo = Currency.Code.valueOf(intent.getStringExtra(INTENT_KEY_TO));
        currencyFrom = Currency.currencies.get(codeFrom);
        currencyTo = Currency.currencies.get(codeTo);
        value = intent.getDoubleExtra(INTENT_KEY_VALUE, 0);
        change = intent.getDoubleExtra(INTENT_KEY_CHANGE, 0);
    }
    
    void packIntent(Intent intent)
    {
        intent.putExtra(INTENT_KEY_FROM, currencyFrom.code);
        intent.putExtra(INTENT_KEY_TO, currencyTo.code);
        intent.putExtra(INTENT_KEY_VALUE, value);
        intent.putExtra(INTENT_KEY_CHANGE, change);
    }
    
    void update()
    {
        try
        {
            JSONObject json = currencyTo.json.getJSONObject(currencyFrom.code.toString())
                                             .getJSONObject(currencyTo.code.toString());
            value = json.getDouble(DATUM_VALUE);
            change = json.getDouble(DATUM_CHANGE);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
    
    public double getValue() { return value; }
    public double getChange() { return change; }
}
