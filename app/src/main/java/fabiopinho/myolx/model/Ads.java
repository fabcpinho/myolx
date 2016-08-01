package fabiopinho.myolx.model;

import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by pinho on 30/07/2016.
 */
public class Ads extends RealmObject{
    @PrimaryKey
    private String id;
    private String url;
    private String preview_url ;
    private String title ;
    private String created ;
    private int age ;
    private String description ;
    private int category_id ;
    private String status ;
    private String campaignSource ;
    private int is_price_negotiable ;
    private String price_type;
    private String price_numeric ;
    private int map_zoom ;
    private float map_lat ;
    private float map_lon ;
    private int map_radius ;
    private String map_location ;
    private long city_id ;
    private int region_id ;
    private String city_label ;
    private String list_label ;
    private String list_label_ad ;
    private String  photos ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCampaignSource() {
        return campaignSource;
    }

    public void setCampaignSource(String campaignSource) {
        this.campaignSource = campaignSource;
    }

    public int getIs_price_negotiable() {
        return is_price_negotiable;
    }

    public void setIs_price_negotiable(int is_price_negotiable) {
        this.is_price_negotiable = is_price_negotiable;
    }

    public String getPrice_type() {
        return price_type;
    }

    public void setPrice_type(String price_type) {
        this.price_type = price_type;
    }

    public String getPrice_numeric() {
        return price_numeric;
    }

    public void setPrice_numeric(String price_numeric) {
        this.price_numeric = price_numeric;
    }

    public int getMap_zoom() {
        return map_zoom;
    }

    public void setMap_zoom(int map_zoom) {
        this.map_zoom = map_zoom;
    }

    public float getMap_lat() {
        return map_lat;
    }

    public void setMap_lat(float map_lat) {
        this.map_lat = map_lat;
    }

    public float getMap_lon() {
        return map_lon;
    }

    public void setMap_lon(float map_lon) {
        this.map_lon = map_lon;
    }

    public int getMap_radius() {
        return map_radius;
    }

    public void setMap_radius(int map_radius) {
        this.map_radius = map_radius;
    }

    public String getMap_location() {
        return map_location;
    }

    public void setMap_location(String map_location) {
        this.map_location = map_location;
    }

    public long getCity_id() {
        return city_id;
    }

    public void setCity_id(long city_id) {
        this.city_id = city_id;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getCity_label() {
        return city_label;
    }

    public void setCity_label(String city_label) {
        this.city_label = city_label;
    }

    public String getList_label() {
        return list_label;
    }

    public void setList_label(String list_label) {
        this.list_label = list_label;
    }

    public String getList_label_ad() {
        return list_label_ad;
    }

    public void setList_label_ad(String list_label_ad) {
        this.list_label_ad = list_label_ad;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }
}
