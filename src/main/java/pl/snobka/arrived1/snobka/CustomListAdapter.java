package pl.snobka.arrived1.snobka;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Entry> {

    Context context;
    int layoutResourceId;
    List<Entry> data = null;
    String currentDate = null;


    public CustomListAdapter(Context context, int layoutResourceId, List<Entry> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.currentDate = setCurrentDate();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(layoutResourceId, parent, false);

        ImageView image = (ImageView)convertView.findViewById(R.id.imgIcon);
        TextView title = (TextView)convertView.findViewById(R.id.txtTitle);
        TextView summary = (TextView)convertView.findViewById(R.id.txtSummary);

        Entry entry = data.get(position);
        title.setText(entry.getTitle());

        if(isNewsFromToday(entry.getUpdated()))
            title.setTextColor(context.getResources().getColor(R.color.pink));

        summary.setText(Html.fromHtml(entry.getSummary()));
        image.setImageResource(R.drawable.ic_launcher);

        return convertView;
    }

    private String setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        String month = Integer.toString(calendar.get(Calendar.MONTH) + 1); // + 1 cos of moths starts from 0
        //TODO: what if its 1st day of moth:) 1 - 1 = 0, should be changed to last dey of prev month
        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH) - 1); // -1 cos of news are from yesterday always

        if(month.length() < 2)
            month = makeDateWith0(month);

        if(day.length() < 2)
            day = makeDateWith0(day);

        return year + "-" + month + "-" + day;
    }

    private String makeDateWith0(String date) {
        String newDate = "0" + date;
        return newDate;
    }

    private boolean isNewsFromToday(String newsDate) {
        return newsDate.contains(currentDate);
    }
}