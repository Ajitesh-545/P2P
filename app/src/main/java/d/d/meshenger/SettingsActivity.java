package d.d.meshenger;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.ObjectsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Locale;

public class SettingsActivity extends MeshengerActivity {
    String nick;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle(getResources().getString(R.string.menu_settings));
        prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        nick = prefs.getString("username", "undefined");

        findViewById(R.id.changeNickLayout).setOnClickListener((v) -> changeNick());


    }




    private void changeNick(){
        EditText et = new EditText(this);
        et.setText(nick);
        et.setSelection(nick.length());
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.settings_change_nick))
                .setView(et)
                .setPositiveButton("ok", (dialogInterface, i) -> {
                    nick = et.getText().toString();
                    prefs.edit().putString("username", nick).apply();
                    syncSettings("username", nick);
                    initViews();
                })
                .setNegativeButton(getResources().getText(R.string.cancel), null)
                .show();
    }


    private void syncSettings(String what, String content){
        Intent intent = new Intent("settings_changed");
        intent.putExtra("subject", what);
        intent.putExtra(what, content);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    private void initViews(){
        ((TextView) findViewById(R.id.nickTv)).setText(nick);

    }
}
