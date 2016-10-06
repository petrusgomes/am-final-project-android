package br.com.notifycar.controller.mapa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import br.com.notifycar.R;
import br.com.notifycar.helper.CamposHelper;
import br.com.notifycar.interfacetask.AsyncResponseLoc;
import br.com.notifycar.repository.api.ListaUltimaLocalizacaoTask;
import br.com.notifycar.service.MyCurrencyLocation;


public class MapsViewActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener, AsyncResponseLoc {

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationManager locationManager;
    Circle circle;
    Button btnLista;
    Polyline rectLine;
    PolylineOptions basepoly;
    final ArrayList<String> nomes = new ArrayList<>();

    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location = null; // location
//    double latitude; // latitude
//    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute


    Marker car;
    private Button btnSafe;
    private String url;
    private CamposHelper helper;
    private TextView txtVoltar;
    private ListaUltimaLocalizacaoTask taskLoc;
    private String veiculoId;
    private String latitude;
    private String longitude;
    private double latitudeAux;
    private double longitudeAux;
    private Location locationcar;
    private TextView txtRelogioLoc;
    int count = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapsveiculo);

        btnSafe = (Button) findViewById(R.id.btnSafe);
        btnSafe.setOnClickListener(this);

        txtVoltar = (TextView) findViewById(R.id.ic_voltar);
        txtVoltar.setOnClickListener(this);

        txtRelogioLoc = (TextView) findViewById(R.id.txtRelogioLoc);

        Intent it = getIntent();
        Bundle urlRemote = it.getExtras();
        url = urlRemote.getString("urlRemoteControl");
        veiculoId = urlRemote.getString("idVeiculo");

        populaLatlongCar();
        pegarLocalizacaoAtual();


        relogioEventoLoc();


        helper = new CamposHelper();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        } else {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                showGPSDisabledAlertToUser();
            }
        }


        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);


    }

    private void doSomethingRepeatedly() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {

                try {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            count = 0;

                            Log.i("REPEATELOC", String.valueOf(count));
                            taskLoc = new ListaUltimaLocalizacaoTask(MapsViewActivity.this, veiculoId);

                            taskLoc.delegate = MapsViewActivity.this;

                            taskLoc.execute();

                        }
                    });





                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }, 0, 6000);
    }


    public void populaLatlongCar() {
        //JSONObject listLoc = null;
        try {
//            listLoc = new JSONObject(result);
//            latitude = listLoc.getString("latitude");
//            longitude = listLoc.getString("longitude");

//            locationatual.getLatitude();
//            locationatual.getLongitude();

            double latcar = -23.52766;
            double loncar = -46.4658205;
            locationcar = new Location("");
            locationcar.setLatitude(latcar);
            locationcar.setLongitude(loncar);
//
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void pegarLocalizacaoAtual(){
        MyCurrencyLocation gps = new MyCurrencyLocation(this);

        // check if GPS location can get Location
        if (gps.canGetLocation()) {

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                Log.d("Your Location", "latitude:" + gps.getLatitude()
                        + ", longitude: " + gps.getLongitude());

                longitudeAux = gps.getLongitude();
                latitudeAux = gps.getLatitude();
            }
        }
    }

            private void showGPSDisabledAlertToUser() {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("O GPS está desativado, deseja ativalo ?")
                    .setCancelable(false)
                    .setPositiveButton("Configuraçōes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(callGPSSettingIntent);

                            mapFrag.getMapAsync(MapsViewActivity.this);
                        }
                    });
            alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }

        @Override
        public void onPause() {
            super.onPause();

            //stop location updates when Activity is no longer active
            if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mGoogleMap = googleMap;
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


            //Initialize Google Play Services
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    buildGoogleApiClient();
                    mGoogleMap.setMyLocationEnabled(true);
                }
            } else {
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            }
        }

        protected synchronized void buildGoogleApiClient() {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }

        @Override
        public void onConnected(Bundle bundle) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }

        @Override
        public void onConnectionSuspended(int i) {
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
        }

    @Override
    protected void onResume() {
        super.onResume();
        doSomethingRepeatedly();
    }

    @Override
        public void onLocationChanged(Location location) {
            mLastLocation = location;

//            LatLng fromPosition = new LatLng(location.getLatitude(), location.getLongitude());
//            LatLng toPosition = new LatLng(-23.5764311,-46.6272482);
//
//            LatLng toPositon2 = new LatLng(-23.5763331,-46.6335457);
//
//            GMapV2Direction md = new GMapV2Direction();
//
//            Document doc = md.getDocument(fromPosition, toPosition, GMapV2Direction.MODE_DRIVING);
//            ArrayList<LatLng> directionPoint = md.getDirection(doc);
//            PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);
//
//            Document doc2 = md.getDocument(fromPosition, toPositon2, GMapV2Direction.MODE_DRIVING);
//            ArrayList<LatLng> directionPoint2 = md.getDirection(doc);
//            PolylineOptions rectLine2 = new PolylineOptions().width(3).color(Color.BLUE);


//            for(int i = 0 ; i < directionPoint.size() ; i++) {
//                rectLine.add(directionPoint.get(i));
//            }
//            mGoogleMap.addPolyline(rectLine2);


//            Cidade c1 = new Cidade();
//            c1.setLat(-23.575189);
//            c1.setLog(-46.688402);
//            c1.setNome("Brigadeiro faria lima 2056");
//
//            Cidade c2 = new Cidade();
//            c2.setLat(-23.577224);
//            c2.setLog(-46.692994);
//            c2.setNome("Alameda Gabriel Monteiro da silva");
//
//            Cidade c3 = new Cidade();
//            c3.setLat(-23.574579);
//            c3.setLog(-46.689615);
//            c3.setNome("Brigadeiro faria lima 1912");
//
//            Cidade c4 = new Cidade();
//            c4.setLat(-23.5312475);
//            c4.setLog(-46.4631361);
//            c4.setNome("Rua alexandre dias");
//
//
//            List<Cidade> cd = new ArrayList<Cidade>();
//            cd.add(c1);
//            cd.add(c2);
//            cd.add(c3);
//            cd.add(c4);


//            if (mCurrLocationMarker != null) {
//                mCurrLocationMarker.remove();
//            }

            if(car != null){
                car.remove();
            }


            LatLng latLngCar = new LatLng(locationcar.getLatitude(), locationcar.getLongitude());
            MarkerOptions make = new MarkerOptions();
            make.position(latLngCar);
            make.title("Car Position");
            make.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car));
            car = mGoogleMap.addMarker(make);
            //move map camera

            //Place current location marker
