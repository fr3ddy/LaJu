package de.dhbw_loerrach.laju;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class NewInfo extends AppCompatActivity {
    private String benutzername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_info);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        benutzername = intent.getStringExtra(getString(R.string.username));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }

    public String getBenutzername(){
        return benutzername;
    }
}
