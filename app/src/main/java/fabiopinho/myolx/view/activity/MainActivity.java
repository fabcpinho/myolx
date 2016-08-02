package fabiopinho.myolx.view.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fabiopinho.myolx.MyOlxApp;
import fabiopinho.myolx.R;
import fabiopinho.myolx.model.Ads;
import fabiopinho.myolx.model.ResponseInfo;
import fabiopinho.myolx.presenters.impl.AdsPresenter;
import fabiopinho.myolx.utils.MessageEvent;
import fabiopinho.myolx.view.slidingtabs.SlidingTabLayout;
import fabiopinho.myolx.view.activity.base.BaseActivity;
import io.realm.Realm;
import io.realm.RealmList;

public class MainActivity extends BaseActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public AdsPresenter presenter;
    RealmList<Ads> ads = new RealmList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Creatye the adapter that will return a fragment for each of the three
        // primary sections of the activit.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        presenter = new AdsPresenter(this);
        presenter.subscribeCallbacks();

        // Give the SlidingTabLayout the ViewPager
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        // Center the tabs in the layout
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabView(R.layout.custom_tab, 0);

        // Customize tab color
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorPrimary);
            }

            @Override
            public int getDividerColor(int position) {
                return Color.WHITE;
            }
        });
        slidingTabLayout.setViewPager(mViewPager);

        download_info();
    }

    public void showAds(RealmList<Ads> ads) {
       this.ads = ads;
        EventBus.getDefault().post(new MessageEvent("update"));
    }


    public void download_info(){
        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        String url = "https://olx.pt/i2/ads/?json=1&amp;search[category_id]=25";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        interpretJson(response);
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Fabio", "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

        // Adding request to request queue
        if(MyOlxApp.getInstance()!=null)
            MyOlxApp.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        else{
            Log.d("Fabio", "MyOlxApp.getInstance() returning null");
        }
    }

    public void interpretJson(final JSONObject jsonObject){

        Log.d("Fabio", "JSON: "+jsonObject.toString());

        final Realm realm = Realm.getDefaultInstance();
        // Asynchronously update objects on a background thread
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                try {
                    int page = jsonObject.getInt("page");
                    ResponseInfo mRi = new ResponseInfo(page);
                    final ResponseInfo ri = bgRealm.copyToRealmOrUpdate(mRi); // Persist unmanaged objects
                    int adsOnPage = jsonObject.getInt("ads_on_page");
                    JSONArray adsArray = jsonObject.getJSONArray("ads");
                    for(int i = 0; i< adsOnPage; i++){
                        JSONObject adObject = adsArray.getJSONObject(i);
                        Log.d("Fabio", "Ad: "+adObject);
                        Ads ad = new Ads();
                        ad.setId(adObject.getString("id"));
                        ad.setTitle(adObject.getString("title"));
                        ad.setMap_lat(adObject.getDouble("map_lat"));
                        ad.setMap_lon(adObject.getDouble("map_lon"));
                        String pricetype = adObject.getString("price_type");
                        ad.setPrice_type(pricetype);
                        if(pricetype.equals("budget")){
                            ad.setPrice_numeric("budget");
                        }else if(pricetype.equals("price")){
                            ad.setPrice_numeric(adObject.getString("price_numeric"));
                        }
                        ad.setStatus(adObject.getString("status"));
                        ad.setCity_label(adObject.getString("city_label"));
                        ad.setDescription(adObject.getString("description"));
                        final Ads managedAd = bgRealm.copyToRealmOrUpdate(ad); // Persist unmanaged objects
                        ri.getAds().add(managedAd);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Original queries and Realm objects are automatically updated.
                Log.d("Fabio", "SUCCESS ADDING STUFF");
                presenter.getAllAdsByResponseInfoId(1);

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sync) {
            download_info();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<String> tabTitles = new ArrayList();
        private String tab1 = "Map";
        private String tab2 = "List";
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            tabTitles.add(tab1);
            tabTitles.add(tab2);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==0){
                return MapsFragment.newInstance();
            }else
                return AdsFragment.newInstance();
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return tab1;
                case 1:
                    return tab2;
            }
            return null;
        }
    }
}
