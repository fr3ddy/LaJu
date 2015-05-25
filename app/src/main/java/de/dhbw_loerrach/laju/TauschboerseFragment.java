package de.dhbw_loerrach.laju;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Frederik on 24.05.2015.
 */
public class TauschboerseFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private FragmentTabHost mTabHost;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TauschboerseFragment newInstance() {
        TauschboerseFragment fragment = new TauschboerseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public TauschboerseFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tauschboerse, container, false);
        mTabHost = (FragmentTabHost) rootView.findViewById(R.id.tabhosttausch);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontenttausch);

        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.offers)).setIndicator(getString(R.string.offers)), OffersTabFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.requests)).setIndicator(getString(R.string.requests)), RequestsTabFragment.class, null);

        mTabHost.getTabWidget().setBackgroundColor(getResources().getColor(R.color.bright_green));

        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            v.setBackgroundResource(R.drawable.tabs);

            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(getResources().getColor(android.R.color.white));
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}
