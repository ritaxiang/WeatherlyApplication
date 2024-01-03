/*
Description: The RecyclerViewAdapter class is used to help two unrelated classes work together.
In this case, it grabs the data from locations class and use that data to create a recycler view containing the saved cities.
 */
package com.example.weatherly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CityFinder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_finder);

        //Declare EditText and ImageView
        final EditText editText=findViewById(R.id.searchCity);
        ImageView backButton=findViewById(R.id.backButton);

        //the click listener allows user to go back to main page when click on the button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //the action listener allows user to see the weather of the city they put in immediately
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String newCity= editText.getText().toString();//store the city user enters to a new variable called newCity

                Intent intent=new Intent(CityFinder.this,MainActivity.class);
                intent.putExtra("City",newCity);
                startActivity(intent);//starts main page


                return false;
            }
        });

    }
}