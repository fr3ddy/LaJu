package de.dhbw_loerrach.laju;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Event extends AppCompatActivity {
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        
        Intent intent = getIntent();
        EventItem e = (EventItem) intent.getSerializableExtra("event");
        TextView eventItemUntertitel = (TextView) findViewById(R.id.eventItemUntertitel);
        TextView eventItemBeschreibung = (TextView) findViewById(R.id.eventItemBeschreibung);
        TextView eventItemDatumVon = (TextView) findViewById(R.id.eventItemDatumVon);
        TextView eventItemDatumBis = (TextView) findViewById(R.id.eventItemDatumBis);

        setTitle(e.getTitel());
        eventItemUntertitel.setText(e.getUntertitel());
        eventItemBeschreibung.setText(e.getBeschreibung());
        eventItemDatumVon.setText(e.getDatum_von());
        eventItemDatumBis.setText(e.getDatum_bis());
        new DownloadImageTask((ImageView) findViewById(R.id.eventItemImage)).execute(e.getBild());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
//        }
        return super.onOptionsItemSelected(item);
    }

}
