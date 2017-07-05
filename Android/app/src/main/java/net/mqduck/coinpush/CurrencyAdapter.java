package net.mqduck.coinpush;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    private int updateDelay = 6000;
    
    CurrencyAdapter(final Context context, final List<CurrencyData> data)
    {
        super(context, -1, data);
        //this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        
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
        View dataView;
        //if(convertView == null)
            dataView = inflater.inflate(R.layout.currency_layout, parent, false);
        //else
        //    dataView = convertView;
        
        
        CurrencyData datum = data.get(position);
        
        TextView textCurrency = (TextView)dataView.findViewById(R.id.textViewCurrency);
        TextView textValue = (TextView)dataView.findViewById(R.id.textViewValue);
        TextView textChange = (TextView)dataView.findViewById(R.id.textViewChange);
        
        textCurrency.setText(datum.currency.name);
        textValue.setText(String.format(Locale.getDefault(), "%s %.3f %s", datum.conversion.symbol, datum.getValue(), datum.conversion.code));
        
        double change = datum.getChange();
        textChange.setText(String.format(Locale.getDefault(), "%+.4f%%", change));
        if(change < 0)
            textChange.setTextColor(Color.rgb((int)Math.round(change * -30), 0, 0));
        else
            textChange.setTextColor(Color.rgb(0, (int)Math.round(change * 30), 0));
        
        return dataView;
    }
}
