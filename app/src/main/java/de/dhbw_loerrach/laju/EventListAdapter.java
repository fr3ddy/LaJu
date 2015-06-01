package de.dhbw_loerrach.laju;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class EventListAdapter extends ArrayAdapter<EventItem> {

    private ArrayList<EventItem> data;
    private Context context;
    private int layoutResourceId;

    public EventListAdapter(Context context, int layoutResourceId, ArrayList<EventItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        InfoHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new InfoHolder();
            holder.eventTitel = (TextView)row.findViewById(R.id.eventTitel);
            holder.eventUntertitel = (TextView)row.findViewById(R.id.eventUntertitel);
            holder.eventStart = (TextView)row.findViewById(R.id.eventStart);
            new DownloadImageTask((ImageView) row.findViewById(R.id.eventItemImage)).execute(data.get(position).getBild());

            row.setTag(holder);
        }
        else
        {
            holder = (InfoHolder)row.getTag();
        }

        holder.eventTitel.setText(data.get(position).getTitel());
        holder.eventUntertitel.setText(data.get(position).getUntertitel().equals("{}") ? "" : data.get(position).getUntertitel());
        holder.eventStart.setText(data.get(position).getDatum_von());

        return row;
    }

    static class InfoHolder{
        TextView eventTitel;
        TextView eventUntertitel;
        TextView eventStart;
    }
}