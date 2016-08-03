package com.studentchef.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

@SuppressLint("NewApi")
public class YogiUtils {

	private static final String TAG = "YogiUtils";
	static Activity context;
	private String path1;
	private String str_song_name;
	DialogListener dialogListener;

	private static final Uri sArtworkUri = Uri
			.parse("content://media/external/audio/albumart");

	public YogiUtils(Activity context) {
		super();
		this.context = context;
	}

	public YogiUtils(Activity context, DialogListener dialogListener) {
		super();
		this.context = context;
		this.dialogListener = dialogListener;
	}

	public interface DialogListener {

		public void OnCancle();

		public void onOk(int position, String string, String string2);

		public void onOk();
	}

	// public ArrayList<SongItems> getRawSongList() {
	//
	// ArrayList<SongItems> arrayList = new ArrayList<SongItems>();
	// try {
	// Field[] fields = R.raw.class.getFields();
	// for (int i = 0; i < fields.length; i++) {
	//
	// int resourceID = fields[i].getInt(fields[i]);
	//
	// Log.e(TAG, " raw " + R.raw.carefree + "==" + resourceID);
	//
	// String path = "android.resource://" + context.getPackageName()
	// + "/" + resourceID;
	//
	// Uri mediaPath = Uri.parse(path);
	// MediaMetadataRetriever mmr = new MediaMetadataRetriever();
	// mmr.setDataSource(context, mediaPath);
	//
	// String title = mmr
	// .extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
	// String artistname = mmr
	// .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
	//
	// String albumtitle = mmr
	// .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
	//
	// String songdur = mmr
	// .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
	//
	// try {
	// path1 = Environment.getExternalStorageDirectory()
	// + "/PriyankaChopra";
	// File dir = new File(path1);
	// if (dir.mkdirs() || dir.isDirectory()) {
	//
	// if (title == null) {
	// str_song_name = fields[i] + "" + i + ".mp3";
	// } else {
	// str_song_name = title + "" + i + ".mp3";
	// }
	//
	// CopyRAWtoSDCard(resourceID, path1 + File.separator
	// + str_song_name);
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// String realpath = path1 + "/" + str_song_name;
	//
	// SongItems songItems = new SongItems();
	//
	// if (title == null) {
	// songItems.setTitle(fields[i].getName());
	// } else {
	// songItems.setTitle(title);
	// }
	//
	// songItems.setArtist(artistname);
	// songItems.setDuration(songdur);
	// songItems.setAlbumname(albumtitle);
	// songItems.setPath(realpath);
	//
	// arrayList.add(songItems);
	//
	// Log.e(TAG, " title== " + title + " artistname== " + artistname
	// + " albumtitle== " + albumtitle + " songdur== "
	// + songdur);
	//
	// }
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalArgumentException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return arrayList;
	//
	// }

	public static String getDurationBreakdown(long millis) {
		if (millis < 0) {
			throw new IllegalArgumentException(
					"Duration must be greater than zero!");
		}

		long days = TimeUnit.MILLISECONDS.toDays(millis);
		millis -= TimeUnit.DAYS.toMillis(days);
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		millis -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

		// StringBuilder sb = new StringBuilder(64);
		// sb.append(days);
		// sb.append(" Days ");
		// sb.append(hours);
		// sb.append(" Hours ");
		// sb.append(minutes);
		// sb.append(" Minutes ");
		// sb.append(seconds);
		// sb.append(" Seconds");

		StringBuilder sb = new StringBuilder(64);

		// sb.append(String.format("%02d", hours));
		// sb.append(":");
		sb.append(String.format("%02d", minutes));
		sb.append(":");
		sb.append(String.format("%02d", seconds));

		return (sb.toString());
	}

	private void CopyRAWtoSDCard(int id, String path) throws IOException {
		InputStream in = context.getResources().openRawResource(id);
		FileOutputStream out = new FileOutputStream(path);
		byte[] buff = new byte[1024];
		int read = 0;

		Log.e(TAG, path);

		try {
			while ((read = in.read(buff)) > 0) {
				out.write(buff, 0, read);
			}
		} finally {
			Log.e(TAG, "finaly");
			in.close();
			out.close();
		}
	}

