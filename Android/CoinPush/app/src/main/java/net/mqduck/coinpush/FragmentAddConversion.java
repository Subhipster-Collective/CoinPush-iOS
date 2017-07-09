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

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by mqduck on 7/8/17.
 */

public class FragmentAddConversion extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_conversion, null);
        
        final CurrencyAdapter adapter = new CurrencyAdapter(getActivity(), new ArrayList<>(Currency.currencies.values()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinnerFrom = (Spinner)view.findViewById(R.id.spinner_currency_from);
        final Spinner spinnerTo = (Spinner)view.findViewById(R.id.spinner_currency_to);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);
        
        builder.setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which)
                    {
                        Currency currencyFrom = (Currency)spinnerFrom.getSelectedItem();
                        Currency currencyTo = (Currency)spinnerFrom.getSelectedItem();
                        ActivityMain.conversions.add(new Conversion((Currency)spinnerFrom.getSelectedItem(),
                                                                    (Currency)spinnerTo.getSelectedItem()));
                        //ActivityMain.conversionAdapter.notifyDataSetChanged();
                        ActivityMain.conversionAdapter.updateData();
                    }
                })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   @Override public void onClick(DialogInterface dialog, int which)
                   {
                       
                   }
               });
        
        return builder.create();
    }
}
