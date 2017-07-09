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

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mqduck on 7/4/17.
 */

class ConversionAdapter extends ArrayAdapter<Conversion>
{
    //private final Context context;
    private final ConversionList conversions;
    private LayoutInflater inflater;
    private int updateDelay = 10000;
    private final Runnable updateRunnable;
    private final Handler updateHandler;
    
    ConversionAdapter(final Context context, final ConversionList conversions)
    {
        super(context, -1, conversions);
        //this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.conversions = conversions;
        
        updateRunnable = new Runnable() {
            @Override public void run()
            {
                updateData();
                updateHandler.postDelayed(this, updateDelay);
            }
        };
        updateHandler = new Handler();
        updateHandler.postDelayed(updateRunnable, 0);
    }
    
    void updateData()
    {
        new AsyncTask<Void, Void, Void>() {
            @Override protected Void doInBackground(Void... params)
            {
                Currency.updateJsons();
                for(Conversion conversion : conversions)
                    conversion.update();
                return null;
            }
            @Override protected void onPostExecute(Void result) { notifyDataSetChanged(); }
        }.execute();
    }
    
    @NonNull
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent)
    {
        View conversionView = convertView == null ? inflater.inflate(R.layout.conversion, parent, false) : convertView;
        TextView textCurrencyFrom = (TextView)conversionView.findViewById(R.id.textViewCurrencyFrom);
        TextView textValue = (TextView)conversionView.findViewById(R.id.textViewValue);
        TextView textChange = (TextView)conversionView.findViewById(R.id.textViewChange);
        ImageView iconFrom = (ImageView)conversionView.findViewById(R.id.icon_from);
        TextView emojiFrom = (TextView)conversionView.findViewById(R.id.emoji_from);
        
        Conversion conversion = conversions.get(position);
        textCurrencyFrom.setText(conversion.currencyFrom.toString(true));
        textValue.setText(conversion.currencyTo.getValueStr(conversion.getValue(), true));
        iconFrom.setImageResource(conversion.currencyFrom.icon);
        emojiFrom.setTextSize(TypedValue.COMPLEX_UNIT_PX, ActivityMain.emojiSize);
        emojiFrom.setText(conversion.currencyFrom.emoji);
        
        double change = conversion.getChange();
        textChange.setText(String.format(textChange.getTag().toString(), change));
        if(change < 0)
            textChange.setTextColor(Color.rgb((int)Math.round(change * -30), 0, 0));
        else
            textChange.setTextColor(Color.rgb(0, (int)Math.round(change * 30), 0));
        
        return conversionView;
    }
    
    ConversionList getConversions() { return conversions; }
}
