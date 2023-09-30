package com.example.weatherapptutorial;

import static com.example.weatherapptutorial.R.drawable.skiingsuggest;
import static com.example.weatherapptutorial.R.drawable.swimmingsuggest;
import static com.example.weatherapptutorial.R.drawable.walksuggest;

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
//class for giving activity suggestions
public class EventSuggestion extends AppCompatActivity {

    //declare variables
    BottomNavigationView bnView;
    TextView Temperature, Suggestion, Condition;
    ImageView ActivitySuggest;

    String[] data = new String[5];//declare and set length of the array for storing weather data needed to make suggestions
    String condition,temp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_suggestion);

        //connect each variable with their corresponding ids
        Temperature = findViewById(R.id.temperature);
        Suggestion = findViewById(R.id.suggestion);
        Condition = findViewById(R.id.condition);
        ActivitySuggest = findViewById(R.id.activitySuggest);

        bnView = findViewById(R.id.bnView);
        bnView.setSelectedItemId(R.id.activity);
        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            //for switching pages using bottom navigation view
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.clothing:
                        Intent sendDataC = new Intent(EventSuggestion.this, ClothingSuggestion.class);
                        sendDataC.putExtra("dataC", data);
                        startActivity(sendDataC);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        Intent sendDataH = new Intent(EventSuggestion.this, MainActivity.class);
                        sendDataH.putExtra("dataH", data);
                        startActivity(sendDataH);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.transportation:
                        Intent sendDataT = new Intent(EventSuggestion.this, TransportationSuggestion.class);
                        sendDataT.putExtra("dataT", data);
                        startActivity(sendDataT);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.activity:
                        return true;

                    case R.id.locations:
                        Intent sendDataL = new Intent(EventSuggestion.this, Locations.class);
                        sendDataL.putExtra("dataL", data);
                        startActivity(sendDataL);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;//when no item is selected, nothing happens
            }
        });
    }

    @Override
    protected void onResume() {//receive weather data for a new city from MainActivity
        super.onResume();

        Intent mIntent = getIntent();
        data = mIntent.getStringArrayExtra("dataA");

        if (data != null) {
            //Use TextViews to display information stored in passed data array
            Temperature.setText(data[0]);
            Condition.setText(data[1]);
            suggest(data);
        }
    }

    //method used to provide suggestions based on different temperatures and conditions
        @SuppressLint("SetTextI18n")
    private void suggest(String[] dataArray) {
        temp = dataArray[2];
        condition = dataArray[1];
        int temperature = Integer.parseInt(temp);//parse temp from string to integer

        if (temperature >= 30 && condition=="Sunny") {
            Suggestion.setText("Swimming"+ "\nHiking");
            ActivitySuggest.setImageResource(swimmingsuggest);
        }
        else if (temperature >= 20 && 30 > temperature && (condition.equals("Clouds") || condition.equals("Sunny")) ){
            Suggestion.setText("Hiking"+ "\nFishing"+"\nKayaking");
            ActivitySuggest.setImageResource(walksuggest);
        }
        else if(temperature >=10 && 20 > temperature){
            Suggestion.setText("Golf"+ "\nMuseum"+"\nBowling");
        }
        else if(temperature>=0 && 10 > temperature){
            Suggestion.setText("Skating"+ "\nIndoor sports"+"\nBowling");
        }
        else if(temperature>=-10 && 0 > temperature){
            Suggestion.setText("Skiing"+ "\nIce fishing"+ "\nSkating"+ "\nWinter Camping"+ "\nSledding");
            ActivitySuggest.setImageResource(skiingsuggest);
        }
        else {
            Suggestion.setText(condition);
        }
    }
}

