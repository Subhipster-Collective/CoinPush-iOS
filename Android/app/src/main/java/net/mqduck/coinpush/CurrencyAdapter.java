package net.mqduck.coinpush;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mqduck on 7/4/17.
 */

public class CurrencyAdapter extends ArrayAdapter<CurrencyData>
{
    private final Context context;
    private final List<CurrencyData> data;
    private LayoutInflater inflater;
    
    public CurrencyAdapter(Context context, List<CurrencyData> data)
    {
        super(context, -1, data);
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
    }
    
    public View getView(final int position, final View convertView, final ViewGroup parent)
    {
        View dataView = inflater.inflate(R.layout.currency_layout, parent, false);
        
        TextView textCurrency = (TextView)dataView.findViewById(R.id.textViewCurrency);
        TextView textValue = (TextView)dataView.findViewById(R.id.textViewValue);
        TextView textChange = (TextView)dataView.findViewById(R.id.textViewChange);
        
        CurrencyData datum = data.get(position);
        textCurrency.setText(datum.getCurrencyName());
        textValue.setText(String.format("%.3f %s", datum.getValue(), datum.getConversion()));
        
        double change = datum.getChange();
        textChange.setText(String.format("%+.4f%%", change));
        if(change < 0)
            textChange.setTextColor(Color.rgb((int)Math.round(255 + change * 30), 0, 0));
        else
            textChange.setTextColor(Color.rgb(0, (int)Math.round(change * 30), 0));
        
        return dataView;
    }
}
