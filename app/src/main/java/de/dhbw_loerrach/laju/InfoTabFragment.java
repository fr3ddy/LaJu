package de.dhbw_loerrach.laju;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Frederik on 16.05.2015.
 */
public class InfoTabFragment extends Fragment {
    public CustomSwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;

    public static InfoTabFragment newInstance() {
        InfoTabFragment fragment = new InfoTabFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public InfoTabFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_infotab, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final InfoTabFragment tmpfrag = this;

        mListView = (ListView) getActivity().findViewById(R.id.infoTabList);

        mSwipeRefreshLayout = (CustomSwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Receiver receiver = new Receiver(tmpfrag);
                receiver.fillInfos();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.bright_green, R.color.dark_green);

        mSwipeRefreshLayout.setOnChildScrollUpListener(new CustomSwipeRefreshLayout.OnChildScrollUpListener() {
            @Override
            public boolean canChildScrollUp() {
                return mListView.getFirstVisiblePosition() > 0 ||
                        mListView.getChildAt(0) == null ||
                        mListView.getChildAt(0).getTop() < 0;
            }
        });;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_addInfo);
        if(item != null) {
            if(User.getInstance() != null) {
                item.setVisible(true);
            } else {
                item.setVisible(false);
            }
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        restoreActionBar();
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void restoreActionBar() {
        ActionBar actionBar = getActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.infos);
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Receiver receiver = new Receiver(this);
        receiver.fillInfos();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
