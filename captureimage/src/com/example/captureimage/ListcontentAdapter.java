package com.example.captureimage;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListcontentAdapter extends ArrayAdapter<imagebean> {

	private Context context;
	private int resId;
	public static ArrayList<imagebean> list_data;


	public ListcontentAdapter(Context context, int textViewResourceId,
			ArrayList<imagebean> data) {
		super(context, textViewResourceId, data);
		// TODO Auto-generated constructor stub

		this.context = context;
		this.resId = textViewResourceId;
		this.list_data = data;

	}

	private class ViewHolder {
		TextView name;
		ImageView image;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {

			convertView = mInflater.inflate(resId, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.textView123);
			
			holder.image = (ImageView) convertView
					.findViewById(R.id.imageView123);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		
		holder.name.setText(list_data.get(position).getName());

	    byte [] sample_array = Base64.decode(list_data.get(position).getBytecode(), Base64.DEFAULT);
      
        Bitmap bitmap=BitmapFactory.decodeByteArray(sample_array, 0, sample_array.length);

		holder.image.setImageBitmap(bitmap);
		
		return convertView;

	}


	@Override
	public int getCount() {
		return list_data.size();
	}

	

}