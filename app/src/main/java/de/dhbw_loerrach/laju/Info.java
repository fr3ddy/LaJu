package de.dhbw_loerrach.laju;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;


public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        InfoItem i = (InfoItem) intent.getSerializableExtra("info");

        TextView infoItemText = (TextView) findViewById(R.id.infoItemText);
        TextView infoItemErstelldatum = (TextView) findViewById(R.id.infoItemErstelldatum);
        TextView infoItemAutor = (TextView) findViewById(R.id.infoItemAutor);

        infoItemAutor.setText(i.getAutor());
        infoItemErstelldatum.setText(i.getErstelldatum());
        infoItemText.setText(i.getText());
        setTitle(i.getTitel());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }
}
