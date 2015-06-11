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

                if(title.getText().toString().length() == 0){
                    title.setError("Bitte geben Sie einen Titel ein!");
                }else if(text.getText().toString().length() == 0){
                    text.setError("Bitte geben Sie einen Text ein!");
                }else{
                    HashMap<String , String> params = new HashMap<String , String>();
                    params.put("appkey", getString(R.string.appkeyweb));
                    params.put("autor", User.getInstance().username);
                    params.put("titel", title.getText().toString());
                    params.put("text", text.getText().toString());

                    Receiver receiver = new Receiver(infofragment);
                    receiver.sendNewInfo(params);
                }
            }
        });
    }
}
