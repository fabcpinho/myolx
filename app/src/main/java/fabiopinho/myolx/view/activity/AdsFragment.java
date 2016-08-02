package fabiopinho.myolx.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import fabiopinho.myolx.R;
import fabiopinho.myolx.model.Ads;
import fabiopinho.myolx.realm.table.RealmTable;
import fabiopinho.myolx.utils.MessageEvent;
import fabiopinho.myolx.view.adapters.AdsAdapter;
import io.realm.RealmList;

/**
 * Created by pinho on 30/07/2016.
 */
public class AdsFragment extends Fragment{
    private Context context;

    private RecyclerView rvAds;
    private AdsAdapter adapter;
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

    public void updateAdapter() {
        RealmList<Ads> ads = ((MainActivity) getActivity()).ads;
        adapter = new AdsAdapter(ads);
        rvAds.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String id) {
                Intent intent = new Intent(context, AdDetailActivity.class);
                intent.putExtra(RealmTable.Ads.ID, id);
                startActivity(intent);
            }
        });
    }
}
