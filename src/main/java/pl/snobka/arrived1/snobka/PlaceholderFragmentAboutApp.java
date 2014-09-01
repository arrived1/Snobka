package pl.snobka.arrived1.snobka;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragmentAboutApp extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragmentAboutApp newInstance(int sectionNumber) {
        PlaceholderFragmentAboutApp fragment = new PlaceholderFragmentAboutApp();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragmentAboutApp() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about_app, container, false);

        TextView text1 = (TextView)rootView.findViewById(R.id.about1);
        text1.setText(R.string.about1);

        TextView text2 = (TextView)rootView.findViewById(R.id.about2);
        text2.setText(R.string.about2);

        TextView text3 = (TextView)rootView.findViewById(R.id.about3);
        text3.setText(R.string.about3);


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MyActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}