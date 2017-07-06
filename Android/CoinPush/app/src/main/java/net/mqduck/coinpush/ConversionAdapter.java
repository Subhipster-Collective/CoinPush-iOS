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

import java.util.List;
import java.util.Locale;

/**
 * Created by mqduck on 7/4/17.
 */

class ConversionAdapter extends ArrayAdapter<ConversionData>
{
    //private final Context context;
    private final List<ConversionData> data;
    private LayoutInflater inflater;
    private int updateDelay = 60000;
    private static float fontSize;
    
    ConversionAdapter(final Context context, final ConversionDataList data)
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
                new AsyncTask<ConversionData, Void, Void>() {
                    @Override protected Void doInBackground(ConversionData... data)
                    {
                        Currency.updateJsons();
                        for(ConversionData datum : data)
                            datum.update();
                        return null;
                    }
                    @Override protected void onPostExecute(Void result)
                    {
                        notifyDataSetChanged();
                    }
                }.execute(data.toArray(new ConversionData[0]));
                handler.postDelayed(this, updateDelay);
            }
        }, 0);
    }
    
    @NonNull
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent)
    {
        View dataView = convertView == null ? inflater.inflate(R.layout.currency, parent, false) : convertView ;
        TextView textCurrencyFrom = (TextView)dataView.findViewById(R.id.textViewCurrencyFrom);
        TextView textValue = (TextView)dataView.findViewById(R.id.textViewValue);
        TextView textChange = (TextView)dataView.findViewById(R.id.textViewChange);
        ImageView iconFrom = (ImageView)dataView.findViewById(R.id.icon_from);
        TextView emojiFrom = (TextView)dataView.findViewById(R.id.emoji_from);
        
        ConversionData datum = data.get(position);
        textCurrencyFrom.setText(datum.currencyFrom.name);
        textValue.setText(String.format(Locale.getDefault(), "%s %.3f %s",
                                        datum.currencyTo.symbol, datum.getValue(), datum.currencyTo.code));
        iconFrom.setImageResource(datum.currencyFrom.icon);
        emojiFrom.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
        emojiFrom.setText(datum.currencyFrom.emoji);
        
        double change = datum.getChange();
        textChange.setText(String.format(Locale.getDefault(), "%+.4f%%", change));
        if(change < 0)
            textChange.setTextColor(Color.rgb((int)Math.round(change * -30), 0, 0));
        else
            textChange.setTextColor(Color.rgb(0, (int)Math.round(change * 30), 0));
        
        return dataView;
    }
}
