package de.dhbw_loerrach.laju;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


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

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("appkey", "123456"));
                nameValuePairs.add(new BasicNameValuePair("autor", ((NewInfo)getActivity()).getBenutzername()));
                nameValuePairs.add(new BasicNameValuePair("titel", title.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("text", text.getText().toString()));

                Toast.makeText(getActivity(), title.getText() + "\n" + text.getText(), Toast.LENGTH_LONG).show();
                new Receiver(infofragment, "http://laju.frederik-frey.de/lajuapp/NeuigkeitEinreichen" , nameValuePairs);
            }
        });
    }
}