//            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(latLng);
//            markerOptions.title("Posição Atual");
//            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_user));
//            mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLngCar));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));


//            circle = mGoogleMap.addCircle(new CircleOptions().radius(1000.0).strokeColor(Color.RED).center(new LatLng(location.getLatitude(),location.getLongitude())));
//
//            Location loc = new Location(location);
//            for (Cidade c: cd) {
//                    loc.setLatitude(c.getLat());
//                    loc.setLongitude(c.getLog());
//                    double distancia = loc.distanceTo(location);
//                    LatLng latLng2 = new LatLng(loc.getLatitude(), loc.getLongitude());
//                    MarkerOptions markerOptions2 = new MarkerOptions();
//                    basepoly = new PolylineOptions();
//                    GMapV2Direction md = new GMapV2Direction();
//                if(distancia <= 1000000.0) {
//                    markerOptions2.position(latLng2);
//                    markerOptions2.title(c.getNome());
//                    markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.pagamentosantander));
//                    mCurrLocationMarker = mGoogleMap.addMarker(markerOptions2);
//                    Document doc = md.getDocument(fromPosition, latLng2, GMapV2Direction.MODE_WALKING);
//                    ArrayList<LatLng> directionPoint = md.getDirection(doc);
//                    for(int i = 0 ; i < directionPoint.size() ; i++) {
//                        basepoly.add(directionPoint.get(i));
//                    }
//                    rectLine = mGoogleMap.addPolyline(basepoly);
//                    String teste = md.getDistanceText(doc);
//                    basepoly.color(Color.BLUE).width(6);
//                    TextView txt = new TextView(this);
//                    txt.setText(teste);
//                    txt.setTextSize(50);
//                    txt.setTextColor(Color.BLACK);
//                    Toast.makeText(this, c.getNome() + teste, Toast.LENGTH_LONG).show();
//                    nomes.add(c.getNome());
//                } else {
//                    markerOptions2.position(latLng2);
//                    markerOptions2.title(c.getNome());
//                    markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
//                    mCurrLocationMarker = mGoogleMap.addMarker(markerOptions2);
//                }
//            }

//            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(14));

            //stop location updates
            if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }
        }

    public void relogioEventoLoc(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {

                try {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            int valorRelogio = count++;
                            Log.i("RELOGIO", "***********************"+count);

                            txtRelogioLoc.setText(String.valueOf(valorRelogio));


                        }
                    });





                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }, 0, 1000);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

        public boolean checkLocationPermission() {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                    //Prompt the user once explanation has been shown
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                }
                return false;
            } else {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    showGPSDisabledAlertToUser();
                }

                return true;
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_LOCATION: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.
                        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                showGPSDisabledAlertToUser();
                            }

                            if (mGoogleApiClient == null) {
                                buildGoogleApiClient();
                            }
                            //mGoogleMap.setMyLocationEnabled(true);
                        }
                    } else {
                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                        Toast.makeText(this, "Permissão Negada", Toast.LENGTH_LONG).show();
                    }
                    return;
                }
                // other 'case' lines to check for other
                // permissions this app might request
            }
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSafe:
                helper.registraSafeVeiculo(this, url);
                break;
            case R.id.ic_voltar:
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    public void processResult(String result) {
        JSONObject listLoc = null;
        try {
            listLoc = new JSONObject(result);
            latitude = listLoc.getString("latitude");
            longitude = listLoc.getString("longitude");


            double latcar = Double.parseDouble(latitude);
            double loncar = Double.parseDouble(longitude);
            locationcar = new Location("");
            locationcar.setLatitude(latcar);
            locationcar.setLongitude(loncar);

            Location locuser = new Location("");
            locuser.setLatitude(latitudeAux);
            locuser.setLongitude(longitudeAux);

            onLocationChanged(locuser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
