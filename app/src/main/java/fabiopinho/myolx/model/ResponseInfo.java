package fabiopinho.myolx.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by pinho on 30/07/2016.
 */
public class ResponseInfo extends RealmObject{
    @PrimaryKey
    private int pageNumber;
    private String next_page_url;
    private RealmList<Ads> ads;

    //Constructor
    public ResponseInfo() {
    }

    public ResponseInfo(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public RealmList<Ads> getAds() {
        return ads;
    }

    public void setAds(RealmList<Ads> ads) {
        this.ads = ads;
    }
}
