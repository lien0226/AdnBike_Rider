package com.arife.adnbikerider.mvp.v;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.arife.adnbikerider.R;
import com.arife.adnbikerider.Utilitarios.Charge;
import com.arife.adnbikerider.mvc.c.Rest;
import com.arife.adnbikerider.mvc.m.RestModel;
import com.arife.adnbikerider.mvc.m.RouteModel;
import com.arife.adnbikerider.mvc.m.UbicacionModel;
import com.arife.adnbikerider.mvp.GETROUTEPERSON.InteractorRoutePerson;
import com.arife.adnbikerider.mvp.GETROUTEPERSON.PresenterRoutePerson;
import com.arife.adnbikerider.mvp.GETROUTEPERSON.RoutePersonPresenter;
import com.arife.adnbikerider.mvp.GETROUTEPERSON.RoutePersonView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class ViewRecord extends FragmentActivity implements OnMapReadyCallback , RoutePersonView {

    private GoogleMap mMap;
    //cual es el adaptador?
    private RouteModel routeModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        Bundle bundle = getIntent().getExtras();

        if (bundle!=null){
            this.routeModel = (RouteModel) bundle.getSerializable("routeModel");

        }

        this.routePersonPresenter = new PresenterRoutePerson( new InteractorRoutePerson() , this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    private RoutePersonPresenter routePersonPresenter;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);

        this.routePersonPresenter.OnRoutePerson(this.OnRestModel());
    }

    @Override
    public void OnSucessRoutePerson(List<UbicacionModel> ubicacionModels) {
        this.PaintZoom(ubicacionModels);
        //Prueba :V go
        // proyecta pe como wa a ver
    }

    @Override
    public void OnError(String Error) {
        Toast.makeText(this, Error, Toast.LENGTH_SHORT).show();
    }

    private RestModel OnRestModel(){
        RestModel restModel = new RestModel();
        restModel.setContext(this);
        restModel.setLink(Charge.getInstance().genGetRoutePerson(this.routeModel));
        return  restModel;
    }

    private void PaintZoom (List<UbicacionModel> points){
        //:v asi como el de quality products :V
        // es que no esta recorriendo
        // no veo nada chamo
        for (int i = 0 ; i < points.size()-1 ; i++){
            this.mMap.addPolyline(new PolylineOptions().add(points.get(i).getUbicacion(),points.get(i+1).getUbicacion())).setColor(Color.BLUE);
            //Log.e("TAG",i+" " + points.get(i).getUbicacion());
        }
        mMap.addMarker(new MarkerOptions().position(points.get(0).getUbicacion()).title("Acá inició el chamo"));
        //pera
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(points.get(0).getUbicacion(), 16));

    }
}