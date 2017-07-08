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
    private int updateDelay = 60000;
    private static float emojiSize;
    
    ConversionAdapter(final Context context, final ConversionList conversions)
    {
        super(context, -1, conversions);
        //this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.conversions = conversions;
        emojiSize = (float)0.7 * context.getResources().getDrawable(R.mipmap.ic_eth).getIntrinsicHeight();
        
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override public void run()
            {
                new AsyncTask<Void, Void, Void>() {
                    @Override protected Void doInBackground(Void... voids)
                    {
                        Currency.updateJsons();
                        for(Conversion conversion : conversions)
                            conversion.update();
                        return null;
                    }
                    @Override protected void onPostExecute(Void result) { notifyDataSetChanged(); }
                }.execute();
                handler.postDelayed(this, updateDelay);
            }
        }, 0);
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
        textCurrencyFrom.setText(String.format(textCurrencyFrom.getTag().toString(),
                                               conversion.currencyFrom.name,
                                               conversion.currencyFrom.code));
        textValue.setText(String.format(textValue.getTag().toString(),
                                        conversion.currencyTo.symbol,
                                        conversion.getValue(),
                                        conversion.currencyTo.code));
        iconFrom.setImageResource(conversion.currencyFrom.icon);
        emojiFrom.setTextSize(TypedValue.COMPLEX_UNIT_PX, emojiSize);
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
