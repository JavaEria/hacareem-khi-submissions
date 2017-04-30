package com.example.bilal.careem2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    int RESULT_LOCATION_UPDATE = 3223;
    private GoogleMap mMap;
    private Location location=null;
    private Button[] Seggestion;
    private String[] NameofPlace = new String[3];
    private Double[] LatitudeofPlace = new Double[4],LongitudeofPlace = new Double[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Seggestion = new Button[3];
        Seggestion[0] = (Button) findViewById(R.id.firsttxt);
        Seggestion[1] = (Button) findViewById(R.id.secondtxt);
        Seggestion[2] = (Button) findViewById(R.id.thirdtxt);
    }
    public void onClick1(View v) {
        Seggestion[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "0 is clicked", Toast.LENGTH_SHORT).show();
                mMap.clear();
                mMap.addMarker(new MarkerOptions().title(NameofPlace[0]).position(new LatLng(LatitudeofPlace[0], LongitudeofPlace[0])));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(LatitudeofPlace[0], LongitudeofPlace[0]), 20));
                PolylineOptions polylineOptions = new PolylineOptions()
                        .add(new LatLng(LatitudeofPlace[0], LongitudeofPlace[0]))
                        .add(new LatLng(LatitudeofPlace[3], LongitudeofPlace[3]))
                        .color(Color.RED)
                        .width(5);

                Polyline polyline = mMap.addPolyline(polylineOptions);
            }
        });
    }
    public void onClick2(View v)

    {
        Seggestion[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "1 is clicked", Toast.LENGTH_SHORT).show();
                mMap.clear();
                mMap.addMarker(new MarkerOptions().title(NameofPlace[1]).position(new LatLng(LatitudeofPlace[1], LongitudeofPlace[1])));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(LatitudeofPlace[1], LongitudeofPlace[1]), 20));
                PolylineOptions polylineOptions = new PolylineOptions()
                        .add(new LatLng(LatitudeofPlace[1], LongitudeofPlace[1]))
                        .add(new LatLng(LatitudeofPlace[3], LongitudeofPlace[3]))
                        .color(Color.RED)
                        .width(5);

                Polyline polyline = mMap.addPolyline(polylineOptions);
            }
        });
    }
    public void onClick3(View v)
    {
        Seggestion[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"2 is clicked",Toast.LENGTH_SHORT).show();
                mMap.clear();
                mMap.addMarker(new MarkerOptions().title(NameofPlace[2]).position(new LatLng(LatitudeofPlace[2],LongitudeofPlace[2])));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(LatitudeofPlace[2],LongitudeofPlace[2]),20));
                PolylineOptions polylineOptions = new PolylineOptions()
                        .add(new LatLng(LatitudeofPlace[2], LongitudeofPlace[2]))
                        .add(new LatLng(LatitudeofPlace[3], LongitudeofPlace[3]))
                        .color(Color.RED)
                        .width(5);

                Polyline polyline = mMap.addPolyline(polylineOptions);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Home) {
            // Handle the camera action
        } else if (id == R.id.nav_rides) {

        } else if (id == R.id.nav_wallet) {

        } else if (id == R.id.nav_free_Rides) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_ContactUs) {

        } else if (id == R.id.nav_help) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        turnOnLocation();
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mMap != null) {
            mMap.setMyLocationEnabled(true);
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            String locationProvider = LocationManager.NETWORK_PROVIDER;
            location = locationManager.getLastKnownLocation(locationProvider);
            if (location != null) {
                double latitude = location.getLatitude(), longitude = location.getLongitude();
                LatitudeofPlace[3]=latitude;LongitudeofPlace[3]=longitude;
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 17));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
//                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                Long tsLong = System.currentTimeMillis() / 1000;
                String geohashes = "";
                try {
                    String desResponse = new BackgroundGetDestination()
                            .execute("03458009561",
                                    tsLong.toString(),
                                    String.valueOf(latitude),
                                    String.valueOf(longitude))
                            .get();
                    JSONObject jsonObject = new JSONObject(desResponse);
                    geohashes += jsonObject.optString("geohash_3");
                    geohashes += ",";
                    geohashes += jsonObject.optString("geohash_2");
                    geohashes += ",";
                    geohashes += jsonObject.optString("geohash_1");
                    Log.d("billal ->", geohashes);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                onProceed(geohashes);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
            }
            else
            {
                Toast.makeText(this,"Location is null",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void onProceed(String geohashes) {
//        String GeohashResponse= new BackgroundGetGeohash(latitude,longitude).getGeohash();

        String[] totalstr = geohashes.split(",");
        String latlong="";int i=0;
        for (String str: totalstr) {
            latlong="";
            try {
                String latlng = new BackgroundGetLatLongFromGeohash()
                        .execute(str)
                        .get();
            JSONObject jsonObject = new JSONObject(latlng);
                latlong += jsonObject.optString("Lat");
                latlong += ",";
                latlong += jsonObject.optString("Lon");
//                Log.d("billal -> ",latlong);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            String[] latlngstr = latlong.split(",");

            Double deslatitude,deslongitude;
            try {
                deslatitude = new Double(latlngstr[0]);
                deslongitude = new Double(latlngstr[1]);
            } catch (NumberFormatException e) {
                deslatitude = 0.0;
                deslongitude = 0.0;
                // your default value
            }
            List<Address> addressList = null;
            Geocoder geocoder = new Geocoder(this);
                try
                {
                    LatitudeofPlace[i] = deslatitude;
                    LongitudeofPlace[i] = deslongitude;
                    addressList = geocoder.getFromLocation(deslatitude,deslongitude,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address address = addressList.get(0);
                Log.d("billal ->","Name : "+address.getAddressLine(0));
                NameofPlace[i] = address.getAddressLine(0);
                Seggestion[i].setText(address.getAddressLine(0));
                i++;
            }
        }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_LOCATION_UPDATE) {
            Log.e("Jozeb", "Result code: " + resultCode);
//              if (resultCode ==RESULT_OK){
//              }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void turnOnLocation() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(800);// Vibrate for 800 milliseco nds

            Toast.makeText(this, "Please turn on Location", Toast.LENGTH_SHORT).show();
            Text_To_Speech txt = new Text_To_Speech(this,"Please turn on Location");
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), RESULT_LOCATION_UPDATE);
        }
    }
}
