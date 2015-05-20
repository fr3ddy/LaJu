package de.dhbw_loerrach.laju;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class InfoListAdapter extends ArrayAdapter<InfoItem> {

    private ArrayList<InfoItem> data;
    Context context;
    int layoutResourceId;

    public InfoListAdapter(Context context, int layoutResourceId, ArrayList<InfoItem> data) {
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
            holder.newsTitle = (TextView)row.findViewById(R.id.newsTitle);
            holder.autor = (TextView)row.findViewById(R.id.autor);
            holder.erstelldatum = (TextView)row.findViewById(R.id.erdat);
            holder.text = (TextView)row.findViewById(R.id.textPreview);

            row.setTag(holder);
        }
        else
        {
            holder = (InfoHolder)row.getTag();
        }

        holder.newsTitle.setText(data.get(position).getTitel());
        holder.autor.setText(data.get(position).getAutor());
        holder.erstelldatum.setText(data.get(position).getErstelldatum());
        holder.text.setText(data.get(position).getTextpreview());

        return row;
    }

    static class InfoHolder{
        TextView newsTitle;
        TextView autor;
        TextView erstelldatum;
        TextView text;
    }
}