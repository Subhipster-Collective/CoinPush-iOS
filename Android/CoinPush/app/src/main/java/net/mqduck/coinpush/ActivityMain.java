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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ActivityMain extends AppCompatActivity
{
    static ConversionList conversions = new ConversionList();
    static ConversionAdapter conversionAdapter;
    static float emojiSize;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        emojiSize = (float)0.7 * getResources().getDrawable(R.mipmap.ic_eth).getIntrinsicHeight();
        
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        
        setSupportActionBar(toolbar);
    
        conversions.add(new Conversion(Currency.Code.ETH, Currency.Code.USD));
        conversions.add(new Conversion(Currency.Code.BTC, Currency.Code.EUR));
        conversions.add(new Conversion(Currency.Code.LTC, Currency.Code.JPY));
        conversions.add(new Conversion(Currency.Code.NXT, Currency.Code.BTC));
        conversions.add(new Conversion(Currency.Code.XMR, Currency.Code.CNY));
        ListView list = (ListView)findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Conversion conversion = conversions.get(position);
                Intent intent = new Intent(parent.getContext(), ActivityConversionPreferences.class);
                intent.putExtra(getString(R.string.key_intent_currency_from), conversion.currencyFrom.code.toString());
                intent.putExtra(getString(R.string.key_intent_currency_to), conversion.currencyTo.code.toString());
                startActivity(intent);
            }
        });
        conversionAdapter = new ConversionAdapter(this, conversions);
        list.setAdapter(conversionAdapter);
        
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        /*// Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings)
        {
            return true;
        }
        
        return super.onOptionsItemSelected(item);*/
        
        switch(item.getItemId())
        {
        case R.id.action_settings:
            return true;
        case R.id.action_add_currency:
            new FragmentAddConversion().show(getFragmentManager(), "FOO");
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
