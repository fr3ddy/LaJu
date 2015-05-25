package de.dhbw_loerrach.laju;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class NewInfoFragment extends Fragment {

    private NewInfoFragment infofragment;

    public NewInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        infofragment = this;
        return inflater.inflate(R.layout.fragment_new_info, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn = (Button) view.findViewById(R.id.btnSendeInfo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title = (EditText) view.findViewById(R.id.newInfoTitle);
                EditText text = (EditText) view.findViewById(R.id.newInfoText);

                HashMap<String , String> params = new HashMap<String , String>();
                params.put("appkey", "123456");
                params.put("autor", ((NewInfo) getActivity()).getBenutzername());
                params.put("titel", title.getText().toString());
                params.put("text", text.getText().toString());

                Receiver receiver = new Receiver(infofragment);
                receiver.sendNewInfo("http://laju.frederik-frey.de/lajuapp/NeuigkeitEinreichen", params);
            }
        });
    }
}
