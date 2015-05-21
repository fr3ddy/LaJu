package de.dhbw_loerrach.laju;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
    }    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Toast.makeText(this, "ID: " + id, Toast.LENGTH_LONG).show();
        //noinspection SimplifiableIfStatement
        if (id == 0) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MainFragment extends Fragment {
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
        public static MainFragment newInstance(int position) {
            MainFragment fragment = new MainFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            fragment.setArguments(args);
            return fragment;
        }

        public MainFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mTabHost = (FragmentTabHost) rootView.findViewById(android.R.id.tabhost);
            mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

            mTabHost.addTab(mTabHost.newTabSpec("Infos").setIndicator("Infos"), InfoTabFragment.class, null);
            mTabHost.addTab(mTabHost.newTabSpec("Veranstaltungen").setIndicator("Veranstaltungen"), EventTabFragment.class, null);
            //mTabHost.setCurrentTab(getArguments().getInt("position"));

            mTabHost.getTabWidget().setBackgroundColor(getResources().getColor(R.color.bright_green));

            for(int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
                View v = mTabHost.getTabWidget().getChildAt(i);
                v.setBackgroundResource(R.drawable.tabs);

                TextView tv = (TextView)  mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                tv.setTextColor(getResources().getColor(R.color.grey));
            }
            
            //Receiver r = new Receiver();
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

    }

}
