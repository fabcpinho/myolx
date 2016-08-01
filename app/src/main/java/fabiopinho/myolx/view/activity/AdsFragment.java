package fabiopinho.myolx.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import fabiopinho.myolx.R;
import fabiopinho.myolx.model.Ads;
import fabiopinho.myolx.presenters.impl.AdsPresenter;
import fabiopinho.myolx.view.adapters.AdsAdapter;
import io.realm.RealmList;

/**
 * Created by pinho on 30/07/2016.
 */
public class AdsFragment extends Fragment {


    private Context context;

    private RecyclerView rvAds;
    private AdsPresenter presenter;
    private AdsAdapter adapter;

    private RealmList<Ads> ads;
    private String responseId;

    public AdsFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AdsFragment newInstance() {
        AdsFragment fragment = new AdsFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AdsPresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribeCallbacks();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unSubscribeCallbacks();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_ads, container, false);
        initRecyclerListener(rootView);
        return rootView;
    }

    private void initRecyclerListener(View root) {
        rvAds = (RecyclerView) root.findViewById(R.id.rv_ads);
        rvAds.setLayoutManager(new LinearLayoutManager(context));
        rvAds.setItemAnimator(new DefaultItemAnimator());
    }

    public void showAds(RealmList<Ads> ads) {
        this.ads = ads;
        adapter = new AdsAdapter(ads);
        rvAds.setAdapter(adapter);
    }

    public void showMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                Log.d("MyFragment", "Not visible anymore.  Stopping audio.");
                presenter.getAllAdsByResponseInfoId(1);
            }
        }
    }
}
