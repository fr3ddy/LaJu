package de.dhbw_loerrach.laju;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Frederik on 16.05.2015.
 */
public class EventTabFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static EventTabFragment newInstance(int position) {
        EventTabFragment fragment = new EventTabFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public EventTabFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_eventtab, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Receiver receiver = new Receiver(this, (ListView)getActivity().findViewById(R.id.eventTabList));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
