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

/**
 * Created by Frederik on 24.05.2015.
 */
public class RegisterFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
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
        EditText username = (EditText) view.findViewById(R.id.registerUsername);
        EditText email = (EditText) view.findViewById(R.id.registerEmail);
        EditText email_c = (EditText) view.findViewById(R.id.registerEmailConfirm);
        EditText password = (EditText) view.findViewById(R.id.registerPassword);
        EditText password_c = (EditText) view.findViewById(R.id.registerPasswordConfirm);
        EditText firstname = (EditText) view.findViewById(R.id.registerFirstname);
        EditText lastname = (EditText) view.findViewById(R.id.registerLastname);
        Button registerbtn = (Button)view.findViewById(R.id.registerFragmentButton);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}
