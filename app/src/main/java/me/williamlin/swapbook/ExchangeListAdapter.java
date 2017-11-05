package me.williamlin.swapbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by William Lin on 1/6/2017.
 */

public class ExchangeListAdapter extends ArrayAdapter<ExchangeEntry> {
    Context context;
    int layoutResourceId;
    ArrayList<ExchangeEntry> allList = null;

    public ExchangeListAdapter(Context context, ArrayList<ExchangeEntry> allList){
        super(context, 0, allList);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.allList = allList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        //Get the data item for this position
        final ExchangeEntry entry = allList.get(position);
        //Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exchange_list_item_layout, parent, false);
        }
        //Lookup view for data population
        TextView tvTitle = (TextView)convertView.findViewById(R.id.book_item_title);
        TextView tvIsbn = (TextView)convertView.findViewById(R.id.book_item_isbn);
        TextView tvSeller = (TextView)convertView.findViewById(R.id.book_item_seller);
        TextView tvDesc = (TextView)convertView.findViewById(R.id.book_item_desc);
        //Populate the date into the template view using the data object
        tvTitle.setText(entry.bookTitle);
        tvIsbn.setText(entry.isbn);
        tvSeller.setText(entry.seller);
        tvDesc.setText(entry.description);
        //Return the completed view to render on screen

        return convertView;
    }
}