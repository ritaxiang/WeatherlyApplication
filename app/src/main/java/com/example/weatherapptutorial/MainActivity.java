package com.example.weatherapptutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
//homepage class
public class MainActivity extends AppCompatActivity {

    //Initialize variables with information to connect with API
    final String APP_ID = "dab3af44de7d24ae7ff86549334e45bd";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";


    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;


    String Location_Provider = LocationManager.GPS_PROVIDER;

    //Declare TextViews, ImageViews, button, and layout
    TextView NameofCity, weatherState, Temperature;
    ImageView mweatherIcon;
    Button mSavedLocation;
    RelativeLayout mCityFinder;



    LocationManager mLocationManager;
    LocationListener mLocationListner;

    BottomNavigationView bnView;

    String saveCity;

    //Initialize arrays to help store and pass information between classes
    //sendData stores information and passes it to other activities
    String [] sendData= new String [5];
    //data stores received information that is passed to this class
    String [] data = new String[5];


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize TextViews and buttons using resource identifier
        weatherState = findViewById(R.id.weatherCondition);
        Temperature = findViewById(R.id.temperature);
        mweatherIcon = findViewById(R.id.weatherIcon);
        mCityFinder = findViewById(R.id.cityFinder);
        NameofCity = findViewById(R.id.cityName);
        mSavedLocation = findViewById(R.id.saveButton);

        //Setup navigation bar
        bnView = findViewById(R.id.bnView);
        bnView.setSelectedItemId(R.id.home);

        //The following switch statements allow the user to switch between pages using the navigation bar
        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.clothing:
                        //Intent is declared to switch between this class to clothingSuggestion class
                        Intent sendDataC = new Intent(MainActivity.this, ClothingSuggestion.class);
                        //Intent allows this class to send information to another activity (in this case, clothing page) using sendData array
                        sendDataC.putExtra("dataC", sendData);
                        startActivity(sendDataC);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        return true;

                    case R.id.transportation:
                        Intent sendDataT = new Intent(MainActivity.this, TransportationSuggestion.class);
                        sendDataT.putExtra("dataT", sendData);
                        startActivity(sendDataT);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.activity:
                        Intent sendDataA = new Intent(MainActivity.this, EventSuggestion.class);
                        sendDataA.putExtra("dataA", sendData);
                        startActivity(sendDataA);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.locations:
                        Intent sendDataL = new Intent(MainActivity.this, Locations.class);
                        sendDataL.putExtra("dataL", sendData);
                        startActivity(sendDataL);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        //If user taps on search icon:
        mCityFinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent takes the user from Home Page to search bar/cityFinder class
                Intent intent = new Intent(MainActivity.this, CityFinder.class);
                startActivity(intent);
            }
        });

        //If user taps on star icon:
        mSavedLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResume();
                //If the program reads a location/city on the screen:
                if (saveCity != null) {
                    //Intent is used to go to Saved Locations page and sends city name with it
                    Intent intent = new Intent(MainActivity.this, Locations.class);
                    intent.putExtra("saveCity",saveCity);
                    startActivity(intent);
                }
            }
        });
        }

    //The following code runs throughout the activity's lifecycle
    @Override
    protected void onResume() {
        super.onResume();
        Intent mIntent=getIntent(); //Use getIntent method to save information passed from other classes
        //If user searches up city, location name is passed from cityFinder class and saved in city:
        String city= mIntent.getStringExtra("City");
        //If user clicks on city from locations class (saved cities), info is passed and saved in city2:
        String city2 = mIntent.getStringExtra("city_name");
        //If user switches pages, share and save info from previous page to this page in data array:
        data =mIntent.getStringArrayExtra("dataH");
        if(city!=null) { //If user searched up a city:
            getWeatherForNewCity(city); //Find city's weather using name in parameters
        }
        else if(city2!=null){ //If user clicked on saved city:
            getWeatherForNewCity(city2); //Find city's weather using name in parameters
        }
        else if(data!=null){ //This allows the previously displayed information to stay on the Home Page, even if user switches pages
            //Use TextViews and ImageView to display information stored in passed data array
            Temperature.setText(data[0]);
            weatherState.setText(data[1]);
            NameofCity.setText(data[3]);
            mweatherIcon.setImageResource(Integer.parseInt(data[4]));
            //Update sendData array, so that if user changes pages, same data is shared again
            sendData = data;
            //Initialize saveCity variable so save button can still work
            saveCity=data[3];
        }
        else { //If there is no input when app is opened:
            getWeatherForCurrentLocation(); //Use this method to find user's current location
        }


    }


    //method used to get weather data for new searched city
    private void getWeatherForNewCity(String city)
    {
        RequestParams params=new RequestParams();
        params.put("q",city);
        params.put("appid",APP_ID);
        gatherData(params);


    }



//method used to get weather data for user's current location
    private void getWeatherForCurrentLocation() {

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListner = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());

                RequestParams params =new RequestParams();
                params.put("lat" ,Latitude);
                params.put("lon",Longitude);
                params.put("appid",APP_ID);
                gatherData(params);




            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                //not able to get location
            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mLocationListner);

    }



    private  void gatherData(RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Toast.makeText(MainActivity.this,"Data Get Success",Toast.LENGTH_SHORT).show();

                WeatherData weatherD= WeatherData.fromJson(response);
                updateUI(weatherD);






               // super.onSuccess(statusCode, headers, response);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });



    }

    private void updateUI(WeatherData weather){//method used to update weather data for a city


        Temperature.setText(weather.getmTemperature());
        NameofCity.setText(weather.getMcity());
        weatherState.setText(weather.getmWeatherType());
        int resourceID=getResources().getIdentifier(weather.getMicon(),"drawable",getPackageName());
        mweatherIcon.setImageResource(resourceID);
        saveCity = weather.getMcity();

        sendData[0] = weather.getmTemperature();
        sendData[1]= weather.getmWeatherType();
        sendData[2] = weather.getNum();
        sendData[3] = weather.getMcity();
        sendData[4] = resourceID + "";




    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mLocationManager!=null)
        {
            mLocationManager.removeUpdates(mLocationListner);
        }
    }







}