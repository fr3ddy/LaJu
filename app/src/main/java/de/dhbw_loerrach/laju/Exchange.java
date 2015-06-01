package de.dhbw_loerrach.laju;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class Exchange extends AppCompatActivity {

    private ExchangeItem o;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        o = (ExchangeItem) intent.getSerializableExtra("offer");

        TextView offerErdat = (TextView) findViewById(R.id.offerErdat);
        TextView offerText = (TextView) findViewById(R.id.offerText);
        TextView offerName = (TextView) findViewById(R.id.offerName);
//        TextView offerStatus = (TextView) findViewById(R.id.offerStatus);
        View offerSeparator = (View) findViewById(R.id.separator);
        final EditText offerComment = (EditText) findViewById(R.id.offerNewComment);
        Button offerButton = (Button) findViewById(R.id.offerSubmitComment);

        offerErdat.setText(o.getErdat());
        offerText.setText(o.getText());
        offerName.setText(o.getUserfirstname() + " " + o.getUserlastname().substring(0, 1) + ".");
//        String status;
//        if (o.isDone() == true) {
//            status = "Erledigt";
//        } else {
//            status = "Zu haben";
//        }
//        if (o.isOpen() == true) {
//            status += " - Offen";
//        } else {
//            status += " - Geschlossen";
//        }
//        offerStatus.setText(status);
        String title;
        if(o.isOpen()) {
            title = o.getTitle();
        } else {
            title = "[Geschlossen] " + o.getTitle();
        }
        setTitle(title);

        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("appkey", "123456");
        params.put("tauschid", "" + o.getTauschid());
        final Receiver receiver = new Receiver(this);
        receiver.fillComments(params);

        if (User.isLoggedIn()) {
            offerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    HashMap<String, String> paramsC = new HashMap<String, String>();
                    paramsC.put("appkey", "123456");
                    paramsC.put("tauschid", "" + o.getTauschid());
                    paramsC.put("user", User.getInstance().username);
                    paramsC.put("text", offerComment.getText().toString());
                    receiver.sendNewComment(paramsC, params);
                }
            });
        } else {
            offerButton.setVisibility(View.INVISIBLE);
            offerComment.setVisibility(View.INVISIBLE);
            offerSeparator.setVisibility(View.INVISIBLE);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_markAsSolved);
        if (item != null) {
            item.setVisible(false);
            if (User.isLoggedIn() && o.isOpen() && o.getUsername().equals(User.getInstance().username)) {
                item.setVisible(true);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_exchange, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                switch (o.getType()) {
                    case "Angebot":
                        setResult(1338);
                        break;
                    case "Nachfragen":
                        setResult(1339);
                        break;
                }
                finish();
                return true;
            case R.id.action_markAsSolved:
                final Receiver receiver = new Receiver(Exchange.this);
                final HashMap<String , String> params = new HashMap<String,String>();
                params.put("appkey" , "123456");
                params.put("user" , o.getUsername());
                params.put("tauschid" , o.getTauschid()+"");

                AlertDialog.Builder builder = new AlertDialog.Builder(Exchange.this);
                builder.setMessage(R.string.dialog_msg_solved).setTitle(R.string.dialog_title_solved);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        params.put("geloest" , "TRUE");
                        receiver.closeExchange(params);
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        params.put("geloest" , "FALSE");
                        receiver.closeExchange(params);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }
}
