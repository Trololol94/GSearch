package com.etna.gsearch;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imRestaurant;
    ImageView imTatou;
    ImageView imSuper;
    EditText searchText;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imRestaurant = (ImageView) findViewById(R.id.imRestaurant);
        imTatou = (ImageView) findViewById(R.id.imTatou);
        imSuper = (ImageView) findViewById(R.id.imSuper);
        searchText = (EditText) findViewById(R.id.searchText);
        submit = (Button) findViewById(R.id.submit);

        imRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchList.class);
                intent.putExtra("search", "restaurant");
                startActivity(intent);

                overridePendingTransition(R.animator.animation3, R.animator.animation4);

            }
        });

        imTatou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchList.class);
                intent.putExtra("search", "tatoueur");
                startActivity(intent);

                overridePendingTransition(R.animator.animation3, R.animator.animation4);
            }
        });

        imSuper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchList.class);
                intent.putExtra("search", "coiffeur");
                startActivity(intent);

                overridePendingTransition(R.animator.animation3, R.animator.animation4);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchList.class);
                intent.putExtra("search", searchText.getText().toString());
                startActivity(intent);

                overridePendingTransition(R.animator.animation3, R.animator.animation4);
            }
        });
    }
}
