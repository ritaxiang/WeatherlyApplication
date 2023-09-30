package com.example.weatherapptutorial;

import static com.example.weatherapptutorial.R.drawable.bikesuggest;
import static com.example.weatherapptutorial.R.drawable.drivesuggest;
import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

//class for giving transportation suggestions
public class TransportationSuggestion extends AppCompatActivity {

    //declare variables
    TextView Suggestion, Temperature, Condition,Alternatives, Answer;
    BottomNavigationView bnView;
    ImageView Icon,PageIcon;


    String temperature,condition;
    String [] data = new String[5];


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation_suggestion);

        //connect each variable with their corresponding ids

        Suggestion = findViewById(R.id.suggestion);
        Temperature = findViewById(R.id.temperature);
        Condition = findViewById(R.id.condition);
        Alternatives = findViewById(R.id.alternatives);
        Answer = findViewById(R.id.answer);

        //image views
        Icon = findViewById(R.id.transportationIcon);
        PageIcon = findViewById(R.id.pageIcon);

        bnView = findViewById(R.id.bnView);
        bnView.setSelectedItemId(R.id.transportation);
        ///for switching pages using bottom navigation view
        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.clothing:
                        Intent sendDataC = new Intent(TransportationSuggestion.this, ClothingSuggestion.class);
                        sendDataC.putExtra("dataC", data);
                        startActivity(sendDataC);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        Intent sendDataH = new Intent(TransportationSuggestion.this, MainActivity.class);
                        sendDataH.putExtra("dataH", data);
                        startActivity(sendDataH);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.transportation:
                        return true;

                    case R.id.activity:
                        Intent sendDataA = new Intent(TransportationSuggestion.this, EventSuggestion.class);
                        sendDataA.putExtra("dataA", data);
                        startActivity(sendDataA);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.locations:
                        Intent sendDataL = new Intent(TransportationSuggestion.this, Locations.class);
                        sendDataL.putExtra("dataL", data);
                        startActivity(sendDataL);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });



    }

    @Override
    protected void onResume() {//receive weather data for a new city
        super.onResume();

        Intent mIntent =getIntent();
        data = mIntent.getStringArrayExtra("dataT");
        //Use TextViews to display information stored in passed data array
        Temperature.setText(data[0]);
        Condition.setText(data[1]);
        temperature = data[2];
        condition = data[1];

        if(temperature!=null){//error trapping
            suggest();
        }

    }


    //method used to give suggestions based on the conditional statements
    private void suggest() {

        if (Integer.parseInt(temperature) <= 15 && Integer.parseInt(temperature) > 0) {
            Suggestion.setText("Bike");
            Alternatives.setText("Walk" + "\nSkateboard");
            Icon.setImageResource(bikesuggest);
            Answer.setText("YES");

        }

        if (Integer.parseInt(temperature) <= -5 || condition.equals("Snow")) {
            Suggestion.setText("Drive");
            Alternatives.setText("Bus" + "\nUber");
            Icon.setImageResource(drivesuggest);
            Answer.setText("YES");

        }

        if (Integer.parseInt(temperature) <= -15 || condition.equals("Snow")) {
            Suggestion.setText("Drive");
            Alternatives.setText("Bus" + "\nUber");
            Icon.setImageResource(drivesuggest);
            Answer.setText("NO");

        }

        if(condition.equals("Thunderstorm")|| condition.equals("Shower") || condition.equals("Rain")){
            Answer.setText("NO");
        }

    }


}