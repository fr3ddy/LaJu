package de.dhbw_loerrach.laju;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ExchangeListAdapter extends ArrayAdapter<ExchangeItem> {

    private ArrayList<ExchangeItem> data;
    Context context;
    int layoutResourceId;

    public ExchangeListAdapter(Context context, int layoutResourceId, ArrayList<ExchangeItem> data) {
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

        String title;
        if(data.get(position).isOpen()) {
            title = data.get(position).getTitle();
        } else {
            title = "[Geschlossen] " + data.get(position).getTitle();
        }
        holder.title.setText(title);
        holder.erdat.setText(data.get(position).getErdat());

        return row;
    }

    static class OfferHolder {
        TextView title;
        TextView erdat;
    }
}