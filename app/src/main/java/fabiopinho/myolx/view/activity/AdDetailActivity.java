package fabiopinho.myolx.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import fabiopinho.myolx.R;
import fabiopinho.myolx.model.Ads;
import fabiopinho.myolx.presenters.impl.AdDetailPresenter;
import fabiopinho.myolx.realm.table.RealmTable;
import fabiopinho.myolx.view.activity.base.BaseActivity;
import io.realm.RealmList;

/**
 * Created by pinho on 01.08.16.
 */
public class AdDetailActivity extends BaseActivity {
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    public RealmList<Ads> ads = new RealmList<>();
    public AdDetailPresenter presenter;
    public String mRequestedAdId;
    int mCurrentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabiopinho.myolx.view.slidingtabs.SlidingTabLayout slidingTabLayout = (fabiopinho.myolx.view.slidingtabs.SlidingTabLayout)findViewById(R.id.sliding_tabs);
        slidingTabLayout.setVisibility(View.GONE);

        Intent intent = getIntent();
        mRequestedAdId = intent.getStringExtra(RealmTable.Ads.ID);

        presenter = new AdDetailPresenter(this);
        presenter.subscribeCallbacks();

        presenter.getAllAdsByResponseInfoId(1);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new AdDetailPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        presenter.getAdsById(mRequestedAdId);
    }

    public void showAds(RealmList<Ads> ads) {
        this.ads = ads;
    }

    public void setCurrentItem(Ads ad) {
        mCurrentPos = ads.indexOf(ad);
        mPager.setCurrentItem(mCurrentPos);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class AdDetailPagerAdapter extends FragmentPagerAdapter {
        public AdDetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return AdDetailPageFragment.create(position);
        }

        @Override
        public int getCount() {
            return ads.size();
        }
    }
}
