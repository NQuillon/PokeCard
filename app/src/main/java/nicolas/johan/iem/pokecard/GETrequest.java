package nicolas.johan.iem.pokecard;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Johan on 07/11/2017.
 */

public class GETrequest extends AsyncTask<Object, String, String>

        //(final String route, final JSONObject jsonParam)
{
    protected String doInBackground(Object... data) {
        String line="";
        try {
            URL url = new URL("http://192.168.43.200:3000/"+data[0]); //192.168.0.17
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStream inputStream = conn.getInputStream();
            if (inputStream == null) {
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuffer buffer=new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
                buffer.append("\n");
            }
            line=buffer.toString();

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return line;
    }

    protected void onPostExecute(String result) {
        System.out.println("RETOUR DE L'API: "+result);
        super.onPostExecute(result);
    }
}
