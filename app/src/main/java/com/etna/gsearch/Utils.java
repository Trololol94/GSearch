package com.etna.gsearch;

        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;
        import java.io.InputStream;
        import java.net.HttpURLConnection;
        import java.net.URL;

        import okhttp3.Call;
        import okhttp3.Callback;
        import okhttp3.Headers;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.Response;

/**
 * Created by Bastien Chevallier on 4/28/2016.
 */
public class Utils {
    public static Bitmap drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return x;
    }

    public static JSONArray googleTextSearch(String q) throws Exception {
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + q + "&key=AIzaSyAfnWszc9E_rG_61huhSTNX4_5EIu_JhQo&location=48.859200,2.341700&radius=5000&language=fr")
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }

        String jsonData = response.body().string();
        JSONObject Jobject = null;
        JSONArray Jarray = null;
        try {
            Jobject = new JSONObject(jsonData);
            Jarray = Jobject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return Jarray;
    }

    public static JSONObject googlePlaceId(String q) throws Exception {
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/place/details/json?placeid=" + q + "&key=AIzaSyAfnWszc9E_rG_61huhSTNX4_5EIu_JhQo&language=fr")
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }

        String jsonData = response.body().string();
        JSONObject Jobject = null;
        try {
            Jobject = new JSONObject(jsonData);
            Jobject = Jobject.getJSONObject("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(jsonData);
        return Jobject;
    }
}
