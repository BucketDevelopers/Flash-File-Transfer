package com.bucketdevelopers.uft;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdaptor extends ArrayAdapter<String> {
	 private final Context context;
	 private final List<String> values;
	 private final ArrayList<Integer> selectedItems;

	public CustomAdaptor(Context context, List<String> objects, ArrayList<Integer> selected) {
		super(context, R.layout.list_text, objects);
		this.context = context;
	    this.values = objects ;
	    this.selectedItems = selected;
		// TODO Auto-generated constructor stub
	}
	
	 @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.list_text, parent, false);
	    TextView textView = (TextView) rowView.findViewById(R.id.tv_list);
	    textView.setText(values.get(position));
	    if(selectedItems.contains((Integer)position))
	    {
	    	textView.setTextColor(Color.BLUE);
	    }
	    return rowView;
	  }

}
