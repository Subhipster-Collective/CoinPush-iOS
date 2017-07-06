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
    final static String CURRENCY_LTC = "LTC";
    final static String CURRENCY_DASH = "DASH";
    final static String CURRENCY_XMR = "XMR";
    final static String CURRENCY_NXT = "NXT";
    final static String CURRENCY_ZEC = "ZEC";
    final static String CURRENCY_DGB = "DGB";
    final static String CURRENCY_XRP = "XRP";
    
    final static String CURRENCY_USD = "USD";
    final static String CURRENCY_EUR = "EUR";
    final static String CURRENCY_GBP = "GBP";
    final static String CURRENCY_JPY = "JPY";
    final static String CURRENCY_CNY = "CNY";
    
    private final static HashMap<String, Currency> currencies;
    private final static String url;
    
    final Currency currency, conversion;
    private Double value = 0.0, change = 0.0;
    private static JSONObject json;
    
    static
    {
        currencies = new HashMap<>();
        currencies.put(CURRENCY_ETH, new Currency(CURRENCY_ETH, "Etherium (ETH)", "Ξ", R.mipmap.ic_eth));
        currencies.put(CURRENCY_BTC, new Currency(CURRENCY_BTC, "Bitcoin (BTC)",
                                                  android.os.Build.VERSION.SDK_INT < 26 ? "Ƀ" : "\u20BF",
                                                  R.mipmap.ic_btc));
        currencies.put(CURRENCY_LTC, new Currency(CURRENCY_LTC, "Litecoin (LTC)", "Ł", R.mipmap.ic_ltc));
        currencies.put(CURRENCY_DASH, new Currency(CURRENCY_DASH, "DigitalCash (DASH)", "DASH", R.mipmap.ic_dash));
        currencies.put(CURRENCY_XMR, new Currency(CURRENCY_XMR, "Monero (XMR)", "ɱ", R.mipmap.ic_xmr));
        currencies.put(CURRENCY_NXT, new Currency(CURRENCY_NXT, "Nxt (NXT)", "NXT", R.mipmap.ic_nxt));
        currencies.put(CURRENCY_ZEC, new Currency(CURRENCY_ZEC, "ZCash (ZEC)", "ZEC", R.mipmap.ic_zec));
        currencies.put(CURRENCY_DGB, new Currency(CURRENCY_DGB, "DigiByte (DGB)", "", R.mipmap.ic_dgb));
        currencies.put(CURRENCY_XRP, new Currency(CURRENCY_XRP, "Ripple (XRP)", "", R.mipmap.ic_xrp));
        
        currencies.put(CURRENCY_USD, new Currency(CURRENCY_USD, "US Dollar (USC)", "$", "\uD83C\uDDFA\uD83C\uDDF8"));
        currencies.put(CURRENCY_EUR, new Currency(CURRENCY_EUR, "Euro (EUR)", "€", "\uD83C\uDDEA\uD83C\uDDFA"));
        currencies.put(CURRENCY_GBP, new Currency(CURRENCY_GBP, "British Pound (GBP)", "£", "\uD83C\uDDEC\uD83C\uDDE7"));
        currencies.put(CURRENCY_JPY, new Currency(CURRENCY_JPY, "Japanese Yen (JPY)", "¥", "\uD83C\uDDEF\uD83C\uDDF5"));
        currencies.put(CURRENCY_CNY, new Currency(CURRENCY_CNY, "Chinese Yuan (CNY)", "¥", "\uD83C\uDDE8\uD83C\uDDF3"));
        
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
            InputStream stream = null;
            stream = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
            StringBuilder strBuilder = new StringBuilder();
            int cp;
            while((cp = reader.read()) != -1)
                strBuilder.append((char)cp);
            json = new JSONObject(strBuilder.toString()).getJSONObject("RAW");
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
