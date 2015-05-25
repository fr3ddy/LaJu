package de.dhbw_loerrach.laju;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Frederik on 16.05.2015.
 */
public class LoginFragment extends Fragment {

    private static LoginFragment loginfragment;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        loginfragment = fragment;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText username = (EditText) view.findViewById(R.id.usernameLogin);
        final EditText password = (EditText) view.findViewById(R.id.passwordLogin);
        final Button loginbtn = (Button) view.findViewById(R.id.submitLogin);
        Button registerbtn = (Button) view.findViewById(R.id.registerButton);

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

                    Receiver receiver = new Receiver(loginfragment);
                    receiver.login(params);
                }
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Register will come soon...", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_addInfo);
        if (item != null) {
            item.setVisible(false);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        restoreActionBar();
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void restoreActionBar() {
        ActionBar actionBar = getActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.login);
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Receiver receiver = new Receiver(this, (ListView)getActivity().findViewById(R.id.infoTabList));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
