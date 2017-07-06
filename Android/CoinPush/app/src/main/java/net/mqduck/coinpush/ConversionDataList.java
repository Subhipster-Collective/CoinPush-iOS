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

import java.util.ArrayList;

/**
 * Created by mqduck on 7/6/17.
 */

class ConversionDataList extends ArrayList<ConversionData>
{
    public boolean add(ConversionData datum)
    {
        if(super.add(datum))
        {
            datum.currencyTo.addConversion(datum.currencyFrom);
            return true;
        }
        return false;
    }
    
    public ConversionData remove(int index)
    {
        ConversionData datum = super.remove(index);
        datum.currencyTo.removeConversion(datum.currencyFrom);
        return datum;
    }
    
    public boolean remove(Object datum)
    {
        if(super.remove(datum))
        {
            ((ConversionData)datum).currencyTo.removeConversion(((ConversionData)datum).currencyFrom);
            return true;
        }
        return false;
    }
    
    public boolean removeAll() // For now, don't call this
    {
        return false;
    }
}
