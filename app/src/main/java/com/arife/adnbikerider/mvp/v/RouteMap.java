package com.arife.adnbikerider.mvp.v;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import com.arife.adnbikerider.R;
import com.arife.adnbikerider.Utilitarios.Gps.UtilsGps;
import com.arife.adnbikerider.Utilitarios.SqlLite.ConnectionSqlLite;
import com.arife.adnbikerider.Utilitarios.SqlLite.Constantes;
import com.arife.adnbikerider.mvc.m.RouteModel;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class RouteMap extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private ConnectionSqlLite connectionSqlLite;
    private RouteModel routeModel;
    private FloatingActionButton InitRoute;
    private TextView DistanceRecord, DistanceFormat, VelRecord, VelFormat;
    private Chronometer chronometer;
    private ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_map);
        this.InitRoute = findViewById(R.id.InitRoute);
        this.DistanceRecord = findViewById(R.id.DistanceRecord);
        this.DistanceFormat = findViewById(R.id.DistanceFormat);
        this.VelRecord = findViewById(R.id.VelRecord);
        this.VelFormat = findViewById(R.id.VelFormat);
        this.imageButton = findViewById(R.id.btnRecord);
        this.chronometer = findViewById(R.id.IdChronometer);
        this.InitRoute.setOnClickListener(this);
        this.imageButton.setOnClickListener(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
            return;
        }
        Bundle bundle = getIntent().getExtras();

        if (bundle!=null){
            routeModel = (RouteModel) bundle.getSerializable("RouteModel");
        }
        this.connectionSqlLite = new ConnectionSqlLite(this, "DB_ADNRIDE", null, 1);
        //this.connectionSqlLite.ExectSql(Constantes.CREATE_TABLE);
    }

    /**
     *
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private void Permitions(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION },
                    1);
            return;
        }
        this.mMap.setMyLocationEnabled(true);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        Permitions();
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    private List<LatLng> pointsL;
    //private List<LatLng> distance;
    private List<Long> datetime;
    private void InitRoute(){
        pointsL = new ArrayList<>();
       // distance = new ArrayList<>();
        datetime = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    try {
                        Thread.sleep(5000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Location location = mMap.getMyLocation();
                                if (location!=null){

                                    RouteMap.this.pointsL.add(new LatLng(location.getLatitude(),location.getLongitude()));
                                    //RouteMap.this.distance.add(new LatLng(location.getLatitude(), location.getLongitude()));
                                    RouteMap.this.datetime.add(UtilsGps.DateToMillisecons());

                                    ContentValues values = new ContentValues();
                                    values.put(Constantes.CAMPO_ID,0);
                                    values.put(Constantes.CAMPO_ID_RUTA,routeModel.getId());
                                    values.put(Constantes.CAMPO_LONGITUD, location.getLongitude());
                                    values.put(Constantes.CAMPO_LATITUD,location.getLatitude());
                                    SQLiteDatabase db = RouteMap.this.connectionSqlLite.getWritableDatabase();
                                    Long a = db.insert(Constantes.TABLE_POINTS,Constantes.CAMPO_ID,values);
                                    RouteMap.this.connectionSqlLite.close();

                                   // Toast.makeText(RouteMap.this, "Id "+a+" | "+mMap.getMyLocation().distanceTo(location), Toast.LENGTH_SHORT).show();

                                    if (RouteMap.this.pointsL.size()>1){
                                        RouteMap.this.paintPoints(RouteMap.this.pointsL, RouteMap.this.datetime);

                                    }

                                    db = RouteMap.this.connectionSqlLite.getReadableDatabase();

                                    Cursor cursor = db.rawQuery("SELECT * FROM "+Constantes.TABLE_POINTS,null);
                                    while (cursor.moveToNext()){
                                       // Log.e("Cursor " ,cursor.getInt(1) +" | "+ cursor.getDouble(2)+" - "+ cursor.getDouble(3)+" | "+ UtilsGps.DateToMillisecons());
                                    }
                                }
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    float m = 0;
    //private static DecimalFormat df = new DecimalFormat("0.00000");

    int tiempo;
    double velocidad;


    private void paintPoints(List<LatLng> points, List<Long> time){
        this.mMap.addPolyline(new PolylineOptions().add(points.get(points.size()-2),points.get(points.size()-1))).setColor(Color.BLUE);
        Location a = new Location("dummyprovider");
        a.setLatitude((points.get(points.size()-2).latitude));
        a.setLongitude((points.get(points.size()-2).longitude));
        Location b = new Location("dummyprovider");
        b.setLatitude((points.get(points.size()-1).latitude));
        b.setLongitude((points.get(points.size()-1).longitude));

        tiempo = Integer.parseInt(String.valueOf((time.get(time.size()-1))-(time.get(time.size()-2))));
        velocidad = (Double.parseDouble(UtilsGps.DistanceMyVehicle(a.distanceTo(b)).split(" ")[2])/(tiempo));

        //Log.e("m " ,UtilsGps.DistanceMyVehicle(( m += a.distanceTo(b))));
        //Log.e("time: ", tiempo+" | vel: "+velocidad+" | dist: "+(UtilsGps.DistanceMyVehicle(a.distanceTo(b)).split(" ")[2]));
        //Toasty.success(this, UtilsGps.DistanceMyVehicle(( m += a.distanceTo(b)))).show();

        this.DistanceRecord.setText(UtilsGps.DistanceMyVehicle(( m += a.distanceTo(b))).split(" ")[2]);
        this.DistanceFormat.setText(UtilsGps.DistanceMyVehicle(( m += a.distanceTo(b))).split(" ")[3]);
        this.VelRecord.setText(String.valueOf(velocidad));
        this.VelFormat.setText("Km/h");

    }


    private float lasPosition(List<LatLng> distance){
        Location locationA = new Location("Punta A");
        locationA.setLatitude(distance.get(distance.size()-2).latitude);
        locationA.setLongitude(distance.get(distance.size()-2).longitude);
        Location locationB = new Location("Punto B");
        locationB.setLatitude(distance.get(distance.size()-1).latitude);
        locationB.setLongitude(distance.get(distance.size()-1).longitude);
        // = locationA.distanceTo(locationB);
        float distanc = mMap.getMyLocation().distanceTo(locationA);
        return distanc;
        //Log.e("ditancia: ",String.valueOf(distanc));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRecord :
                this.InitRoute();
                this.startChronometer();
                break;
        }
    }

    private void startChronometer(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }
}