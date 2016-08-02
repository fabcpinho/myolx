package fabiopinho.myolx.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fabiopinho.myolx.R;
import fabiopinho.myolx.model.Ads;
import io.realm.RealmList;

/**
 * Created by pinho on 01.08.16.
 */
public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.AdsViewHolder> {
    private OnItemClickListener onItemClickListener;
    private RealmList<Ads> ads;

    public AdsAdapter(RealmList<Ads> ads) {
        this.ads = ads;
    }

    @Override
    public AdsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_ad, parent, false);
        return new AdsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdsViewHolder holder, int position) {
            holder.tvTitle.setText(ads.get(position).getTitle());
            String desc =ads.get(position).getDescription();
            holder.tvPrice.setText(desc);
            String price = ads.get(position).getPrice_numeric();
            holder.tvDescription.setText(price);
    }

    @Override
    public int getItemCount() {
        return ads.size();
    }

    public class AdsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvTitle;
        private TextView tvPrice;
        private TextView tvDescription;

        public AdsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
        }

        @Override
        public void onClick(View v) {
            Ads mAd = ads.get(getAdapterPosition());
            onItemClickListener.onItemClick(mAd.getId());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(String id);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
