package net.mqduck.coinpush;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by mqduck on 7/4/17.
 */

public class CurrencyData
{
    private final static String BASE_URL = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=%s&tsyms=BTC,USD,EUR";
    private final static String DATUM_VALUE = "PRICE";
    private final static String DATUM_CHANGE = "CHANGEPCT24HOUR";
    
    final static String CURRENCY_ETHER = "ETH";
    final static String CURRENCY_BTC = "BTC";
    final static String CURRENCY_USD = "USD";
    
    final static HashMap<String, String> currencyNames;
    
    final private String url, name, currency, conversion;
    private JSONObject json = null;
    private Double value = 0.0, change = 0.0;
    
    static
    {
        currencyNames = new HashMap<>();
        currencyNames.put(CURRENCY_ETHER, "Etherium");
        currencyNames.put(CURRENCY_BTC, "Bitcoin");
        currencyNames.put(CURRENCY_USD, "USD");
    }
    
    CurrencyData(final String currency, final String conversion)
    {
        this.currency = currency;
        this.conversion = conversion;
        name = currencyNames.get(currency);
        //url = URL_PART_1 + currency + URL_PART_2;
        url = String.format(BASE_URL, currency);
        update();
    }
    
    void update()
    {
        try
        {
            json = new RetrieveJson().execute(url).get();
            value = json.getJSONObject(currency).getJSONObject(conversion).getDouble(DATUM_VALUE);
            change = json.getJSONObject(currency).getJSONObject(conversion).getDouble(DATUM_CHANGE);
        }
        catch(InterruptedException | ExecutionException | JSONException e)
        {
            e.printStackTrace();
        }
    
    }
    
    public String getCurrencyName() { return name; }
    public String getCurrency() { return currency; }
    public String getConversion() { return conversion; }
    public double getValue() { return value; }
    public double getChange() { return change; }
}
