package com.etna.gsearch;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchList extends AppCompatActivity {

    TextView title;
    ListView listView;
    private List<Results> results = new ArrayList<Results>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        //SetTitle
        title = (TextView) findViewById(R.id.title);
        String text = getIntent().getStringExtra("search");
        text = text.substring(0, 1).toUpperCase() + text.substring(1);
        title.setText(text);

        try {
            getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayAdapter<Results> adapter = new MyListAdapter();
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    private void getResultList() throws Exception {
        System.out.println(this.getIntent().getStringExtra("search"));
        JSONArray jsonRes = Utils.googleTextSearch(this.getIntent().getStringExtra("search"));
        for (int i=0; i < jsonRes.length(); i++) {
            JSONObject adder = jsonRes.getJSONObject(i);
            double rating = -1;
            if (!adder.isNull("rating")) {
                rating = adder.getDouble("rating");
            }
            results.add(new Results(adder.getString("name"), adder.getString("formatted_address"), adder.getString("icon"), rating, adder.getString("place_id")));
        }
    }

    private class MyListAdapter extends ArrayAdapter<Results> {
        public MyListAdapter() {
            super(SearchList.this, R.layout.item_view, results);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            final Results currentResult = results.get(position);

            TextView placeName = (TextView) itemView.findViewById(R.id.rAuthor);
            TextView placeAddress = (TextView) itemView.findViewById(R.id.PlaceAdress);
            ImageView placeIcon = (ImageView) itemView.findViewById(R.id.rIm);

            placeName.setText(currentResult.getName());
            placeAddress.setText(currentResult.getAddress());
            try {
                placeIcon.setImageBitmap(Utils.drawableFromUrl(currentResult.getIcon()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Description.class);
                    intent.putExtra("placeId", currentResult.getPlaceId());
                    startActivity(intent);

                    overridePendingTransition(R.animator.animation3, R.animator.animation4);
                }
            });

            return itemView;
        }
    }
}
