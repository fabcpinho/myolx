package fabiopinho.myolx.presenters.impl;

import fabiopinho.myolx.model.Ads;
import fabiopinho.myolx.presenters.IAdsPresenter;
import fabiopinho.myolx.realm.repository.IAdsRepository;
import fabiopinho.myolx.realm.repository.IReponseInfoRepository;
import fabiopinho.myolx.realm.repository.impl.AdsRepository;
import fabiopinho.myolx.realm.repository.impl.ResponseInfoRepository;
import fabiopinho.myolx.view.activity.AdsFragment;
import fabiopinho.myolx.view.activity.MainActivity;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by pinho on 31.07.16.
 */
public class AdsPresenter implements IAdsPresenter {
    private MainActivity view;

    private IAdsRepository.OnSaveAdCallback onSaveAdCallback;
    private IAdsRepository.OnGetAllAdsCallback onGetAllAdsCallback;
    private IAdsRepository.OnGetAdByIdCallback onGetAdByIdCallback;
    private IAdsRepository.OnGetAdsCallback onGetAdsCallback;

    private IAdsRepository adsRepository;
    public AdsPresenter(MainActivity view) {
        this.view = view;
        adsRepository = new AdsRepository();
    }

    @Override
    public void addAd(Ads student) {
        adsRepository.addAd(student, onSaveAdCallback);
    }

    @Override
    public void addAdByResponseInfoId(Ads student, String responseId) {
        adsRepository.addAdByResponseInfoId(student, responseId, onSaveAdCallback);
    }


    @Override
    public void getAllAds() {
        adsRepository.getAllAds(onGetAllAdsCallback);
    }

    @Override
    public void getAllAdsByResponseInfoId(int pageId) {
        adsRepository.getAllAdsByResponseInfoId(pageId, onGetAdsCallback);

    }

    @Override
    public void getAdsById(String id) {
        adsRepository.getAdById(id, onGetAdByIdCallback);
    }


    @Override
    public void subscribeCallbacks() {
        onSaveAdCallback = new IAdsRepository.OnSaveAdCallback() {
            @Override
            public void onSuccess() {
                view.showMessage("Added");
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };

        onGetAllAdsCallback = new IAdsRepository.OnGetAllAdsCallback() {
            @Override
            public void onSuccess(RealmResults<Ads> ads) {

            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };
        onGetAdByIdCallback = new IAdsRepository.OnGetAdByIdCallback() {
            @Override
            public void onSuccess(Ads ads) {

            }

            @Override
            public void onError(String message) {

            }
        };
        onGetAdsCallback = new IAdsRepository.OnGetAdsCallback() {
            @Override
            public void onSuccess(RealmList<Ads> ads) {
                view.showAds(ads);
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };
    }

    @Override
    public void unSubscribeCallbacks() {

    }
}
