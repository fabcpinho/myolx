package fabiopinho.myolx.realm.repository.impl;

import java.util.UUID;

import fabiopinho.myolx.MyOlxApp;
import fabiopinho.myolx.model.ResponseInfo;
import fabiopinho.myolx.realm.repository.IReponseInfoRepository;
import fabiopinho.myolx.realm.table.RealmTable;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by pinho on 31/07/2016.
 */
public class ResponseInfoRepository implements IReponseInfoRepository{

    @Override
    public void addResponseInfo(ResponseInfo response, OnAddResponseInfoCallback callback) {
        Realm realm = Realm.getInstance(MyOlxApp.getInstance());
        realm.beginTransaction();
        ResponseInfo u = realm.createObject(ResponseInfo.class);
        u.setPageNumber(response.getPageNumber());
        u.setNext_page_url(response.getNext_page_url());
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void deleteResponseInfoById(String Id, OnDeleteResponseInfoCallback callback) {
        Realm realm = Realm.getInstance(MyOlxApp.getInstance());
        realm.beginTransaction();
        ResponseInfo university = realm.where(ResponseInfo.class).equalTo(RealmTable.ID, Id).findFirst();
        university.removeFromRealm();
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void getAllResponseInfos(OnGetAllResponseInfoCallback callback) {
        Realm realm = Realm.getInstance(MyOlxApp.getInstance());
        RealmQuery<ResponseInfo> query = realm.where(ResponseInfo.class);
        RealmResults<ResponseInfo> results = query.findAll();

        if (callback != null)
            callback.onSuccess(results);
    }

    @Override
    public void getResponseInfoById(int id, OnGetResponseInfoByIdCallback callback) {
        Realm realm = Realm.getInstance(MyOlxApp.getInstance());
        ResponseInfo result = realm.where(ResponseInfo.class).equalTo(RealmTable.ID, id).findFirst();

        if (callback != null)
            callback.onSuccess(result);
    }
}
