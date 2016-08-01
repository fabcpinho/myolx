package fabiopinho.myolx.realm.repository;

import fabiopinho.myolx.model.Ads;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by pinho on 31/07/2016.
 */
public interface IAdsRepository {
    interface OnSaveAdCallback {
        void onSuccess();
        void onError(String message);
    }

    interface OnGetAdByIdCallback {
        void onSuccess(Ads ad);
        void onError(String message);
    }

    interface OnGetAllAdsCallback {
        void onSuccess(RealmResults<Ads> ads);
        void onError(String message);
    }

    interface OnGetAdsCallback{
        void onSuccess(RealmList<Ads> ads);
        void onError(String message);
    }

    void addAd(Ads ad, OnSaveAdCallback callback);

    void addAdByResponseInfoId(Ads ad, String adId, OnSaveAdCallback callback);

    void getAllAds(OnGetAllAdsCallback callback);

    void getAllAdsByResponseInfoId(int id, OnGetAdsCallback callback);

    void getAdById(String id, OnGetAdByIdCallback callback);
}
