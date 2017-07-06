package net.mqduck.coinpush;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

import java.util.List;
import java.util.Locale;

/**
 * Created by mqduck on 7/4/17.
 */

class CurrencyAdapter extends ArrayAdapter<CurrencyData>
{
    //private final Context context;
    private final List<CurrencyData> data;
    private LayoutInflater inflater;
    private int updateDelay = 60000;
    private static float fontSize;
    
    CurrencyAdapter(final Context context, final List<CurrencyData> data)
    {
        super(context, -1, data);
        //this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        fontSize = (float)0.7 * context.getResources().getDrawable(R.mipmap.ic_eth).getIntrinsicHeight();
        
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override public void run()
            {
                new AsyncTask<CurrencyData, Void, Void>() {
                    @Override protected Void doInBackground(CurrencyData... data)
                    {
                        CurrencyData.updateJson();
                        for(CurrencyData datum : data)
                            datum.update();
                        return null;
                    }
                    @Override protected void onPostExecute(Void result)
                    {
                        notifyDataSetChanged();
                    }
                }.execute(data.toArray(new CurrencyData[0]));
                handler.postDelayed(this, updateDelay);
            }
        }, 0);
    }
    
    @NonNull
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent)
    {
        View dataView = convertView == null ? inflater.inflate(R.layout.currency, parent, false) : convertView ;
        TextView textCurrency = (TextView)dataView.findViewById(R.id.textViewCurrency);
        TextView textValue = (TextView)dataView.findViewById(R.id.textViewValue);
        TextView textChange = (TextView)dataView.findViewById(R.id.textViewChange);
        ImageView iconFrom = (ImageView)dataView.findViewById(R.id.icon_from);
        TextView emojiFrom = (TextView)dataView.findViewById(R.id.emoji_from);
        
        CurrencyData datum = data.get(position);
        textCurrency.setText(datum.currency.name);
        textValue.setText(String.format(Locale.getDefault(), "%s %.3f %s",
                                        datum.conversion.symbol, datum.getValue(), datum.conversion.code));
        iconFrom.setImageResource(datum.currency.icon);
        emojiFrom.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
        emojiFrom.setText(datum.currency.emoji);
        
        double change = datum.getChange();
        textChange.setText(String.format(Locale.getDefault(), "%+.4f%%", change));
        if(change < 0)
            textChange.setTextColor(Color.rgb((int)Math.round(change * -30), 0, 0));
        else
            textChange.setTextColor(Color.rgb(0, (int)Math.round(change * 30), 0));
        
        
        return dataView;
    }
}