	@SuppressLint("InlinedApi")
	public void setStatusBarColor(View statusBar, int color) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
				&& Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			Window w = context.getWindow();
			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// status bar height
			int actionBarHeight = getActionBarHeight();
			int statusBarHeight = getStatusBarHeight();
			// action bar height
			statusBar.getLayoutParams().height = statusBarHeight;
			statusBar.setBackgroundColor(color);
		} else {
//			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//				Window window = context.getWindow();
//				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//				window.setStatusBarColor(color);
//			}
		}
	}

	public int getActionBarHeight() {
		int actionBarHeight = 0;
		TypedValue tv = new TypedValue();
		if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize,
				tv, true)) {
			actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
					context.getResources().getDisplayMetrics());
		}
		return actionBarHeight;
	}

	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	// public void CreateDialog(String title, String Message, final int
	// position) {
	// AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
	// context);
	// alertDialogBuilder.setMessage(Message);
	// alertDialogBuilder.setTitle(title);
	// alertDialogBuilder.setPositiveButton("Ok",
	// new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface arg0, int arg1) {
	// // Toast.makeText(context, "You clicked yes button",
	// // Toast.LENGTH_LONG).show();
	// Log.e(TAG, "ok out side");
	//
	// Uri uri = Uri.parse(ContentProviderPlayList.CONTENT_URI
	// + "/" + Integer.toString(position));
	// context.getContentResolver().delete(uri, null, null);
	//
	// String where = SongListDB.KEY_PLAYTLIST_ID + "=?";
	//
	// String whereVal[] = { Integer.toString(position) };
	//
	// context.getContentResolver().delete(
	// ContentProviderSongList.CONTENT_URI, where,
	// whereVal);
	//
	// // if (context
	// // .getClass()
	// // .getSimpleName()
	// // .equals(new AddMusicSwipeMenu().getClass()
	// // .getSimpleName())) {
	// // Log.e(TAG, "ok inside");
	// dialogListener.onOk();
	// // }
	// // if (context
	// // .getClass()
	// // .getSimpleName()
	// // .equals(new RingdroidEditActivity().getClass()
	// // .getSimpleName())) {
	// // Log.e(TAG, "ok inside");
	// // dialogListener.onOk(position, "", "delete");
	// // }
	// }
	//
	// });
	//
	// alertDialogBuilder.setNegativeButton("Cancel",
	// new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	//
	// // if (context
	// // .getClass()
	// // .getSimpleName()
	// // .equals(new AddMusicSwipeMenu().getClass()
	// // .getSimpleName())) {
	// // dialogListener.OnCancle();
	// // }
	//
	// }
	// });
	//
	// AlertDialog alertDialog = alertDialogBuilder.create();
	// alertDialog.show();
	//
	// }

	// public static void shareDialog(final int pos) {
	//
	// // get prompts.xml view
	// LayoutInflater layoutInflater = LayoutInflater.from(context);
	// View promptView = layoutInflater.inflate(R.layout.dialog_share, null);
	// AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
	// context);
	//
	// ImageView img_cancle = (ImageView) promptView
	// .findViewById(R.id.imageView3);
	//
	// alertDialogBuilder.setView(promptView);
	//
	// // setup a dialog window
	// // alertDialogBuilder
	// // .setCancelable(false)
	// // .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	// // public void onClick(DialogInterface dialog, int id) {
	// // ContentValues values = new ContentValues();
	// // values.put(PlayListDB.KEY_NAME, editText.getText()
	// // .toString());
	// // values.put(PlayListDB.KEY_NO_OF_SONGS, "0");
	// //
	// // // insert a record
	// //
	// // context.getContentResolver().insert(
	// // ContentProviderPlayList.CONTENT_URI, values);
	// //
	// // dialogListener.onOk();
	// // // if (context
	// // // .getClass()
	// // // .getSimpleName()
	// // // .equals(new AddMusicSwipeMenu().getClass()
	// // // .getSimpleName())) {
	// // //
	// // // if (editText.getText().toString().equals("")) {
	// // // Toast.makeText(context, "Please enter title!",
	// // // Toast.LENGTH_SHORT).show();
	// // // } else {
	// // //
	// // // dialogListener.onOk(pos, editText.getText()
	// // // .toString(), "edit title");
	// // // }
	// // // }
	// // }
	// // })
	// // .setNegativeButton("Cancel",
	// // new DialogInterface.OnClickListener() {
	// // public void onClick(DialogInterface dialog, int id) {
	// // dialog.cancel();
	// // }
	// // });
	//
	// // create an alert dialog
	// final AlertDialog alert = alertDialogBuilder.create();
	// alert.show();
	// img_cancle.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// alert.dismiss();
	// }
	// });
	// }

	// public static void showInputDialog(final int pos) {
	//
	// // get prompts.xml view
	// try {
	// LayoutInflater layoutInflater = LayoutInflater.from(context);
	// View promptView = layoutInflater.inflate(R.layout.input_dialog,
	// null);
	// AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
	// context);
	// Button btn_takeout = (Button) promptView.findViewById(R.id.button1);
	// Button btn_order_at_table = (Button) promptView
	// .findViewById(R.id.button2);
	// ImageView img_cancle = (ImageView) promptView
	// .findViewById(R.id.imageView3);
	//
	// alertDialogBuilder.setView(promptView);
	//
	// // setup a dialog window
	// // alertDialogBuilder
	// // .setCancelable(false)
	// // .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	// // public void onClick(DialogInterface dialog, int id) {
	// // ContentValues values = new ContentValues();
	// // values.put(PlayListDB.KEY_NAME, editText.getText()
	// // .toString());
	// // values.put(PlayListDB.KEY_NO_OF_SONGS, "0");
	// //
	// // // insert a record
	// //
	// // context.getContentResolver().insert(
	// // ContentProviderPlayList.CONTENT_URI, values);
	// //
	// // dialogListener.onOk();
	// // // if (context
	// // // .getClass()
	// // // .getSimpleName()
	// // // .equals(new AddMusicSwipeMenu().getClass()
	// // // .getSimpleName())) {
	// // //
	// // // if (editText.getText().toString().equals("")) {
	// // // Toast.makeText(context, "Please enter title!",
	// // // Toast.LENGTH_SHORT).show();
	// // // } else {
	// // //
	// // // dialogListener.onOk(pos, editText.getText()
	// // // .toString(), "edit title");
	// // // }
	// // // }
	// // }
	// // })
	// // .setNegativeButton("Cancel",
	// // new DialogInterface.OnClickListener() {
	// // public void onClick(DialogInterface dialog, int id) {
	// // dialog.cancel();
	// // }
	// // });
	//
	// // create an alert dialog
	// final AlertDialog alert = alertDialogBuilder.create();
	// alert.show();
	//
	// img_cancle.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// alert.dismiss();
	// }
	// });
	//
	// btn_takeout.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	//
	// showInputDialog1(0);
	// alert.dismiss();
	// }
	// });
	//
	// btn_order_at_table.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// showInputDialog1(0);
	// alert.dismiss();
	// }
	// });
	//
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	// public static void showInputDialog1(final int pos) {
	//
	// // get prompts.xml view
	// try {
	// LayoutInflater layoutInflater = LayoutInflater.from(context);
	// View promptView = layoutInflater.inflate(R.layout.input_dialog1,
	// null);
	// AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
	// context);
	// // Button btn_takeout = (Button)
	// // promptView.findViewById(R.id.button1);
	// Button btn_order_at_table = (Button) promptView
	// .findViewById(R.id.button2);
	// ImageView img_cancle = (ImageView) promptView
	// .findViewById(R.id.imageView3);
	// //
	// // btn_takeout.setOnClickListener(new OnClickListener() {
	// //
	// // @Override
	// // public void onClick(View v) {
	// // // TODO Auto-generated method stub
	// //
	// // }
	// // });
	//
	// alertDialogBuilder.setView(promptView);
	//
	// // setup a dialog window
	// // alertDialogBuilder
	// // .setCancelable(false)
	// // .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	// // public void onClick(DialogInterface dialog, int id) {
	// // ContentValues values = new ContentValues();
	// // values.put(PlayListDB.KEY_NAME, editText.getText()
	// // .toString());
	// // values.put(PlayListDB.KEY_NO_OF_SONGS, "0");
	// //
	// // // insert a record
	// //
	// // context.getContentResolver().insert(
	// // ContentProviderPlayList.CONTENT_URI, values);
	// //
	// // dialogListener.onOk();
	// // // if (context
	// // // .getClass()
	// // // .getSimpleName()
	// // // .equals(new AddMusicSwipeMenu().getClass()
	// // // .getSimpleName())) {
	// // //
	// // // if (editText.getText().toString().equals("")) {
	// // // Toast.makeText(context, "Please enter title!",
	// // // Toast.LENGTH_SHORT).show();
	// // // } else {
	// // //
	// // // dialogListener.onOk(pos, editText.getText()
	// // // .toString(), "edit title");
	// // // }
	// // // }
	// // }
	// // })
	// // .setNegativeButton("Cancel",
	// // new DialogInterface.OnClickListener() {
	// // public void onClick(DialogInterface dialog, int id) {
	// // dialog.cancel();
	// // }
	// // });
	//
	// // create an alert dialog
	// final AlertDialog alert = alertDialogBuilder.create();
	// alert.show();
	// btn_order_at_table.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// Toast.makeText(context, "Submitted", Toast.LENGTH_SHORT)
	// .show();
	// alert.dismiss();
	// }
	// });
	//
	// img_cancle.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// alert.dismiss();
	// }
	// });
	//
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	// public static void showInputDialogAddNote(final int pos) {
	//
	// // get prompts.xml view
	// try {
	// LayoutInflater layoutInflater = LayoutInflater.from(context);
	// View promptView = layoutInflater.inflate(
	// R.layout.inputdialog_addnote, null);
	// AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
	// context);
	// // Button btn_takeout = (Button)
	// // promptView.findViewById(R.id.button1);
	// Button btn_order_at_table = (Button) promptView
	// .findViewById(R.id.button2);
	// ImageView img_cancle = (ImageView) promptView
	// .findViewById(R.id.imageView3);
	// //
	// // btn_takeout.setOnClickListener(new OnClickListener() {
	// //
	// // @Override
	// // public void onClick(View v) {
	// // // TODO Auto-generated method stub
	// //
	// // }
	// // });
	//
	// alertDialogBuilder.setView(promptView);
	//
	// // setup a dialog window
	// // alertDialogBuilder
	// // .setCancelable(false)
	// // .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	// // public void onClick(DialogInterface dialog, int id) {
	// // ContentValues values = new ContentValues();
	// // values.put(PlayListDB.KEY_NAME, editText.getText()
	// // .toString());
	// // values.put(PlayListDB.KEY_NO_OF_SONGS, "0");
	// //
	// // // insert a record
	// //
	// // context.getContentResolver().insert(
	// // ContentProviderPlayList.CONTENT_URI, values);
	// //
	// // dialogListener.onOk();
	// // // if (context
	// // // .getClass()
	// // // .getSimpleName()
	// // // .equals(new AddMusicSwipeMenu().getClass()
	// // // .getSimpleName())) {
	// // //
	// // // if (editText.getText().toString().equals("")) {
	// // // Toast.makeText(context, "Please enter title!",
	// // // Toast.LENGTH_SHORT).show();
	// // // } else {
	// // //
	// // // dialogListener.onOk(pos, editText.getText()
	// // // .toString(), "edit title");
	// // // }
	// // // }
	// // }
	// // })
	// // .setNegativeButton("Cancel",
	// // new DialogInterface.OnClickListener() {
	// // public void onClick(DialogInterface dialog, int id) {
	// // dialog.cancel();
	// // }
	// // });
	//
	// // create an alert dialog
	// final AlertDialog alert = alertDialogBuilder.create();
	// alert.show();
	// btn_order_at_table.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// Toast.makeText(context, "Submitted", Toast.LENGTH_SHORT)
	// .show();
	// alert.dismiss();
	// }
	// });
	//
	// img_cancle.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// alert.dismiss();
	// }
	// });
	//
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	//
	// public ArrayList<SongItems> getSongList() {
	// // retrieve song info
	// ArrayList<SongItems> sonArrayList = new ArrayList<SongItems>();
	//
	// String[] projection = new String[] { MediaStore.Audio.Media._ID,
	// MediaStore.Audio.Media.DISPLAY_NAME,
	// MediaStore.Audio.Media.ARTIST, MediaStore.Images.Media.DATA,
	// MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ALBUM
	//
	// };
	// ContentResolver musicResolver = context.getContentResolver();
	// Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	//
	// Cursor musicCursor = musicResolver.query(musicUri, projection, null,
	// null, null);
	// // Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	// // Cursor cur = musicResolver.query(images, projection, null, null,
	// // null);
	//
	// if (musicCursor != null && musicCursor.moveToFirst()) {
	// // get columns
	// int titleColumn = musicCursor
	// .getColumnIndex(android.provider.MediaStore.Audio.Media.DISPLAY_NAME);
	// int idColumn = musicCursor
	// .getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
	// int artistColumn = musicCursor
	// .getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);
	//
	// int dateColumn = musicCursor
	// .getColumnIndex(android.provider.MediaStore.Images.Media.DATA);
	// int duration = musicCursor
	// .getColumnIndex(android.provider.MediaStore.Audio.Media.DURATION);
	// int album = musicCursor
	// .getColumnIndex(android.provider.MediaStore.Audio.Media.ALBUM);
	//
	// do {
	// long thisId = musicCursor.getLong(idColumn);
	// String thisTitle = musicCursor.getString(titleColumn);
	// String thisArtist = musicCursor.getString(artistColumn);
	// String date = musicCursor.getString(dateColumn);
	// // String img = musicCursor.getString(imgColumn);
	//
	// String thisduration = musicCursor.getString(duration);
	// String albumname = musicCursor.getString(album);
	// SongItems songItems = null;
	// songItems.setTitle(thisTitle);
	// songItems.setAlbumname(albumname);
	// songItems.setDuration(thisduration);
	// songItems.setArtist(thisArtist);
	// songItems.setPath(date);
	// sonArrayList.add(songItems);
	// try {
	// Log.e(TAG,
	// "dur::"
	// + YogiUtils.getDurationBreakdown(Long
	// .parseLong(thisduration)));
	// songItems = new SongItems();
	// } catch (NumberFormatException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// // songList.add(new Song(thisId, thisTitle, thisArtist, date));
	// } while (musicCursor.moveToNext());
	//
	// }
	// return sonArrayList;
	// }

	public void SendMail() {
		String mailTo[] = new String[] { "cqlsys@gmail.com" };
		Intent i = new Intent(Intent.ACTION_SENDTO);
		i.setData(Uri.parse("mailto:"));
		i.putExtra(Intent.EXTRA_EMAIL, mailTo);
		i.putExtra(Intent.EXTRA_SUBJECT, "My Query is");
		i.putExtra(Intent.EXTRA_TEXT, "Here you go....");
		try {
			context.startActivity(Intent.createChooser(i, "Send mail:"));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(context, "No Client installed!", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public static String toTitleCase(String givenString) {
		String[] arr = givenString.split(" ");
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < arr.length; i++) {
			sb.append(Character.toUpperCase(arr[i].charAt(0)))
					.append(arr[i].substring(1)).append(" ");
		}
		return sb.toString().trim();
	}

	// public void intToolbar(Toolbar toolbar) {
	// TextView title = (TextView) toolbar.findViewById(R.id.title);
	// title.setText("Help");
	// context.setSupportActionBar(toolbar);
	// context.getSupportActionBar().setTitle("");
	// getSupportActionBar().setDisplayShowHomeEnabled(true);
	// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	// yogiUtils = new YogiUtils(this);
	// yogiUtils.setStatusBarColor(findViewById(R.id.status_bar),
	// getResources().getColor(R.color.status_color));
	// }

	// @SuppressWarnings("deprecation")
	// public ArrayList<AlbumItem> getAlbumList() {
	//
	// ArrayList<AlbumItem> arrayList = new ArrayList<AlbumItem>();
	//
	// String albumname = "";
	// String[] columns = { android.provider.MediaStore.Audio.Albums._ID,
	// android.provider.MediaStore.Audio.Albums.ALBUM };
	//
	// Cursor cursor = context.managedQuery(
	// MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, columns, null,
	// null, null);
	//
	// if (cursor.moveToFirst()) {
	// do {
	//
	// AlbumItem albumItem = new AlbumItem();
	//
	// Log.e("Vipul",
	// cursor.getString(cursor
	// .getColumnIndex(android.provider.MediaStore.Audio.Albums.ALBUM)));
	//
	// albumname = cursor
	// .getString(cursor
	// .getColumnIndex(android.provider.MediaStore.Audio.Albums.ALBUM));
	//
	// albumItem.setAlbumname(albumname);
	//
	// String[] column = { MediaStore.Audio.Media.DATA,
	// MediaStore.Audio.Media._ID,
	// MediaStore.Audio.Media.TITLE,
	// MediaStore.Audio.Media.DISPLAY_NAME,
	// MediaStore.Audio.Media.MIME_TYPE, };
	//
	// String where = android.provider.MediaStore.Audio.Media.ALBUM
	// + "=?";
	//
	// String whereVal[] = { albumname };
	//
	// String orderBy = android.provider.MediaStore.Audio.Media.TITLE;
	//
	// Cursor cursor1 = context.managedQuery(
	// MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, column,
	// where, whereVal, orderBy);
	//
	// if (cursor1.moveToFirst()) {
	//
	// ArrayList<SongItems> songList = new ArrayList<>();
	//
	// do {
	//
	// Log.e("Vipul song== ",
	// cursor1.getString(cursor1
	// .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
	//
	// String songname = "";
	// String alname="";
	// String duration="";
	// String artist="";
	// String date="";
	// try {
	// songname = cursor1
	// .getString(cursor1
	// .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
	// alname = cursor1.getString(cursor1
	// .getColumnIndex(MediaStore.Audio.Media.ALBUM));
	// duration = cursor1
	// .getString(cursor1
	// .getColumnIndex(MediaStore.Audio.Media.DURATION));
	// artist = cursor1.getString(cursor1
	// .getColumnIndex(MediaStore.Audio.Media.ARTIST));
	// date = cursor1.getString(cursor1
	// .getColumnIndex(MediaStore.Audio.Media.DATA));
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// SongItems songItems = new SongItems();
	// songItems.setTitle(songname);
	// songItems.setAlbumname(alname);
	// songItems.setDuration(duration);
	// songItems.setArtist(artist);
	// songItems.setPath(date);
	// songList.add(songItems);
	// albumItem.setArrSongItems(songList);
	//
	// arrayList.add(albumItem);
	//
	// } while (cursor1.moveToNext());
	// }
	//
	// } while (cursor.moveToNext());
	// }
	//
	// return arrayList;
	//
	// }

	// @SuppressWarnings("deprecation")
	// public ArrayList<AlbumItem> getAlbumList() {
	//
	// ArrayList<AlbumItem> arrayList = new ArrayList<>();
	// String[] columns = { android.provider.MediaStore.Audio.Albums._ID,
	// android.provider.MediaStore.Audio.Albums.ALBUM,
	// android.provider.MediaStore.Audio.Albums.ARTIST };
	// // final String where = MediaStore.Audio.Albums.INTERNAL_CONTENT_URI +
	// // "=1";
	// Cursor cursor = context.managedQuery(
	// MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, columns, null,
	// null, null);
	//
	// if (cursor.moveToFirst()) {
	// do {
	// Log.e("Vipul",
	// cursor.getString(cursor
	// .getColumnIndex(android.provider.MediaStore.Audio.Albums.ALBUM)));
	// Bitmap bitmap = null;
	// String albumname = "";
	// String artistname = "";
	// try {
	// albumname = cursor
	// .getString(cursor
	// .getColumnIndex(android.provider.MediaStore.Audio.Albums.ALBUM));
	// artistname = cursor
	// .getString(cursor
	// .getColumnIndex(android.provider.MediaStore.Audio.Albums.ARTIST));
	//
	// Long albumId = cursor
	// .getLong(cursor
	// .getColumnIndexOrThrow(android.provider.MediaStore.Audio.Albums._ID));
	//
	// Uri sArtworkUri = Uri
	// .parse("content://media/external/audio/albumart");
	// Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri,
	// albumId);
	// Log.e(TAG, " uri == " + albumArtUri);
	// // Logger.debug(albumArtUri.toString());
	//
	// try {
	// bitmap = MediaStore.Images.Media.getBitmap(
	// context.getContentResolver(), albumArtUri);
	// bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100,
	// true);
	//
	// } catch (FileNotFoundException exception) {
	// exception.printStackTrace();
	// bitmap = BitmapFactory.decodeResource(
	// context.getResources(), R.drawable.logo);
	// } catch (IOException e) {
	//
	// e.printStackTrace();
	// }
	//
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// AlbumItem albumItem = new AlbumItem();
	// albumItem.setAlbumname(albumname);
	// albumItem.setArtistname(artistname);
	// albumItem.setBitmap(bitmap);
	// arrayList.add(albumItem);
	// } while (cursor.moveToNext());
	// }
	// return arrayList;
	//
	// }
	//
	// @SuppressWarnings("deprecation")
	// public ArrayList<ArtistItem> getArtistList() {
	//
	// ArrayList<ArtistItem> arrayList = new ArrayList<>();
	// String[] columns = { android.provider.MediaStore.Audio.Artists._ID,
	// android.provider.MediaStore.Audio.Artists.ARTIST,
	// android.provider.MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
	// android.provider.MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
	// android.provider.MediaStore.Audio.Artists.ARTIST_KEY };
	// // final String where = MediaStore.Audio.Albums.INTERNAL_CONTENT_URI +
	// // "=1";
	// Cursor cursor = context.managedQuery(
	// MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, columns, null,
	// null, null);
	//
	// if (cursor.moveToFirst()) {
	// do {
	// Log.e("yogi==",
	// cursor.getString(cursor
	// .getColumnIndex(android.provider.MediaStore.Audio.Artists.ARTIST)));
	// Bitmap bitmap = null;
	// String albumname = "", totalalbums = "", totalsongs = "";
	// String artistname = "";
	// try {
	//
	// artistname = cursor
	// .getString(cursor
	// .getColumnIndex(android.provider.MediaStore.Audio.Artists.ARTIST));
	// totalalbums = cursor
	// .getString(cursor
	// .getColumnIndex(android.provider.MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
	// totalsongs = cursor
	// .getString(cursor
	// .getColumnIndex(android.provider.MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
	// Long albumId = cursor
	// .getLong(cursor
	// .getColumnIndexOrThrow(android.provider.MediaStore.Audio.Artists.ARTIST_KEY));
	//
	// Uri sArtworkUri = Uri
	// .parse("content://media/external/audio/albumart");
	// Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri,
	// albumId);
	// Log.e(TAG, " uri == " + albumArtUri);
	// // Logger.debug(albumArtUri.toString());
	//
	// try {
	// bitmap = MediaStore.Images.Media.getBitmap(
	// context.getContentResolver(), albumArtUri);
	// bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100,
	// true);
	//
	// } catch (FileNotFoundException exception) {
	// exception.printStackTrace();
	// bitmap = BitmapFactory.decodeResource(
	// context.getResources(), R.drawable.logo);
	// } catch (IOException e) {
	//
	// e.printStackTrace();
	// }
	//
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// ArtistItem albumItem = new ArtistItem();
	// albumItem.setArtistname(artistname);
	// albumItem.setBitmap(bitmap);
	// if (totalalbums.equals("1")) {
	// albumItem.setTotalalbums(totalalbums + " ALBUM");
	// } else {
	// albumItem.setTotalalbums(totalalbums + " ALBUMS");
	// }
	//
	// albumItem.setTotalSongs(totalsongs);
	// arrayList.add(albumItem);
	// } while (cursor.moveToNext());
	// }
	// return arrayList;
	//
	// }
	//
	// @SuppressWarnings("unused")
	// public static Bitmap getArtworkFromFile(Context context, long songid,
	// long albumid) {
	// Bitmap bm = null;
	// byte[] art = null;
	// String path = null;
	// if (albumid < 0 && songid < 0) {
	// throw new IllegalArgumentException(
	// "Must specify an album or a song id");
	// }
	// try {
	// if (albumid < 0) {
	// Uri uri = Uri.parse("content://media/external/audio/media/"
	// + songid + "/albumart");
	// ParcelFileDescriptor pfd = context.getContentResolver()
	// .openFileDescriptor(uri, "r");
	// if (pfd != null) {
	// FileDescriptor fd = pfd.getFileDescriptor();
	// bm = BitmapFactory.decodeFileDescriptor(fd);
	// }
	// } else {
	// Uri uri = ContentUris.withAppendedId(sArtworkUri, albumid);
	// ParcelFileDescriptor pfd = context.getContentResolver()
	// .openFileDescriptor(uri, "r");
	// if (pfd != null) {
	// FileDescriptor fd = pfd.getFileDescriptor();
	// bm = BitmapFactory.decodeFileDescriptor(fd);
	// }
	// }
	// } catch (FileNotFoundException ex) {
	//
	// }
	// if (bm != null) {
	// // mCachedBit = bm;
	// } else {
	// bm = BitmapFactory.decodeResource(context.getResources(),
	// R.drawable.logo);
	// }
	// return bm;
	// }
	//
	// public void getMediaImage(String albumId) {
	// Cursor artCursor = context.getContentResolver().query(
	// MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
	// new String[] { MediaStore.Audio.AlbumColumns.ALBUM_ART },
	// MediaStore.Audio.Media._ID + " =?",
	// new String[] { String.valueOf(albumId) }, null);
	// String albumArt;
	// if (artCursor.moveToNext()) {
	// albumArt = "file://" + artCursor.getString(0);
	// Log.e(TAG, "albumArt=== " + albumArt);
	// } else {
	// albumArt = null;
	// }
	// artCursor.close();
	// Uri sArtworkUri = Uri.parse(albumArt);
	// if (albumArt != null) {
	// // BitmapFactory.decodeFile(new File(albumArt));
	//
	// Bitmap bitmap;
	// try {
	// bitmap = MediaStore.Images.Media.getBitmap(
	// context.getContentResolver(), sArtworkUri);
	// bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
	//
	// } catch (Exception exception) {
	// exception.printStackTrace();
	// bitmap = BitmapFactory.decodeResource(context.getResources(),
	// R.drawable.logo);
	//
	// }
	//
	// }
	// }

	public static double distance(double lat1, double lon1, double lat2,
			double lon2, String unit) {
		// double theta = lon1 - lon2;
		// double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
		// + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
		// * Math.cos(deg2rad(theta));
		// dist = Math.acos(dist);
		// dist = rad2deg(dist);
		// dist = dist * 60 * 1.1515;
		// if (unit == "K") {
		//
		// dist = dist * 1.609344;
		// Log.d(TAG, "" + dist);
		// } else if (unit == "N") {
		// dist = dist * 0.8684;
		// }
		//
		// return (dist);
		//

		int Radius = 6371;// radius of earth in Km

		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
				* Math.sin(dLon / 2);
		double c = 2 * Math.asin(Math.sqrt(a));
		double valueResult = Radius * c;
		double km = valueResult / 1;
		DecimalFormat newFormat = new DecimalFormat("####");
		int kmInDec = Integer.valueOf(newFormat.format(km));
		double meter = valueResult % 1000;
		int meterInDec = Integer.valueOf(newFormat.format(meter));
		Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
				+ " Meter   " + meterInDec);

		return kmInDec;
	}

	public static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	public static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	@SuppressWarnings({ "deprecation", "unused", "unchecked", "rawtypes" })
	public static String getDistanceOnRoad(double latitude, double longitude,
			double prelatitute, double prelongitude) {
		String result_in_kms = "";
		String url = "http://maps.google.com/maps/api/directions/xml?origin="
				+ latitude + "," + longitude + "&destination=" + prelatitute
				+ "," + prelongitude + "&sensor=false&units=metric";
		String tag[] = { "text" };
		HttpResponse response = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(url);
			response = httpClient.execute(httpPost, localContext);
			InputStream is = response.getEntity().getContent();
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(is);
			if (doc != null) {
				NodeList nl;
				ArrayList args = new ArrayList();
				for (String s : tag) {
					nl = doc.getElementsByTagName(s);
					if (nl.getLength() > 0) {
						Node node = nl.item(nl.getLength() - 1);
						args.add(node.getTextContent());
					} else {
						args.add(" - ");
					}
				}
				result_in_kms = String.format("%s", args.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_in_kms;
	}

	public static float distanceFrom(double lat1, double lng1, double lat2,
			double lng2) {
		double earthRadius = 3958.75;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
				* Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;
		int meterConversion = 1609;
		return new Float(dist * meterConversion).floatValue();
	}
	
	
	
	public static ArrayList<HashMap<String, String>> getContacts(Context context) {
		// TODO Auto-generated method stub

		ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
		ContentResolver cr = context.getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					Cursor pCur = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?", new String[] { id }, null);
					while (pCur.moveToNext()) {
						int phoneType = pCur
								.getInt(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
						String phoneNumber = pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						
						String names = pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
						HashMap<String, String> hashMap = new HashMap<String, String>();
						switch (phoneType) {
					
						case Phone.TYPE_MOBILE:
							
						
							// Log.e(name + "(mobile number)", phoneNumber);
							// arrayList.add(getSmoothContacts(phoneNumber.trim().replace(" ",
							// "").replace("-", "")));

							// arrayList.add(phoneNumber.trim().replace(" ", "")
							// .replace("-", ""));
							PhoneNumberUtil phoneUtil = PhoneNumberUtil
									.getInstance();
							try {
								// phone must begin with '+'
								PhoneNumber numberProto = phoneUtil.parse(
										phoneNumber.trim(), "");
								int countryCode = numberProto.getCountryCode();

								/*
								 * Log.e("cc", "cc" + countryCode + " ph " +
								 * phoneNumber);
								 */

							} catch (NumberParseException e) {
								System.err
										.println("NumberParseException was thrown: "
												+ e.toString());
							}

//							if (!arrayList.contains(phoneNumber)) {
								
								hashMap.put("phone", phoneNumber.trim());
								hashMap.put("name", names);
								arrayList.add(hashMap);
//							}

							break;
						case Phone.TYPE_HOME:
							// Log.e(name + "(home number)", phoneNumber);
							break;
						case Phone.TYPE_WORK:
							// Log.e(name + "(work number)", phoneNumber);
							break;
						case Phone.TYPE_OTHER:
							// Log.e(name + "(other number)", phoneNumber);

							if (!arrayList.contains(phoneNumber)) {
								PhoneNumberUtil phoneUtil1 = PhoneNumberUtil
										.getInstance();
								try {
									// phone must begin with '+'
									PhoneNumber numberProto = phoneUtil1.parse(
											phoneNumber.trim(), "");
									int countryCode = numberProto
											.getCountryCode();

									// Log.e("TYPE_OTHER", "cc" + countryCode +
									// " ph "
									// + phoneNumber);

								} catch (NumberParseException e) {
									System.err
											.println("NumberParseException was thrown: "
													+ e.toString());
								}
//								if (!arrayList.contains(phoneNumber)) {
								hashMap.put("phone", phoneNumber.trim());
								hashMap.put("name", names);
								arrayList.add(hashMap);
//								}
							}
							break;
						default:
							break;
						}
					}
					pCur.close();
				}
			}
		}
		return arrayList;
	}
}
