package com.etna.gsearch;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Description extends AppCompatActivity {
    TextView desName;
    TextView desHorraires;
    TextView desAddress;
    TextView desOpen;
    TextView desPhone;
    ListView listView;
    ImageButton imageButton;
    ImageView photo;

    private List<Reviews> reviews = new ArrayList<Reviews>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        JSONObject jsonRes = null;
        try {
            jsonRes = Utils.googlePlaceId(this.getIntent().getStringExtra("placeId"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        desName = (TextView) findViewById(R.id.desName);
        desHorraires = (TextView) findViewById(R.id.desHorraires);
        desAddress = (TextView) findViewById(R.id.desAddress);
        desOpen = (TextView) findViewById(R.id.desOpen);
        desPhone = (TextView) findViewById(R.id.desPhone);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        photo = (ImageView) findViewById(R.id.photo);

        try {
            actions(jsonRes);
            final String url = jsonRes.getString("url");
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = url;
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.putExtra(Intent.EXTRA_TEXT, message);

                    startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayAdapter<Reviews> adapter = new MyListAdapter();
        listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(adapter);
    }
    private void actions(JSONObject jsonRes) throws Exception {
        try {
            String imUrl = "";
            if (!jsonRes.isNull("photos")) {
                imUrl = jsonRes.getJSONArray("photos").getJSONObject(0).getString("photo_reference").isEmpty() ? "" : "https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photoreference=" + jsonRes.getJSONArray("photos").getJSONObject(0).getString("photo_reference") + "&key=AIzaSyAfnWszc9E_rG_61huhSTNX4_5EIu_JhQo";
            }
            String weekDay = "";
            if (!jsonRes.isNull("opening_hours")) {
                weekDay = jsonRes.getJSONObject("opening_hours").getJSONArray("weekday_text").join("\n").replace("\"", "")
                        .replace("Monday", "Lundi").replace("Tuesday", "Mardi").replace("Wednesday", "Mercredi")
                        .replace("Thursday", "Jeudi").replace("Friday", "Vendredi").replace("Saturday", "Samedi")
                        .replace("Sunday", "Dimanche");
            }
            desName.setText(jsonRes.getString("name"));
            desPhone.setText(jsonRes.getString("international_phone_number"));
            desHorraires.setText(weekDay);
            desAddress.setText(jsonRes.getString("formatted_address"));
            if (!imUrl.isEmpty()) {
                photo.setImageBitmap(Utils.drawableFromUrl(imUrl));
            }
            if (!jsonRes.isNull("opening_hours")) {
                if (jsonRes.getJSONObject("opening_hours").getBoolean("open_now"))
                    desOpen.setText("Ouvert maintenant !");
                else
                    desOpen.setText("Actuellement fermé !");
            }
            else
                desOpen.setText("");

            JSONArray rev = jsonRes.getJSONArray("reviews");
            for (int i=0; i < rev.length(); i++) {
                JSONObject adder = rev.getJSONObject(i);
                String photo = adder.isNull("photo_url") ?  "" : adder.getString("photo_url");
                reviews.add(new Reviews(adder.getString("author_name"), photo, adder.getString("text"), adder.getDouble("rating")));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class MyListAdapter extends ArrayAdapter<Reviews> {
        public MyListAdapter() {
            super(Description.this, R.layout.review_view, reviews);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.review_view, parent, false);
            }

            final Reviews currentResult = reviews.get(position);

            TextView rAuthor = (TextView) itemView.findViewById(R.id.rAuthor);
            TextView rComment = (TextView) itemView.findViewById(R.id.rComment);
            TextView rRating = (TextView) itemView.findViewById(R.id.rRating);
            ImageView rIm = (ImageView) itemView.findViewById(R.id.rIm);

            rAuthor.setText(currentResult.getAuthor());
            rComment.setText(currentResult.getText());
            rRating.setText(currentResult.getRating() + " / 5.0");
            if (!currentResult.getPhotoUrl().isEmpty()) {
                try {
                    rIm.setImageBitmap(Utils.drawableFromUrl(currentResult.getPhotoUrl()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return itemView;
        }
    }
}
