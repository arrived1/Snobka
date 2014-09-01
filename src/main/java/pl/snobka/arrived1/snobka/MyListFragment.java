package pl.snobka.arrived1.snobka;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class MyListFragment extends ListFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private DBHandler dbHandler = null;
    List<Entry> entryList = null;

    public static MyListFragment newInstance(int sectionNumber) {
        MyListFragment fragment = new MyListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MyListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RateUs.app_launched(getActivity());
//        RateUs.showRateDialog(getActivity(), null); //test purposes

        dbHandler = new DBHandler(getActivity());
        entryList = dbHandler.getAllRecords();


        CustomListAdapter adapter = new CustomListAdapter(getActivity(),
                R.layout.listview_item_row, entryList);

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // do something with the data
        Intent intent = new Intent(l.getContext(), ArticleActivity.class);
        //Log.d("DUPA id wkladam: ", Long.toString(id));
        intent.putExtra("ID", id + 1);
        startActivity(intent);
    }
}