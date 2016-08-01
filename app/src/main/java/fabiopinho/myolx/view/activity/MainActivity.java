package fabiopinho.myolx.view.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
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
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

import java.util.ArrayList;

import fabiopinho.myolx.MyOlxApp;
import fabiopinho.myolx.R;
import fabiopinho.myolx.model.Ads;
import fabiopinho.myolx.model.ResponseInfo;
import fabiopinho.myolx.presenters.impl.AdsPresenter;
import fabiopinho.myolx.view.slidingtabs.SlidingTabLayout;
import fabiopinho.myolx.view.activity.base.BaseActivity;
import io.realm.Realm;

public class MainActivity extends BaseActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

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

        // Attach the page change listener to tab strip and **not** the view pager inside the activity
        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {

            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                test();
            }
        });

    }


    public void test(){
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

    public void interpretJson(JSONObject jsonObject){
        // Persist your data in a transaction
        // Get a Realm instance for this thread
        ResponseInfo mRi = new ResponseInfo(1);
        Ads ad = new Ads();
        ad.setId("asdasd");
        ad.setTitle("adsa");
        ad.setPrice_numeric("123");
        ad.setDescription("desc");


        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final ResponseInfo ri = realm.copyToRealm(mRi); // Persist unmanaged objects
        final Ads managedAd = realm.copyToRealm(ad); // Persist unmanaged objects
        ri.getAds().add(managedAd);
        realm.commitTransaction();
        // Asynchronously update objects on a background thread
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                /*Dog dog = bgRealm.where(Dog.class).equalTo("age", 1).findFirst();
                dog.setAge(3);*/
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Original queries and Realm objects are automatically updated.
               Log.d("Fabio", "Ad: "+ managedAd.getId());
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
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initComponents() {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<String> tabTitles = new ArrayList();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

            tabTitles.add("Map");
            tabTitles.add("List");


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
                    return "Map";
                case 1:
                    return "List";
            }
            return null;
        }
    }
}
