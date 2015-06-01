package de.dhbw_loerrach.laju;

import android.app.MediaRouteButton;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewExchangeFragment extends Fragment {

    private NewExchangeFragment newExchangeFragment;

    public NewExchangeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        newExchangeFragment = this;
        return inflater.inflate(R.layout.fragment_new_exchange, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Spinner type = (Spinner) view.findViewById(R.id.newExchangeType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.exchange_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);

        final EditText title = (EditText) view.findViewById(R.id.newExchangeTitle);
        final EditText text = (EditText) view.findViewById(R.id.newExchangeText);
        Button btn = (Button) view.findViewById(R.id.addExchangeButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString().length() == 0) {
                    title.setError("Bitte geben Sie einen Titel ein!");
                } else if (text.getText().toString().length() == 0) {
                    text.setError("Bitte geben Sie einen Text ein!");
                } else{
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("appkey", "123456");
                    params.put("titel", title.getText().toString());
                    params.put("besch", text.getText().toString());
                    params.put("user", User.getInstance().username);
                    params.put("art", type.getSelectedItem().toString());

                    Receiver receiver = new Receiver(newExchangeFragment);
                    receiver.sendNewExchange(params);
                }
            }
        });
    }

}
