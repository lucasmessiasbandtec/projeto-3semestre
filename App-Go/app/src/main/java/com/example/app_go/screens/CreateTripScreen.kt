 package com.example.app_go.screens

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.app_go.R
import com.example.app_go.fragments.FilterFragment
import com.example.app_go.models.Filter
import com.example.app_go.models.request.NewTripRequest
import com.example.app_go.sessionManager.SessionManager
import com.example.app_go.utils.CommonMaps
import com.example.app_go.utils.GoogleMapDTO
import com.example.appclienterest.ApiGo
import com.example.appclienterest.ConexaoApiGo
import com.example.locaisproximos.Model.MyPlaces
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

 class CreateTripScreen : AppCompatActivity(), OnMapReadyCallback {

     private lateinit var mMap: GoogleMap
     lateinit var btn_start: Button
     lateinit var btn_end: Button

     lateinit var location1: LatLng
     lateinit var location2: LatLng

     lateinit var endereco1: String
     lateinit var endereco2: String

     lateinit var mService:ApiGo

     var listaFiltros = mutableListOf<Filter>()

     internal lateinit var currentPlace:MyPlaces

     private var latitude:Double=0.toDouble()
     private var longitude:Double=0.toDouble()

     private lateinit var mLastLocation: Location
     private var mMarker: Marker?=null

     lateinit var fusedLocationProviderClient: FusedLocationProviderClient
     lateinit var locationRequest: LocationRequest
     lateinit var locationCallback: LocationCallback

     companion object {
         private const val MY_PERMISSION_CODE: Int = 1000
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_trip_screen)
        StatusBarUtil.setTranslucent(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapCreateTrip) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)

         if(requestCode == 100 && resultCode == RESULT_OK) {
             val place: Place = Autocomplete.getPlaceFromIntent(data!!)

             location1 = (place.latLng!!)
             endereco1 = place.address!!.substringBefore("-")

             mMap.addMarker(MarkerOptions().position(location1!!).title("Você está aqui"))
             mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1, 10f)) //o 10f é o zoom da camera que vai de 1f(global) até 15f(estabelecimento)

         }
         else if(requestCode == 200 && resultCode == RESULT_OK){
             val place: Place = Autocomplete.getPlaceFromIntent(data!!)

             location2 = (place.latLng!!)
             endereco2 = place.address!!.substringBefore("-")

             mMap.addMarker(MarkerOptions().position(location2!!).title("Você irá para cá"))
             mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location2, 10f))

             val URL = getDirectionURL(location1,location2)

             GetDirection(URL).execute()
         }

         else if(requestCode == 300 && resultCode == RESULT_OK){
             var address: String
             val name = data?.getStringExtra("name").toString()
             val photo = data?.getStringExtra("photo").toString()

             var r = Random.nextInt(0,10)

             when (r) {
                 0 -> {
                     val filter = Filter(name = name, latitude = "nada", longitude = "Av Fernando Simonsen, 587", image = photo )
                     listaFiltros.add(filter)
                 }
                 1 -> {
                     val filter = Filter(name = name, latitude = "nada", longitude = "Travessa Acácia, 90", image = photo )
                     listaFiltros.add(filter)
                 }
                 2 -> {
                     val filter = Filter(name = name, latitude = "nada", longitude = "Rua Afonso Pena, 12", image = photo )
                     listaFiltros.add(filter)
                 }
                 3 -> {
                     val filter = Filter(name = name, latitude = "nada", longitude = "Rua Alfredo Maluf, 9819", image = photo )
                     listaFiltros.add(filter)
                 }
                 4 -> {
                     val filter = Filter(name = name, latitude = "nada", longitude = "Travessa Carvalho Monteiro, 120", image = photo )
                     listaFiltros.add(filter)
                 }
                 5 -> {
                     val filter = Filter(name = name, latitude = "nada", longitude = "Rua Modelho Padilha, 142", image = photo )
                     listaFiltros.add(filter)
                 }
                 6 -> {
                     val filter = Filter(name = name, latitude = "nada", longitude = "Av Monteiro Chamariz, 56", image = photo )
                     listaFiltros.add(filter)
                 }
                 7 -> {
                     val filter = Filter(name = name, latitude = "nada", longitude = "Rua Marina, 23", image = photo )
                     listaFiltros.add(filter)
                 }
                 8 -> {
                     val filter = Filter(name = name, latitude = "nada", longitude = "Alameda Lucas Miraz,  112", image = photo )
                     listaFiltros.add(filter)
                 }
                 9 -> {
                     val filter = Filter(name = name, latitude = "nada", longitude = "Travessa Lucio Pas, 7654", image = photo )
                     listaFiltros.add(filter)
                 }
                 10 -> {
                     val filter = Filter(name = name, latitude = "nada", longitude = "Rua Botelho, 902", image = photo )
                     listaFiltros.add(filter)
                 }
             }

         }

         else if(resultCode == AutocompleteActivity.RESULT_ERROR){
             val status: Status = Autocomplete.getStatusFromIntent(data!!)
             Toast.makeText(this, status.statusMessage, Toast.LENGTH_SHORT).show()
         }
     }

     fun onCheckboxClicked(view: View) {
         if (view is CheckBox) {
             val checked: Boolean = view.isChecked

             when (view.id) {
                 R.id.cb_hospitais -> {
                     if (checked) {
                         nearByPlace("hospital")
                     }
                 }
                 R.id.cb_bares -> {
                     if (checked) {
                         nearByPlace("bar")
                     }
                 }
                 R.id.cb_hoteis -> {
                     if (checked) {
                         nearByPlace("hotel")
                     }
                 }
                 R.id.cb_parques -> {
                     if (checked) {
                         nearByPlace("park")
                     }
                 }
             }
         }
     }

     private fun nearByPlace(typePlace: String) {

         var directions = mMap.projection.visibleRegion.latLngBounds.center
         val url = getUrl(directions.latitude,directions.longitude,typePlace)

         mService.getNearbyPlaces(url)
             .enqueue(object : retrofit2.Callback<MyPlaces>{
                 override fun onResponse(call: Call<MyPlaces>, response: Response<MyPlaces>) {

                     currentPlace = response.body()!!

                     if(response!!.isSuccessful) {

                         for(i in 0 until response!!.body()!!.results!!.size) {
                             val markerOptions=MarkerOptions()
                             val googlePlace = response.body()!!.results!![i]
                             val lat = googlePlace.geometry!!.location!!.lat
                             val lng = googlePlace.geometry!!.location!!.lng
                             val placeName = googlePlace.name
                             val latLng = LatLng(lat,lng)

                             markerOptions.position(latLng)
                             markerOptions.title(placeName)
                             if(typePlace.equals("hospital"))
                                 markerOptions.icon(
                                     BitmapDescriptorFactory.defaultMarker(
                                         BitmapDescriptorFactory.HUE_YELLOW))
                             else if (typePlace.equals("bar"))
                                 markerOptions.icon(
                                     BitmapDescriptorFactory.defaultMarker(
                                         BitmapDescriptorFactory.HUE_GREEN))
                             else if (typePlace.equals("hotel"))
                                 markerOptions.icon(
                                     BitmapDescriptorFactory.defaultMarker(
                                         BitmapDescriptorFactory.HUE_BLUE))
                             else if (typePlace.equals("park"))
                                 markerOptions.icon(
                                     BitmapDescriptorFactory.defaultMarker(
                                         BitmapDescriptorFactory.HUE_RED))
                             else
                                 markerOptions.icon(
                                     BitmapDescriptorFactory.defaultMarker(
                                         BitmapDescriptorFactory.HUE_BLUE))

                             markerOptions.snippet(i.toString()) //Assign index for marker

                             mMap!!.addMarker(markerOptions)
                             mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                             mMap!!.animateCamera(CameraUpdateFactory.zoomTo(14f))

                         }
                     }
                 }

                 override fun onFailure(call: Call<MyPlaces>, t: Throwable) {
                     Toast.makeText(baseContext,""+t!!.message,Toast.LENGTH_SHORT).show()
                 }
             })
     }

     private fun getUrl(latitude: Double, longitude: Double, typePlace: String): String {
         val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
         googlePlaceUrl.append("?location=$latitude,$longitude")
         googlePlaceUrl.append("&radius=5000")
         googlePlaceUrl.append("&type=$typePlace")
         googlePlaceUrl.append("&key=AIzaSyCtrLnvf05yT8XXuk19chqOXp8egn7aPHA")

         Log.d("URL_DEBUG",googlePlaceUrl.toString())
         return googlePlaceUrl.toString()
     }

     private fun buildLocationCallBack() {
         locationCallback = object : LocationCallback() {
             override fun onLocationResult(p0: LocationResult) {
                 mLastLocation = p0!!.locations.get(p0!!.locations.size-1) //Get last location

                 if(mMarker !=null)
                 {
                     mMarker!!.remove()
                 }

                 latitude = mLastLocation.latitude
                 longitude = mLastLocation.longitude

                 val latLng = LatLng(latitude, longitude)
                 val markerOptions = MarkerOptions()
                     .position(latLng)
                     .title("Sua localização")
                     .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                 mMarker = mMap!!.addMarker(markerOptions)

                 mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                 mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))
             }
         }
     }

     private fun buildLocationRequest() {
         locationRequest = LocationRequest.create().apply {
             priority = LocationRequest.PRIORITY_HIGH_ACCURACY
             interval = 5000
             fastestInterval = 3000
             smallestDisplacement = 10f
         }
     }

     private fun checkLocationPermission() : Boolean {

         if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
         {
             if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
                 ActivityCompat.requestPermissions(this, arrayOf(
                     android.Manifest.permission.ACCESS_FINE_LOCATION
                 ),  MY_PERMISSION_CODE)
             else
                 ActivityCompat.requestPermissions(this, arrayOf(
                     android.Manifest.permission.ACCESS_FINE_LOCATION
                 ), MY_PERMISSION_CODE)
             return false
         }
         else
             return true
     }

     override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults)
         when(requestCode)
         {
             MY_PERMISSION_CODE->{
                 if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                     if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                         if(checkLocationPermission()) {
                             buildLocationRequest();
                             buildLocationCallBack();

                             fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
                             fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

                             mMap.isMyLocationEnabled=true
                         }
                 }
                 else {
                     Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show()
                 }
             }
         }
     }

     override fun onStop() {
         fusedLocationProviderClient.removeLocationUpdates(locationCallback)
         super.onStop()
     }

     //Função que define a URL da API Directions do Google Maps
     fun getDirectionURL(origin:LatLng,dest:LatLng) : String{
         return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&key=AIzaSyCtrLnvf05yT8XXuk19chqOXp8egn7aPHA&sensor=false&mode=driving"
     }

     //O método é tipo um retrofit (mais simples de usar) que pede como parametro uma URL. Esse método é chamado no elseif do botão dois. Ele faz a requisição e converte o JSON nos parametros do GoogleMapsDTO
     private inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
         override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
             val client = OkHttpClient()
             val request = Request.Builder().url(url).build()
             val response = client.newCall(request).execute()
             val data = response.body()!!.string()
             Log.d("GoogleMap" , " data : $data")
             val result =  ArrayList<List<LatLng>>()
             try{
                 val respObj = Gson().fromJson(data, GoogleMapDTO::class.java)

                 val path =  ArrayList<LatLng>()

                 for (i in 0..(respObj.routes[0].legs[0].steps.size-1)){
                     path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                 }
                 result.add(path)
             }catch (e:Exception){
                 e.printStackTrace()
             }
             return result
         }

         override fun onPostExecute(result: List<List<LatLng>>) {
             val lineoption = PolylineOptions() //opções da linha do trajeto, aqui da pra definir praticamente qualquer coisa
             for (i in result.indices){
                 lineoption.addAll(result[i])
                 lineoption.width(10f)
                 lineoption.color(Color.BLUE)
                 lineoption.geodesic(true)
             }
             mMap.addPolyline(lineoption)
         }
     }

     //(Método pronto) As direções que a APi devolvem vem em um formato bizarro, esse método trata o formato e faz ficar bonitinho no mapa.
     fun decodePolyline(encoded: String): List<LatLng> {
         val poly = ArrayList<LatLng>()
         var index = 0
         val len = encoded.length
         var lat = 0
         var lng = 0

         while (index < len) {
             var b: Int
             var shift = 0
             var result = 0
             do {
                 b = encoded[index++].toInt() - 63
                 result = result or (b and 0x1f shl shift)
                 shift += 5
             } while (b >= 0x20)
             val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
             lat += dlat

             shift = 0
             result = 0
             do {
                 b = encoded[index++].toInt() - 63
                 result = result or (b and 0x1f shl shift)
                 shift += 5
             } while (b >= 0x20)
             val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
             lng += dlng

             val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
             poly.add(latLng)
         }

         return poly
     }

     override fun onMapReady(googleMap: GoogleMap) {

         mMap = googleMap

         //Inicializando o Places e passando a chave da API
         Places.initialize(this, "AIzaSyCtrLnvf05yT8XXuk19chqOXp8egn7aPHA") //NÃO POSTAR ESSA CHAVE EM LUGAR NENHUM PLZ

         //Abrindo a barra de pesquisa do places no botão1
         btn_start = findViewById(R.id.btn_start)
         btn_start.setOnClickListener {
             //Cria uma lista que salva como objeto o local/rua/pais que o usuário digita na barra de pesquisa. Esse objeto contém o endereço e o Lat&Lng
             val list1 = listOf<Place.Field>(Place.Field.ADDRESS, Place.Field.LAT_LNG)
             val intent = Intent(
                 Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, list1).build(this)
             )
             startActivityForResult(intent, 100)
         }

         btn_end = findViewById(R.id.btn_end)
         btn_end.setOnClickListener {
             val list2 = listOf<Place.Field>(Place.Field.ADDRESS, Place.Field.LAT_LNG)
             val intent = Intent(
                 Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, list2).build(this)
             )
             startActivityForResult(intent, 200)
         }

         //Init Service
         mService = CommonMaps.googleApiService

         if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             if(checkLocationPermission()) {
                 buildLocationRequest();
                 buildLocationCallBack();

                 fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
                 fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
             }
         }
         else {
             buildLocationRequest();
             buildLocationCallBack();

             fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
             fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
         }

         mMap.uiSettings.isZoomControlsEnabled=true
         mMap.setOnMarkerClickListener { marker ->
             CommonMaps.currentResult = currentPlace!!.results!![Integer.parseInt(marker.snippet)]
             val intent = Intent(this@CreateTripScreen, ViewPlaceScreen::class.java)
             startActivityForResult(intent, 300)
             true
         }
     }


    fun filters(view: View) {
        val filtersBottomSheet = FilterFragment()
        filtersBottomSheet.show(supportFragmentManager,"BottomSheetDialog")
    }

    fun viajar(view: View) {
        val apiGoTravel = ConexaoApiGo.criar()

        val newTrip = NewTripRequest(destinyLatitude = location2.latitude.toString(),destinyLongitude = location2.longitude.toString(),
            endPoint = endereco2, startLatitude = location1.latitude.toString(),startLongitude = location1.longitude.toString(),
            startingPoint = endereco1, filter = listaFiltros)

        apiGoTravel.createTrip(token = "Bearer ${SessionManager(this).fetchAuthToken()}", newTrip).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code().toString() == "200") {
                    Toast.makeText(baseContext, "Viagem cirada com sucesso!", Toast.LENGTH_SHORT).show()

                } else if (response.code().toString() == "400") {
                    Toast.makeText(baseContext, "Falha na autenticação", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(baseContext, "Ocorreu um erro ao criar a viagem", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })

        val intent = Intent (this, ItinerarioScreen::class.java)
        startActivity(intent)
    }

     fun back_home(view: View) {val intent = Intent (this, HomeScreen::class.java)
         startActivity(intent)}

     fun back_itinerario(view: View) {val intent = Intent (this, ItinerarioScreen::class.java)
         startActivity(intent)}

     fun back_create_trip(view: View) {val intent = Intent (this, CreateTripScreen::class.java)
         startActivity(intent)}

     fun back_last_trip(view: View) {val intent = Intent (this,LastTripScreen::class.java)
         startActivity(intent)}

     fun profile(view: View) {
         val intent = Intent (this, ProfileScreen::class.java)
         startActivity(intent)}
 }