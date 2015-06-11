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

public class EditUser extends AppCompatActivity {

    private EditUser editUserinstance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituser);

        editUserinstance = this;

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
        final EditText username = (EditText) findViewById(R.id.editUserUsername);
        final EditText email = (EditText) findViewById(R.id.editUserEmail);
        final EditText password = (EditText) findViewById(R.id.editUserOldPassword);
        final EditText password_n = (EditText) findViewById(R.id.editUserPassword);
        final EditText password_c = (EditText) findViewById(R.id.editUserPasswordConfirm);
        final EditText firstname = (EditText) findViewById(R.id.editUserFirstname);
        final EditText lastname = (EditText) findViewById(R.id.editUserLastname);
        Button editUserbtn = (Button) findViewById(R.id.editUserFragmentButton);

        username.setText(User.username);
        email.setText(User.email);
        firstname.setText(User.firstname);
        lastname.setText(User.lastname);

        editUserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u = username.getText().toString();
                String e = email.getText().toString();
                String p = password.getText().toString();
                String pn = password_n.getText().toString();
                String pc = password_c.getText().toString();
                String f = firstname.getText().toString();
                String l = lastname.getText().toString();

                if (u.length() == 0) {
                    username.setError("Bitte geben Sie einen Benutzernamen ein!");
                } else if (e.length() == 0) {
                    email.setError("Bitte geben Sie eine E-Mail Adresse ein!");
                } else if (p.length() == 0) {
                    password.setError("Bitte geben Sie ein Passwort ein!");
                } else if (pc.length() > 0 && pn.length() == 0) {
                    password_c.setError("Bitte geben Sie ihr Passwort zweimal ein!!");
                } else if (pn.length() > 0 && pc.length() == 0) {
                    password_n.setError("Bitte geben Sie ihr Passwort zweimal ein!!");
                } else if (!pn.equals(pc)) {
                    password_c.setError("Ihre neuen Passwörter stimmen nicht überein!!");
                } else if (f.length() == 0) {
                    firstname.setError("Bitte geben Sie einen Vornamen ein!");
                } else if (l.length() == 0) {
                    lastname.setError("Bitte geben Sie einen Nachnamen ein!");
                } else {
                    String pw = "";
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("appkey", getString(R.string.appkeyweb));
                    params.put("benutzernameAlt", User.username);
                    params.put("benutzernameNeu", u);
                    if(pn.length() > 0){
                        pw = pn;
                    }else{
                        pw = p;
                    }
                    params.put("passwort", pw);
                    params.put("email", e);
                    params.put("vorname", f);
                    params.put("nachname", l);

                    User.username = u;
                    User.email = e;
                    User.firstname = f;
                    User.lastname = l;

                    Receiver receiver = new Receiver(editUserinstance);
                    receiver.editUser(params);
                }
            }
        });
    }
}
