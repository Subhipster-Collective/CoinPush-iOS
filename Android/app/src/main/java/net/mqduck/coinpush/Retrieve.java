package net.mqduck.coinpush;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by mqduck on 7/4/17.
 */

class RetrieveJson extends AsyncTask<String, Void, JSONObject>
{
    @Override
    protected JSONObject doInBackground(String... urls)
    {
        try
        {
            InputStream is = null;
            is = new URL(urls[0]).openStream();
            BufferedReader
                    rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while((cp = rd.read()) != -1)
                sb.append((char)cp);
            return (new JSONObject(sb.toString())).getJSONObject("RAW");
        }
        catch(IOException | JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
