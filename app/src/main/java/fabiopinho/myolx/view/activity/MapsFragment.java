package fabiopinho.myolx.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import fabiopinho.myolx.R;
import fabiopinho.myolx.model.Ads;
import fabiopinho.myolx.realm.table.RealmTable;
import fabiopinho.myolx.utils.MessageEvent;
import io.realm.RealmList;

public class MapsFragment extends Fragment
        implements OnMapReadyCallback{

    private GoogleMap mMap;
    private Context context;

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MapView mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);//when you already implement OnMapReadyCallback in your fragment
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void updateAdapter() {
        if(mMap!=null)
            mMap.clear();

        RealmList<Ads> ads = ((MainActivity) getActivity()).ads;
        for (Ads ad : ads) {
            double lat = ad.getMap_lat();
            double lon = ad.getMap_lon();
            // Add a marker in Sydney and move the camera
            LatLng loc = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(loc).title(ad.getTitle()).snippet(ad.getDescription()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            /*mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                @Override
                public void onInfoWindowClick(Marker arg0) {
                    Intent intent = new Intent(context, AdDetailActivity.class);
                    intent.putExtra(RealmTable.Ads.ID, arg0.getId());
                    startActivity(intent);

                }
            });*/
        }
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if(event.message.equals("update"))
            updateAdapter();
    }



    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

}
