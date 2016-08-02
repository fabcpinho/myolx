package fabiopinho.myolx.view.activity.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import fabiopinho.myolx.model.Ads;
import io.realm.RealmList;

/**
 * Created by pinho on 29.07.16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public void showMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
