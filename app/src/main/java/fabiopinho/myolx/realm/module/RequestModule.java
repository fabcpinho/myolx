package fabiopinho.myolx.realm.module;

import fabiopinho.myolx.model.Ads;
import fabiopinho.myolx.model.ResponseInfo;
import io.realm.annotations.RealmModule;

/**
 * Created by pinho on 31/07/2016.
 */

@RealmModule(classes = {Ads.class, ResponseInfo.class})
public class RequestModule {
}
