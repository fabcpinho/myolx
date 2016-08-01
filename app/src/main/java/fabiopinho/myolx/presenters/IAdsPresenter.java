package fabiopinho.myolx.presenters;


import fabiopinho.myolx.model.Ads;

/**
 * Created by pinho on 30/07/2016.
 */
public interface IAdsPresenter extends IBasePresenter{

    void addAd(Ads student);

    void addAdByResponseInfoId(Ads student, String universityId);

    void getAllAds();

    void getAllAdsByResponseInfoId(int pageId);

    void getAdsById(String id);

}
