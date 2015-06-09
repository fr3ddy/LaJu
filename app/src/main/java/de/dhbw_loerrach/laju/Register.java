package de.dhbw_loerrach.laju;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private Register registerinstance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerinstance = this;

        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        setUpFields();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_addInfo);
        if(item != null) {
            item.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setUpFields() {
        final EditText username = (EditText) findViewById(R.id.registerUsername);
        final EditText email = (EditText) findViewById(R.id.registerEmail);
        final EditText email_c = (EditText) findViewById(R.id.registerEmailConfirm);
        final EditText password = (EditText) findViewById(R.id.registerPassword);
        final EditText password_c = (EditText) findViewById(R.id.registerPasswordConfirm);
        final EditText firstname = (EditText) findViewById(R.id.registerFirstname);
        final EditText lastname = (EditText) findViewById(R.id.registerLastname);
        Button registerbtn = (Button) findViewById(R.id.registerFragmentButton);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u = username.getText().toString();
                String e = email.getText().toString();
                String ec = email_c.getText().toString();
                String p = password.getText().toString();
                String pc = password_c.getText().toString();
                String f = firstname.getText().toString();
                String l = lastname.getText().toString();

                if (u.length() == 0) {
                    username.setError("Bitte geben Sie einen Benutzernamen ein!");
                } else if (e.length() == 0) {
                    email.setError("Bitte geben Sie eine E-Mail Adresse ein!");
                } else if (ec.length() == 0) {
                    email_c.setError("Bitte bestätigen Sie Ihre E-Mail Adresse!");
                } else if (p.length() == 0) {
                    password.setError("Bitte geben Sie ein Passwort ein!");
                } else if (pc.length() == 0) {
                    password_c.setError("Bitte bestätigen Sie Ihre Passwort!");
                } else if (f.length() == 0) {
                    firstname.setError("Bitte geben Sie einen Vornamen ein!");
                } else if (l.length() == 0) {
                    lastname.setError("Bitte geben Sie einen Nachnamen ein!");
                } else if (!e.equals(ec)) {
                    email_c.setError("Ihre E-Mail Adressen stimmen nicht überein!");
                } else if (!p.equals(pc)) {
                    password_c.setError("Ihre Passwörter stimmen nicht überein!");
                } else {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("appkey", getString(R.string.appkeyweb));
                    params.put("benutzername", u);
                    params.put("passwort", p);
                    params.put("email", e);
                    params.put("vorname", f);
                    params.put("nachname", l);


                    Receiver receiver = new Receiver(registerinstance);
                    receiver.register(params);
                }
            }
        });
    }
}
