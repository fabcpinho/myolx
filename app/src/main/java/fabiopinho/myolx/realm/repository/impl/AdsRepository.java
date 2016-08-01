package fabiopinho.myolx.realm.repository.impl;

import java.util.UUID;

import fabiopinho.myolx.MyOlxApp;
import fabiopinho.myolx.model.Ads;
import fabiopinho.myolx.model.ResponseInfo;
import fabiopinho.myolx.presenters.IAdsPresenter;
import fabiopinho.myolx.realm.repository.IAdsRepository;
import fabiopinho.myolx.realm.table.RealmTable;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by pinho on 31/07/2016.
 */
public class AdsRepository implements IAdsRepository{
    @Override
    public void addAd(Ads ad, OnSaveAdCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Ads realmAd = realm.createObject(Ads.class);
        realmAd.setId(ad.getId());
        realmAd.setTitle(ad.getTitle());
        realmAd.setDescription(ad.getDescription());
        realmAd.setPrice_numeric(ad.getPrice_numeric());
        //TODO add remaining fields
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void addAdByResponseInfoId(Ads ad, String responseId, OnSaveAdCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Ads realmAd = realm.createObject(Ads.class);
        realmAd.setId(ad.getId());
        realmAd.setTitle(ad.getTitle());
        realmAd.setDescription(ad.getDescription());
        realmAd.setPrice_numeric(ad.getPrice_numeric());

        ResponseInfo rp = realm.where(ResponseInfo.class).equalTo(RealmTable.ResponseInfo.ID, responseId).findFirst();
        rp.getAds().add(realmAd);

        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();

    }


    @Override
    public void getAllAds(OnGetAllAdsCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Ads> results = realm.where(Ads.class).findAll();

        if (callback != null)
            callback.onSuccess(results);
    }

    @Override
    public void getAllAdsByResponseInfoId(int id, OnGetAdsCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        ResponseInfo r = realm.where(ResponseInfo.class).equalTo(RealmTable.ResponseInfo.ID, id).findFirst();
        RealmList<Ads> ads = new RealmList<>();
        if(r!=null)
             ads = r.getAds();

        if (callback != null)
            callback.onSuccess(ads);
    }

    @Override
    public void getAdById(String id, OnGetAdByIdCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        Ads ads = realm.where(Ads.class).equalTo(RealmTable.Ads.ID, id).findFirst();

        if (callback != null)
            callback.onSuccess(ads);
    }


}
