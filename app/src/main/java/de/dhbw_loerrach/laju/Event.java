package de.dhbw_loerrach.laju;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class Event extends AppCompatActivity {
    private CharSequence mTitle;
    private EventItem e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        e = (EventItem) intent.getSerializableExtra("event");
        TextView eventItemUntertitel = (TextView) findViewById(R.id.eventItemUntertitel);
        TextView eventItemBeschreibung = (TextView) findViewById(R.id.eventItemBeschreibung);
        TextView eventItemDatumVon = (TextView) findViewById(R.id.eventItemDatumVon);
        TextView eventItemDatumBis = (TextView) findViewById(R.id.eventItemDatumBis);
        TextView line = (TextView) findViewById(R.id.line);

        setTitle(e.getTitel());

        eventItemBeschreibung.setText(e.getBeschreibung());
        eventItemDatumVon.setText(e.getDatum_von());
        eventItemDatumBis.setText(e.getDatum_bis());

        if(e.getDatum_bis().equals("")) {
            line.setVisibility(View.INVISIBLE);
        }

        new DownloadImageTask((ImageView) findViewById(R.id.eventItemImage)).execute(e.getBild());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.urlBtn);
        if(!e.getUrl().toString().contains("http")) {
            item.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                setResult(1337);
                finish();
                return true;
            case R.id.urlBtn:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(e.getUrl()));
                startActivity(browserIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }
}
