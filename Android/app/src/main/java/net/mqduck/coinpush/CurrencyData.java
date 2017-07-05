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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Created by mqduck on 7/4/17.
 */

class CurrencyData
{
    private final static String BASE_URL = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=%s&tsyms=%s";
    private final static String DATUM_VALUE = "PRICE";
    private final static String DATUM_CHANGE = "CHANGEPCT24HOUR";
    final static String CURRENCY_ETH = "ETH";
    final static String CURRENCY_BTC = "BTC";
    final static String CURRENCY_USD = "USD";
    final static String CURRENCY_EUR = "EUR";
    final static String CURRENCY_GBP = "GBP";
    final static String CURRENCY_JPY = "JPY";
    final static String CURRENCY_CNY = "CNY";
    final static String CURRENCY_LTC = "LTC";
    
    private final static HashMap<String, Currency> currencies;
    private final static String url;
    
    final Currency currency, conversion;
    private Double value = 0.0, change = 0.0;
    private static JSONObject json;
    
    static
    {
        currencies = new HashMap<>();
        currencies.put(CURRENCY_ETH, new Currency(CURRENCY_ETH, "Etherium (ETH)", "Ξ"));
        currencies.put(CURRENCY_BTC, new Currency(CURRENCY_BTC, "Bitcoin (BTC)", /*"\u20BF"*/ ""));
        currencies.put(CURRENCY_USD, new Currency(CURRENCY_USD, "US Dollar (USC)", "$"));
        currencies.put(CURRENCY_EUR, new Currency(CURRENCY_EUR, "Euro (EUR)", "€"));
        currencies.put(CURRENCY_GBP, new Currency(CURRENCY_GBP, "British Pound (GBP)", "£"));
        currencies.put(CURRENCY_JPY, new Currency(CURRENCY_JPY, "Japanese Yen (JPY)", "¥"));
        currencies.put(CURRENCY_CNY, new Currency(CURRENCY_CNY, "Chinese Yuan (CNY)", "¥"));
        currencies.put(CURRENCY_LTC, new Currency(CURRENCY_LTC, "Litecoin (LTC)", "Ł"));
    
        String codes = "";
        for(String code : CurrencyData.currencies.keySet())
            codes += code + ",";
        url = String.format(BASE_URL, codes, codes);
    }
    
    CurrencyData(final String currencyKey, final String conversionKey)
    {
        currency = currencies.get(currencyKey);
        conversion = currencies.get(conversionKey);
    }
    
    static void updateJson()
    {
        try
        {
            InputStream is = null;
            is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while((cp = rd.read()) != -1)
                sb.append((char)cp);
            json = new JSONObject(sb.toString()).getJSONObject("RAW");
        }
        catch(IOException | JSONException e)
        {
            e.printStackTrace();
        }
    }
    
    void update()
    {
        try
        {
            JSONObject mJson = json.getJSONObject(currency.code).getJSONObject(conversion.code);
            value = mJson.getDouble(DATUM_VALUE);
            change = mJson.getDouble(DATUM_CHANGE);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
    
    public double getValue() { return value; }
    public double getChange() { return change; }
}
