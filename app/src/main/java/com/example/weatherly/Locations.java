package com.example.weatherly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class Locations extends AppCompatActivity implements CN_RecyclerViewAdapter.RecyclerViewInterface {

    BottomNavigationView bnView;
    String [] data = new String[5]; //declare and initialize array length
    ArrayList<SavedLocationsModel> savedLocations = new ArrayList<>(); //array list fro storing saved locations
    CN_RecyclerViewAdapter adapter;//adpater object
    int[] locationImages = {R.drawable.ic_baseline_location_on_24,R.drawable.ic_baseline_location_on_24, R.drawable.ic_baseline_location_on_24 };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        RecyclerView recyclerView = findViewById(R.id.mRecyclerview);
        
        setUpSavedLocations();//add default saved locations to the recycler view

        adapter = new CN_RecyclerViewAdapter(this,savedLocations, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bnView = findViewById(R.id.bnView);
        bnView.setSelectedItemId(R.id.locations);
        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            //click listener for bottom navigation bar
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.clothing:
                        Intent sendDataC = new Intent(Locations.this, ClothingSuggestion.class);
                        sendDataC.putExtra("dataC", data);
                        startActivity(sendDataC);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        Intent sendDataH = new Intent(Locations.this, MainActivity.class);
                        sendDataH.putExtra("dataH", data);
                        startActivity(sendDataH);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.transportation:
                        Intent sendDataT = new Intent(Locations.this, TransportationSuggestion.class);
                        sendDataT.putExtra("dataT", data);
                        startActivity(sendDataT);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.activity:
                        Intent sendDataA = new Intent(Locations.this, EventSuggestion.class);
                        sendDataA.putExtra("dataA", data);
                        startActivity(sendDataA);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.locations:

                        return true;
                }
                return false;
            }
        });


    }

    @Override
    protected void onResume(){//runs when user clicks on the page after the first time
        super.onResume();
        addItem();

        Intent mIntent=getIntent();
        data =mIntent.getStringArrayExtra("dataL");
    }


    private void setUpSavedLocations(){//method used to set-up the three initial locations
        String[] savedLocationNames = getResources().getStringArray(R.array.city_names);

        for(int i = 0; i<savedLocationNames.length; i++){//ues each item in the array to create a recycler view
            savedLocations.add(new SavedLocationsModel(savedLocationNames[i],locationImages[i]));
        }
    }

    public void addItem(){//method used to add new saved locations when star button is clicked in MainActivity
        Intent mIntent=getIntent();
        String city3= mIntent.getStringExtra("saveCity");
        int image = R.drawable.ic_baseline_location_on_24;


        if(city3!=null) {//error trapping
            savedLocations.add(new SavedLocationsModel(city3, image));
            adapter.notifyDataSetChanged();

        }
    }

    //this method allows user to see the weather of the clicked saved city
    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("city_name",savedLocations.get(position).getSavedCity());
        startActivity(intent);


    }

    //functions designed to remove a saved city
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            savedLocations.remove(viewHolder.getAdapterPosition());//removes the saved city from the list
            adapter.notifyDataSetChanged();

        }
    };


}