package de.dhbw_loerrach.laju;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Event extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

}
