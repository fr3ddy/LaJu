package de.dhbw_loerrach.laju;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.HashMap;


public class Offer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        OfferItem o = (OfferItem) intent.getSerializableExtra("offer");
        //TODO: DESIGN DEN SHIIIIT
        TextView offerErdat = (TextView) findViewById(R.id.offerErdat);
        TextView offerText = (TextView) findViewById(R.id.offerText);
        TextView offerName = (TextView) findViewById(R.id.offerName);
        TextView offerStatus = (TextView) findViewById(R.id.offerStatus);

        offerErdat.setText(o.getErdat());
        offerText.setText(o.getText());
        offerName.setText(o.getUserfirstname()+" "+o.getUserlastname().substring(0,1)+".");
        String status;
        if(o.isDone() == true){
            status = "Erledigt";
        }else{
            status = "Zu haben";
        }
        if(o.isOpen() == true){
            status += " - Offen";
        }else{
            status += " - Geschlossen";
        }
        offerStatus.setText(status);
        setTitle(o.getTitle());

        HashMap<String , String> params = new HashMap<String , String>();
        params.put("appkey" , "123456");
        params.put("tauschid" , ""+o.getTauschid());
        Receiver receiver = new Receiver(this);
        receiver.fillComments(params);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_offer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                setResult(1338);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }
}
