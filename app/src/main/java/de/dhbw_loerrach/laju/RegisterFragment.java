package de.dhbw_loerrach.laju;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Frederik on 24.05.2015.
 */
public class RegisterFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static RegisterFragment registerfragment;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        registerfragment = fragment;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText username = (EditText) view.findViewById(R.id.registerUsername);
        final EditText email = (EditText) view.findViewById(R.id.registerEmail);
        final EditText email_c = (EditText) view.findViewById(R.id.registerEmailConfirm);
        final EditText password = (EditText) view.findViewById(R.id.registerPassword);
        final EditText password_c = (EditText) view.findViewById(R.id.registerPasswordConfirm);
        final EditText firstname = (EditText) view.findViewById(R.id.registerFirstname);
        final EditText lastname = (EditText) view.findViewById(R.id.registerLastname);
        Button registerbtn = (Button)view.findViewById(R.id.registerFragmentButton);

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

                if(u.length() == 0){
                    username.setError("Bitte geben Sie einen Benutzernamen ein!");
                }else if(e.length() == 0){
                    email.setError("Bitte geben Sie eine E-Mail Adresse ein!");
                }else if(ec.length() == 0){
                    email_c.setError("Bitte bestätigen Sie Ihre E-Mail Adresse!");
                }else if(p.length() == 0){
                    password.setError("Bitte geben Sie ein Passwort ein!");
                }else if(pc.length() == 0){
                    password_c.setError("Bitte bestätigen Sie Ihre Passwort!");
                }else if(f.length() == 0){
                    firstname.setError("Bitte geben Sie einen Vornamen ein!");
                }else if(l.length() == 0){
                    lastname.setError("Bitte geben Sie einen Nachnamen ein!");
                }else if(!e.equals(ec)){
                    email_c.setError("Ihre E-Mail Adressen stimmen nicht überein!");
                }else if(!p.equals(pc)){
                    password_c.setError("Ihre Passwörter stimmen nicht überein!");
                }else{
                    HashMap<String , String> params = new HashMap<String , String>();
                    params.put("appkey", "123456");
                    params.put("benutzername", u);
                    params.put("passwort", p);
                    params.put("email" , e);
                    params.put("vorname" , f);
                    params.put("nachname", l);
                    Receiver receiver = new Receiver(registerfragment);
                    receiver.register(params);
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}
