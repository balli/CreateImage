package com.example.captureimage;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

	Button mCapture, mUpload_image, mSave_image, mShowlist;
	ImageView pic;

	private static final int CAPTURE_PICTURE = 1;
	private static final int PICK_IMAGE_REQUEST = 2;

	byte[] byteArray;

	EditText eName;
	public static ArrayList<imagebean> imagedata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mCapture = (Button) findViewById(R.id.button1);
		mUpload_image = (Button) findViewById(R.id.button2);
		mSave_image = (Button) findViewById(R.id.button3);
		pic = (ImageView) findViewById(R.id.imageView1);
		eName = (EditText) findViewById(R.id.editText1);
		mShowlist = (Button) findViewById(R.id.button4);

		mCapture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, CAPTURE_PICTURE);

			}
		});

		mUpload_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				// Show only images, no videos or anything else
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				// Always show the chooser (if there are multiple options
				// available)
				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"),
						PICK_IMAGE_REQUEST);

			}
		});

		mSave_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String imagebytecode = Base64.encodeToString(byteArray,
						Base64.NO_WRAP);
				String name = eName.getText().toString();

				MyDatabase db = new MyDatabase(getApplicationContext());
				db.open();
				db.insert(name, imagebytecode);
				db.close();

			}
		});

		mShowlist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyDatabase db = new MyDatabase(getApplicationContext());
				db.open();

				 imagedata = new ArrayList<imagebean>();

				try {
					Cursor c = db.shows();
					c.moveToFirst();
					Log.v("GET LIST size", "FROM DB ## " + c.getCount());
					if (c.getCount() != 0) {
						do {
							String name = c.getString(0);
							String Image_code = c.getString(1);
                            int Pass_id=c.getInt(2);
							Log.e(" Pass_id: ", "Pass_id" + Pass_id);
							// Log.e(" Image_code: ", "Image_code: " +
							// Image_code);

							imagebean bean = new imagebean();
							bean.setName(name);
							bean.setBytecode(Image_code);
							bean.setPid(Pass_id);

							imagedata.add(bean);

						} while (c.moveToNext());

						System.out.println("end");
						c.close();
						db.close();

					} else {
						Log.v("GET LIST size", "No list found");
					}

				} catch (SQLException e) {
					System.out.println("data not found");

				}
				
				
				Intent in=new Intent(MainActivity.this,listofmep.class);
				startActivity(in);
				
				

			}
		});

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == CAPTURE_PICTURE && resultCode == RESULT_OK
				&& data != null) {
			Bitmap bp = (Bitmap) data.getExtras().get("data");
			pic.setImageBitmap(bp);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bp.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byteArray = stream.toByteArray();

		}

		if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
				&& data != null) {

			Uri uri = data.getData();
			Bitmap bitmap;
			try {
				bitmap = MediaStore.Images.Media.getBitmap(
						getContentResolver(), uri);
				pic.setImageBitmap(bitmap);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byteArray = stream.toByteArray();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
