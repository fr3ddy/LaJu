package de.dhbw_loerrach.laju;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar toolbar;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        String title = "";
        if(item.getTitle() != null){
            title = item.getTitle().toString();
        }
        //noinspection SimplifiableIfStatement
        if (title.equals(getString(R.string.new_info))) {
            Intent intent = new Intent(this,NewInfo.class);
            intent.putExtra(getString(R.string.username) , "freyfr");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position){
            case 0:
                fragmentManager.beginTransaction().replace(R.id.container, MainFragment.newInstance(position)).commit();
                setTitle(R.string.infos);
                break;
            case 1:
                fragmentManager.beginTransaction().replace(R.id.container, MainFragment.newInstance(position)).commit();
                setTitle(R.string.events);
                break;
            case 2:
                fragmentManager.beginTransaction().replace(R.id.container, TauschboerseFragment.newInstance()).commit();
                setTitle(R.string.tauschboerse);
                break;
            case 3:
                fragmentManager.beginTransaction().replace(R.id.container, LoginFragment.newInstance()).commit();
                setTitle(R.string.login);
                break;
            default:
                setTitle(R.string.app_name);
                break;
        }
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
            mTabHost = (FragmentTabHost) rootView.findViewById(R.id.tabhost);
            mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

            mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.infos)).setIndicator(getString(R.string.infos)), InfoTabFragment.class, null);
            mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.events)).setIndicator(getString(R.string.events)), EventTabFragment.class, null);
            mTabHost.setCurrentTab(getArguments().getInt("position"));

            mTabHost.getTabWidget().setBackgroundColor(getResources().getColor(R.color.bright_green));

            for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
                View v = mTabHost.getTabWidget().getChildAt(i);
                v.setBackgroundResource(R.drawable.tabs);

                TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                tv.setTextColor(getResources().getColor(R.color.grey));
            }

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

    }

}
