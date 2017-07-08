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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ActivityConversionPreferences extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_preferences);
        
        TextView textConversion = (TextView)findViewById(R.id.text_preferences_conversion);
        TextView textConversionValue = (TextView)findViewById(R.id.text_preferences_conversion_value);
        TextView textNotifyIncrease = (TextView)findViewById(R.id.text_notify_increase);
        TextView textNotifyDecrease = (TextView)findViewById(R.id.text_notify_decrease);
        
        Intent intent = getIntent();
        String codeStrFrom = intent.getStringExtra(getString(R.string.key_intent_currency_from));
        String codeStrTo = intent.getStringExtra(getString(R.string.key_intent_currency_to));
        Conversion conversion = ActivityMain.conversions.get(codeStrFrom, codeStrTo);
        
        textConversion.setText(String.format(textConversion.getTag().toString(),
                                             conversion.currencyFrom.code,
                                             conversion.currencyTo.code));
        textConversionValue.setText(String.format(textConversionValue.getTag().toString(),
                                             conversion.currencyFrom.symbol,
                                             conversion.currencyTo.symbol,
                                             conversion.getValue()));
        textNotifyIncrease.setText(String.format(textNotifyIncrease.getTag().toString(),
                                                 conversion.currencyFrom.code));
        textNotifyDecrease.setText(String.format(textNotifyDecrease.getTag().toString(),
                                                 conversion.currencyFrom.code));
    }
}
