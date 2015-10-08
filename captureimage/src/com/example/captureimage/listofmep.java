package com.example.captureimage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class listofmep extends Activity {
	
	
	ListView list;
	ListcontentAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		list=(ListView)findViewById(R.id.listView1);
		
		adapter = new ListcontentAdapter(this, R.layout.list_row,
				MainActivity.imagedata);
		list.setAdapter(adapter);
		
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				Log.e("data","data:  " + MainActivity.imagedata.get(position)
				.getName());
				
//				
				
				MyDatabase db = new MyDatabase(listofmep.this);
				db.open();
				
				db.deleteitem(MainActivity.imagedata.get(position).getPid());
				db.close();
//				MainActivity.imagedata.remove(position);
//
//				adapter.notifyDataSetChanged();
//				onResume();
				return false;
			}
		});
		
	
	}

}
