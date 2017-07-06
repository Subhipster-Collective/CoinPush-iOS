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

import android.support.annotation.DrawableRes;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mqduck on 7/4/17.
 */

class Currency
{
    enum Code { ETH, BTC, LTC, DASH, XMR, NXT, ZEC, DGB, XRP, USD, EUR, GBP, JPY, CNY; }
    
    private final static String BASE_URL = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=%s&tsyms=%s";
    
    final static HashMap<Code, Currency> currencies;
    
    static
    {
        currencies = new HashMap<>();
        currencies.put(Code.ETH, new Currency("ETH", "Etherium (ETH)", "Ξ", R.mipmap.ic_eth));
        currencies.put(Code.BTC, new Currency("BTC", "Bitcoin (BTC)",
                                                  android.os.Build.VERSION.SDK_INT < 26 ? "Ƀ" : "\u20BF",
                                                  R.mipmap.ic_btc));
        currencies.put(Code.LTC, new Currency("LTC", "Litecoin (LTC)", "Ł", R.mipmap.ic_ltc));
        currencies.put(Code.DASH, new Currency("DASH", "DigitalCash (DASH)", "DASH", R.mipmap.ic_dash));
        currencies.put(Code.XMR, new Currency("XMR", "Monero (XMR)", "ɱ", R.mipmap.ic_xmr));
        currencies.put(Code.NXT, new Currency("NXT", "Nxt (NXT)", "NXT", R.mipmap.ic_nxt));
        currencies.put(Code.ZEC, new Currency("ZEC", "ZCash (ZEC)", "ZEC", R.mipmap.ic_zec));
        currencies.put(Code.DGB, new Currency("DGB", "DigiByte (DGB)", "", R.mipmap.ic_dgb));
        currencies.put(Code.XRP, new Currency("XRP", "Ripple (XRP)", "", R.mipmap.ic_xrp));
        
        currencies.put(Code.USD, new Currency("USD", "US Dollar (USC)", "$", "\uD83C\uDDFA\uD83C\uDDF8"));
        currencies.put(Code.EUR, new Currency("EUR", "Euro (EUR)", "€", "\uD83C\uDDEA\uD83C\uDDFA"));
        currencies.put(Code.GBP, new Currency("GBP", "British Pound (GBP)", "£", "\uD83C\uDDEC\uD83C\uDDE7"));
        currencies.put(Code.JPY, new Currency("JPY", "Japanese Yen (JPY)", "¥", "\uD83C\uDDEF\uD83C\uDDF5"));
        currencies.put(Code.CNY, new Currency("CNY", "Chinese Yuan (CNY)", "¥", "\uD83C\uDDE8\uD83C\uDDF3"));
    }
    
    final String code;
    final String name;
    final String symbol;
    @DrawableRes final int icon;
    final String emoji;
    
    private ArrayList<Currency> conversions = new ArrayList<>(); // List of currencies to convert to this one // change to Set?
    private String url = null;
    JSONObject json = null;
    
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
    
    static void updateJsons()
    {
        for(Code code : Code.values())
            currencies.get(code).updateJson();
    }
    
    private void updateJsonURL()
    {
        if(conversions.isEmpty())
        {
            url = null;
            return;
        }
        
        String conversionCodes = "";
        for(Currency currency : conversions)
            conversionCodes += currency.code + ",";
        url = String.format(BASE_URL, conversionCodes, code);
    }
    
    private void updateJson()
    {
        if(url == null)
        {
            json = null;
            return;
        }
        
        Log.d("url", url);
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
    
    boolean addConversion(final Currency currency)
    {
        if(conversions.add(currency))
        {
            updateJsonURL();
            return true;
        }
        return false;
    }
    
    boolean removeConversion(final Currency currency)
    {
        if(conversions.remove(currency))
        {
            updateJsonURL();
            return true;
        }
        return false;
    }
}
