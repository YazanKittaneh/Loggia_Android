
        package com.loggia.Helpers;

        import android.app.Activity;
        import android.content.Context;
        import android.content.res.Resources;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.drawable.BitmapDrawable;
        import android.graphics.drawable.Drawable;
        import android.util.DisplayMetrics;

        import com.parse.ParseException;
        import com.parse.ParseFile;
        import com.parse.ParseObject;

        import java.io.ByteArrayOutputStream;

/**
 * Created by L7 on 8/23/15.
 */
public class ImageScalar{

    int width;
    int height;
    DisplayMetrics dm;


    /** constructor takes in the activity and gets the eight and width from that activity **/
    public ImageScalar(Activity activity) {
        this.dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.width = dm.widthPixels;
        this.height = dm.heightPixels;

    }


    public BitmapDrawable decodeSampledBitmapFromDrabwable(Resources res, int drawable) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(res, drawable, options);
        options.inSampleSize = calculateInSampleSize(options, this.width / 4, this.height / 4);
        options.inJustDecodeBounds = false;

        return new BitmapDrawable(res, BitmapFactory.decodeResource(res, drawable));


    }


    public BitmapDrawable decodeSampledBitmapFromParse(Resources res, ParseObject mParseObject) {

        StockImageRandomizer randomStock;
        randomStock = new StockImageRandomizer();
        int randomImage = randomStock.getRandomStockDrawable();
        ParseFile imgFile;
        byte[] file = new byte[0];


        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        imgFile = mParseObject.getParseFile("Image");

        if (imgFile != null) {
            try {
                file = imgFile.getData();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

            BitmapFactory.decodeByteArray(file, 0, file.length, options);
            options.inSampleSize = calculateInSampleSize(options, this.width, this.height);
            options.inJustDecodeBounds = false;
            return new BitmapDrawable(res, BitmapFactory.decodeByteArray(file, 0, file.length, options));
        } else {

            BitmapFactory.decodeResource(res, randomImage, options);
            options.inSampleSize = calculateInSampleSize(options, this.width, this.height);
            options.inJustDecodeBounds = false;

            return new BitmapDrawable(res, BitmapFactory.decodeResource(res, randomStock.getRandomStockDrawable()));
        }

    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    /** compresses a given bitmap to 70% and send byte[] back **/
    public static byte[] compressForUpload(Bitmap bitmap) {
        byte[] data;

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        data = stream.toByteArray();
        return data;
    }


}