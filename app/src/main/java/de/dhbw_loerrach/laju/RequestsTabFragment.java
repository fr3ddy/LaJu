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
public class RequestsTabFragment extends Fragment {
    public CustomSwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;

    public static RequestsTabFragment newInstance() {
        RequestsTabFragment fragment = new RequestsTabFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public RequestsTabFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_requeststab, container, false);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        restoreActionBar();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RequestsTabFragment tmpfrag = this;

        mListView = (ListView) getActivity().findViewById(R.id.requestTabList);

        mSwipeRefreshLayout = (CustomSwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Receiver receiver = new Receiver(tmpfrag);
                receiver.clearQueue();
                receiver.fillRequests();
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
            item.setVisible(false);
        }
        MenuItem item2 = menu.findItem(R.id.action_addExchange);
        if(item2 != null && User.isLoggedIn()){
            item2.setVisible(true);
        }
        super.onPrepareOptionsMenu(menu);
    }

    private void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setTitle(R.string.requests);
        actionBar.setTitle(R.string.tauschboerse);
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Receiver receiver = new Receiver(this);
        receiver.clearQueue();
        receiver.fillRequests();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
