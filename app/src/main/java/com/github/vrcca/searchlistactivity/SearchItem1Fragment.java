package com.github.vrcca.searchlistactivity;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchItem1Fragment extends ListFragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    List<String> mAllValues;
    private ArrayAdapter<String> mAdapter;
    private String mActivityPreviousTitle;
    private Context mContext;

    public static SearchItem1Fragment newInstance() {
        SearchItem1Fragment fragment = new SearchItem1Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

        setHasOptionsMenu(true);

        mAllValues = new ArrayList<>();
        mAllValues.add("Option 1");
        mAllValues.add("Option 2");
        mAllValues.add("Option 3");
        mAllValues.add("Option 4");
        mAllValues.add("None");


        mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mAllValues);
        setListAdapter(mAdapter);

        mActivityPreviousTitle = getActivity().getTitle().toString();
        getActivity().setTitle("Item 1");

    }

    @Override
    public void onListItemClick(ListView listView, View v, int position, long id) {
        String item = (String) listView.getAdapter().getItem(position);
        if (getActivity() instanceof OnItem1SelectedListener) {
            ((OnItem1SelectedListener) getActivity()).OnItem1SelectedListener(item);
        }
        getFragmentManager().popBackStack();
    }

    @Override
    public void onDetach() {
        getActivity().setTitle(mActivityPreviousTitle);
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.search_item1_fragment, container, false);
        ListView listView = (ListView) layout.findViewById(android.R.id.list);
        TextView emptyTextView = (TextView) layout.findViewById(android.R.id.empty);
        listView.setEmptyView(emptyTextView);
        return layout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_item_list_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        searchItem.setOnActionExpandListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }

        List<String> filteredValues = new ArrayList<String>(mAllValues);
        for (String value : mAllValues) {
            if (!value.toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }

        mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, filteredValues);
        setListAdapter(mAdapter);

        return false;
    }

    public void resetSearch() {
        mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mAllValues);
        setListAdapter(mAdapter);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        // TODO Limpar
        return true;
    }

    public interface OnItem1SelectedListener {
        void OnItem1SelectedListener(String item);
    }
}
