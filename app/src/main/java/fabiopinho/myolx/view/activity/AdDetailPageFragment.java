package fabiopinho.myolx.view.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fabiopinho.myolx.R;
import fabiopinho.myolx.model.Ads;

/**
 * Created by pinho on 01.08.16.
 */
public class AdDetailPageFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    public static AdDetailPageFragment create(int pageNumber) {
        AdDetailPageFragment fragment = new AdDetailPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AdDetailPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        Log.d("Fabio", "everything ok with page number: "+mPageNumber);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);

        Ads mAd = ((AdDetailActivity)getActivity()).ads.get(getPageNumber());
        inflateLabeledTextView(inflater, rootView, "Titulo", mAd.getTitle());
        inflateLabeledTextView(inflater, rootView, "Descricao", mAd.getDescription());
        inflateLabeledTextView(inflater, rootView, "Tipo de preco", mAd.getPrice_type());
        inflateLabeledTextView(inflater, rootView, "Estado", mAd.getStatus());
        inflateLabeledTextView(inflater, rootView, "Cidade", mAd.getCity_label());
        //Todo add as much fields as wanted

        return rootView;
    }

    private void inflateLabeledTextView(LayoutInflater inflater, ViewGroup rootView, String label, String content){
        LinearLayout item = (LinearLayout )rootView.findViewById(R.id.layout_to_inflate);//where you want to add/inflate a view as a child
        View v = inflater.inflate(R.layout.detail_line, null);
        ((TextView)v.findViewById(R.id.label)).setText(label);
        ((TextView)v.findViewById(R.id.content)).setText(content);
        item.addView(v);
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
