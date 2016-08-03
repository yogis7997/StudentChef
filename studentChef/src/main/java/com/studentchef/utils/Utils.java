package com.studentchef.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Utils {
	private static final String TAG_LOG = "Utils";

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static String millisToShortDHMS(long duration) {
		String res = "";
		long days = TimeUnit.MILLISECONDS.toDays(duration);
		long hours = TimeUnit.MILLISECONDS.toHours(duration)
				- TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
		long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
				- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
						.toHours(duration));
		long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
				- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
						.toMinutes(duration));
		if (days == 0) {
			res = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		} else {
			res = String.format("%dd%02d:%02d:%02d", days, hours, minutes,
					seconds);
		}
		return res;
	}

	public static Bitmap decodeFile(String pathName) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		for (options.inSampleSize = 1; options.inSampleSize <= 32; options.inSampleSize++) {
			try {
				bitmap = BitmapFactory.decodeFile(pathName, options);
				Log.d(TAG_LOG, "Decoded successfully for sampleSize "
						+ options.inSampleSize);
				break;
			} catch (OutOfMemoryError outOfMemoryError) {
				// If an OutOfMemoryError occurred, we continue with for loop
				// and next inSampleSize value
				Log.e(TAG_LOG,
						"outOfMemoryError while reading file for sampleSize "
								+ options.inSampleSize
								+ " retrying with higher value");
			}
		}
		return bitmap;
	}

	public static Bitmap getResizedBitmap(Bitmap bitmap, float maxWidth,
			float maxHeight) {

		float width = bitmap.getWidth();
		float height = bitmap.getHeight();
		if (width > maxWidth) {
			height = (maxWidth / width) * height;
			width = maxWidth;
		}
		if (height > maxHeight) {
			width = (maxHeight / height) * width;
			height = maxHeight;
		}
		return Bitmap.createScaledBitmap(bitmap, (int) width, (int) height,
				true);

	}

	public static String ChangeUnixTimeStampToDate(long duration_seconds) {

		long unixSeconds = duration_seconds;
		Date date = new Date(unixSeconds * 1000L); // *1000 is to convert
													// seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); // the
																			// format
																			// of
																			// your
																			// date
		sdf.setTimeZone(TimeZone.getDefault()); // give a timezone
												// reference for
												// formating (see
												// comment at the bottom
		String formattedDate = sdf.format(date);
		System.out.println(formattedDate);
		return formattedDate;
	}

	public static String getDifferenceBetweenTwoDates(String firstDate,
			String SecondDate) {

		String result = "";
		// String dateStart = "01/14/2012 09:29:58";
		// String dateStop = "01/15/2012 10:31:48";

		String dateStart = firstDate;
		String dateStop = SecondDate;

		// HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			// in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");

			if (diffDays != 0) {
				result = result + diffDays + " Days ago";
			} else if (diffHours != 0) {
				result = result + diffHours + " Hours ago";
			} else if (diffMinutes != 0) {
				result = result + diffMinutes + " Min ago";
			} else if (diffSeconds != 0) {
				result = result + diffSeconds + " Sec ago";
			}else{
				result =" Just now";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	public static String getCurrentDate() {
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String formattedDate = df.format(c.getTime());
		return formattedDate;
	}
}