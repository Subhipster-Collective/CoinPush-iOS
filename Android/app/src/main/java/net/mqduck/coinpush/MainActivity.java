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
        data.add(new CurrencyData(CurrencyData.CURRENCY_ETHER, CurrencyData.CURRENCY_USD));
        data.add(new CurrencyData(CurrencyData.CURRENCY_BTC, CurrencyData.CURRENCY_USD));
        data.add(new CurrencyData("EUR", "USD"));
        data.add(new CurrencyData("ETH", "BTC"));
        ListView list = (ListView)findViewById(R.id.list);
        CurrencyAdapter adapter = new CurrencyAdapter(this, data);
        list.setAdapter(adapter);
    }
}
