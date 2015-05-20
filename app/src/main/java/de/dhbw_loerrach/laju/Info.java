package de.dhbw_loerrach.laju;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;


public class Info extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        InfoItem i = (InfoItem) intent.getSerializableExtra("info");

        TextView infoItemText = (TextView) findViewById(R.id.infoItemText);
        TextView infoItemErstelldatum = (TextView) findViewById(R.id.infoItemErstelldatum);
        TextView infoItemAutor = (TextView) findViewById(R.id.infoItemAutor);

        infoItemAutor.setText(i.getAutor());
        infoItemErstelldatum.setText(i.getErstelldatum());
        infoItemText.setText(i.getText());
        setTitle(i.getTitel());
    }

}
