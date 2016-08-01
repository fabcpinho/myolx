package fabiopinho.myolx.realm.repository;

import fabiopinho.myolx.model.Ads;
import fabiopinho.myolx.model.ResponseInfo;
import io.realm.RealmResults;

/**
 * Created by pinho on 31/07/2016.
 */
public interface IReponseInfoRepository {
    interface OnAddResponseInfoCallback {
        void onSuccess();
        void onError(String message);
    }

    interface OnGetAllResponseInfoCallback {
        void onSuccess(RealmResults<ResponseInfo> responses);
        void onError(String message);
    }

    interface OnGetResponseInfoByIdCallback {
        void onSuccess(ResponseInfo response);
        void onError(String message);
    }

    interface OnDeleteResponseInfoCallback {
        void onSuccess();
        void onError(String message);
    }

    void addResponseInfo(ResponseInfo response, OnAddResponseInfoCallback callback);

    void deleteResponseInfoById(String Id, OnDeleteResponseInfoCallback callback);

    void getAllResponseInfos(OnGetAllResponseInfoCallback callback);

    void getResponseInfoById(int id, OnGetResponseInfoByIdCallback callback);
}
