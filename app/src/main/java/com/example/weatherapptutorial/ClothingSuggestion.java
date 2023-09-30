package com.example.weatherapptutorial;

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

//class for giving clothing suggestions
public class ClothingSuggestion extends AppCompatActivity {

    //declare variables
    TextView Temperature, Suggestion, Condition, AccessoriesSuggestion;
    ImageView SuggestionPic, AccessoriesPic;
    BottomNavigationView bnView;

    //declare and set length of the array for storing weather data needed to make suggestions
   String [] data = new String[5];



    @Override
    protected void onCreate(Bundle savedInstanceState) {//called when the page first opens up
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_suggestion);

        //connect each variable with their corresponding ids
        Temperature = findViewById(R.id.temperature);
        Suggestion = findViewById(R.id.suggestion);
        Condition = findViewById(R.id.condition);
        SuggestionPic = findViewById(R.id.suggestionPic);
        AccessoriesSuggestion = findViewById(R.id.accessoriesSuggestion);
        AccessoriesPic = findViewById(R.id.accessoriesPic);


        bnView = findViewById(R.id.bnView);
        bnView.setSelectedItemId(R.id.clothing);
        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            //for switching pages using bottom navigation view
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.clothing:
                        return true;

                    case R.id.home:
                        Intent sendDataH = new Intent(ClothingSuggestion.this, MainActivity.class);
                        sendDataH.putExtra("dataH", data);
                        startActivity(sendDataH);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.transportation:
                        Intent sendDataT = new Intent(ClothingSuggestion.this, TransportationSuggestion.class);
                        sendDataT.putExtra("dataT", data);
                        startActivity(sendDataT);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.activity:
                        Intent sendDataA = new Intent(ClothingSuggestion.this, EventSuggestion.class);
                        sendDataA.putExtra("dataA", data);
                        startActivity(sendDataA);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.locations:
                        Intent sendDataL = new Intent(ClothingSuggestion.this, Locations.class);
                        sendDataL.putExtra("dataL", data);
                        startActivity(sendDataL);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;//when no item is selected, nothing happens
            }
        });
    }

    @Override
    protected void onResume() {//receive weather data for a new city
        super.onResume();

        Intent mIntent=getIntent();
        data =mIntent.getStringArrayExtra("dataC");

        if(data!=null){
            //Use TextViews to display information stored in passed data array
            Temperature.setText(data[0]);
            Condition.setText(data[1]);
            suggest(data);
        }
}

    //method used to provide suggestions based on different temperatures and conditions
   @SuppressLint("SetTextI18n")
    private void suggest(String[] dataArray){
       String temp = dataArray[2];
       String condition = dataArray[1];
       int resource;
       int resource1;
       int temperature = Integer.parseInt(temp);

       //TEMP SUGGESTIONS
       if(temperature >= 30){
           Suggestion.setText("T-Shirt" +  "\nThin Shirt" +  "\nTank Top" + "\nShorts" + "\nFlowy Skirt" );
           resource = getResources().getIdentifier("thirtydegclothes", "drawable", this.getPackageName());
           SuggestionPic.setImageResource(resource);
       }
       else if(temperature>=20 && 30 > temperature ){
           Suggestion.setText("T-Shirt" +  "\nAthletic Pants" +  "\nPolo" + "\nShorts" + "\nSkirt" );
           resource = getResources().getIdentifier("twentydegclothes", "drawable", this.getPackageName());
           SuggestionPic.setImageResource(resource);
       }
       else if(temperature > 10 && temperature < 20){
           Suggestion.setText("Button-Up Shirt" +  "\nJeans" +  "\nShort Sleeve Shirt" + "\nBlouse" + "\nLight Joggers" );
           resource = getResources().getIdentifier("elevendegclothes", "drawable", this.getPackageName());
           SuggestionPic.setImageResource(resource);
       }
       else if(temperature > 0 && temperature <= 10){
           Suggestion.setText("Long Sleeve Shirt" +  "\nJeans" +  "\nZip-Up" + "\nLight Hoodie" + "\nJoggers" );
           resource = getResources().getIdentifier("onedegclothes", "drawable", this.getPackageName());
           SuggestionPic.setImageResource(resource);
       }

       else if(temperature <=0 && -10 <temperature) {
           Suggestion.setText("Wool Sweater" + "\nHoodie" + "\nLong Sleeve Shirt" + "\nJeans" + "\nSweatpants");
           resource = getResources().getIdentifier("zerodegclothes", "drawable", this.getPackageName());
           SuggestionPic.setImageResource(resource);
       }
       else{
           Suggestion.setText("Hoodie" + "\nSweatpants" + "\nWarm Tutle Neck" + "\nJeans" + "\nSweater");
           resource = getResources().getIdentifier("minustendegclothes", "drawable", this.getPackageName());
           SuggestionPic.setImageResource(resource);
       }

       //ACCESSORY SUGGESTIONS
       if(condition.equals("Snow")){
           AccessoriesSuggestion.setText("Snow Boots" + "\nWinter Jacket" + "\nSnow Pants" + "\nHat" + "\nScarf");
           resource1 = getResources().getIdentifier("hatsuggest", "drawable", this.getPackageName());
           AccessoriesPic.setImageResource(resource1);
       }
       else if(condition.equals("Rain") || condition.equals("Thunderstorm") || condition.equals("Drizzle")){
           AccessoriesSuggestion.setText("Rain Boots" + "\nRain Coat" + "\nUmbrella" + "\nRain Hat" + "\nRain Gloves");
           resource1 = getResources().getIdentifier("umbrellasuggest", "drawable", this.getPackageName());
           AccessoriesPic.setImageResource(resource1);
       }
       else if(condition.equals("Clear")){
           AccessoriesSuggestion.setText("Sunglasses" + "\nRunning Shoes" + "\nBaseball Hat" + "\nSneakers" + "\nSunscreen");
           resource1 = getResources().getIdentifier("capsuggest", "drawable", this.getPackageName());
           AccessoriesPic.setImageResource(resource1);
       }
       else if(condition.equals("Clouds")){
           AccessoriesSuggestion.setText("Boots" + "\nCap" + "\nTrainers" + "\nCardigan");
           resource1 = getResources().getIdentifier("bootsuggest", "drawable", this.getPackageName());
           AccessoriesPic.setImageResource(resource1);
       }



   }




}


















