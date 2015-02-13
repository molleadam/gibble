package com.example.gibble.util;

import java.util.List;

import com.easy.facebook.android.data.Checkin;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlaceAdapter extends ArrayAdapter<Checkin>{

	int layout;
	int textViewName;
	int textViewCategory;
	
	public PlaceAdapter(Context context,int layout,List<Checkin> list, int textViewName,int textViewCategory){
		super(context,layout,list);
		this.layout = layout;
		this.textViewCategory = textViewCategory;
		this.textViewName = textViewName;
	}
	
	@Override
	
	public View getView(int position, View convertView, ViewGroup parent){
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(layout, null);
	        TextView nome = (TextView)convertView.findViewById(textViewName);
	        TextView category = (TextView)convertView.findViewById(textViewCategory);
	        Checkin c = getItem(position);
	        nome.setText(c.getName());
	        category.setText(c.getCategory());
	        return convertView;

	}
}
