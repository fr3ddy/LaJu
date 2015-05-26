package de.dhbw_loerrach.laju;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;


public class Login extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        final EditText username = (EditText) findViewById(R.id.usernameLogin);
        final EditText password = (EditText) findViewById(R.id.passwordLogin);
        final Button loginbtn = (Button) findViewById(R.id.submitLogin);
        Button registerbtn = (Button) findViewById(R.id.registerButton);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = username.getText().toString();
                String p = password.getText().toString();

                if(u.length() == 0){
                    username.setError("Bitte geben Sie einen Benutzernamen ein!");
                }else if(p.length() == 0){
                    password.setError("Bitte geben Sie eine Passwort ein!");
                }else{
                    HashMap<String , String> params = new HashMap<String , String>();
                    params.put("appkey", "123456");
                    params.put("benutzername", u);
                    params.put("passwort", p);

//                    Receiver receiver = new Receiver();
//                    receiver.login(params);
                }
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Register.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_addInfo);
        if (item != null) {
            item.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_info, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
