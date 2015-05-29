package de.dhbw_loerrach.laju;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OfferListAdapter extends ArrayAdapter<OfferItem> {

    private ArrayList<OfferItem> data;
    Context context;
    int layoutResourceId;

    public OfferListAdapter(Context context, int layoutResourceId, ArrayList<OfferItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        OfferHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new OfferHolder();
            holder.title = (TextView) row.findViewById(R.id.offerListTitle);
            holder.erdat = (TextView) row.findViewById(R.id.offerListErdat);

            row.setTag(holder);
        } else {
            holder = (OfferHolder) row.getTag();
        }

        holder.title.setText(data.get(position).getTitle());
        holder.erdat.setText(data.get(position).getErdat());

        return row;
    }

    static class OfferHolder {
        TextView title;
        TextView erdat;
    }
}