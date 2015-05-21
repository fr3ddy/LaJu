package de.dhbw_loerrach.laju;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class NewInfo extends ActionBarActivity {

    private String benutzername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_info);
        Intent intent = getIntent();
        benutzername = intent.getStringExtra("benutzername");
    }

    public String getBenutzername(){
        return benutzername;
    }
}
