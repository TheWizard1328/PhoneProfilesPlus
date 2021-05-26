package sk.henrichg.phoneprofilesplus;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.exifinterface.media.ExifInterface;

import java.io.InputStream;

class BitmapManipulator {

    static final int ICON_BITMAP_SIZE_MULTIPLIER = 4;

    static Bitmap resampleBitmapUri(String bitmapUri, int width, int height, boolean checkSize, boolean checkOrientation, Context context) {
        //PPApplication.logE("---- BitmapManipulator.resampleBitmapUri", "bitmapUri="+bitmapUri);
        //PPApplication.logE("---- BitmapManipulator.resampleBitmapUri", "width="+width);
        //PPApplication.logE("---- BitmapManipulator.resampleBitmapUri", "height="+height);
        //PPApplication.logE("---- BitmapManipulator.resampleBitmapUri", "checkSize="+checkSize);
        //PPApplication.logE("---- BitmapManipulator.resampleBitmapUri", "checkOrientation="+checkOrientation);

        if (bitmapUri == null)
            return null;

        if (!Permissions.checkGallery(context))
            return null;

        Uri uri = Uri.parse(bitmapUri);
        //PPApplication.logE("---- BitmapManipulator.resampleBitmapUri", "uri="+uri);
        if (uri != null) {
            try {

                int rotatedWidth = width;
                int rotatedHeight = height;
                int orientation = 0;

                if (checkOrientation) {
                    orientation = getBitmapUriOrientation(context, uri);

                    if (orientation == 90 || orientation == 270) {
                        //noinspection SuspiciousNameCombination
                        rotatedWidth = height;
                        //noinspection SuspiciousNameCombination
                        rotatedHeight = width;
                    } /*else {
                    rotatedWidth = width;
                    rotatedHeight = height;
                }*/
                }
                //PPApplication.logE("---- BitmapManipulator.resampleBitmapUri", "orientation="+orientation);
                //PPApplication.logE("---- BitmapManipulator.resampleBitmapUri", "rotatedWidth="+rotatedWidth);
                //PPApplication.logE("---- BitmapManipulator.resampleBitmapUri", "rotatedHeight="+rotatedHeight);

                ContentResolver contentResolver = context.getContentResolver();

                //boolean ok = false;

                //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    context.grantUriPermission(PPApplication.PACKAGE_NAME, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //ok = true;
                } catch (Exception e) {
                    // java.lang.SecurityException: UID 10157 does not have permission to
                    // content://com.android.externalstorage.documents/document/93ED-1CEC%3AMirek%2Fmobil%2F.obr%C3%A1zek%2Fblack.jpg
                    // [user 0]; you could obtain access using ACTION_OPEN_DOCUMENT or related APIs
                    //Log.e("BitmapManipulator.resampleBitmapUri", Log.getStackTraceString(e));
                    //PPApplication.recordException(e);
                    return null;
                }
                //if (!ok)
                //    return null;
                //}

                //ok = false;
                InputStream inputStream;
                try {
                    // check if bitmap format is supported or if exists
                    inputStream = context.getContentResolver().openInputStream(uri);
                    //ok = true;
                } catch (Exception e) {
                    return null;
                }
                //if (!ok)
                //    return null;

                // bitmap format is supported and exists
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(inputStream, null, options);

                if(inputStream != null)
                    inputStream.close();

                if (checkSize) {
                    // raw height and width of image
                    final int rawHeight = options.outHeight;
                    final int rawWidth = options.outWidth;
                    if ((rawWidth > ICON_BITMAP_SIZE_MULTIPLIER * width) || (rawHeight > ICON_BITMAP_SIZE_MULTIPLIER * height)) {
                        //PPApplication.logE("BitmapManipulator.resampleBitmapUri", "too large");
                        return null;
                    }
                }

                Bitmap decodedSampleBitmap;
                inputStream = context.getContentResolver().openInputStream(uri);

                // calculate inSampleSize
                options.inSampleSize = calculateInSampleSize(options, rotatedWidth, rotatedHeight);

                options.inJustDecodeBounds = false;
                decodedSampleBitmap = BitmapFactory.decodeStream(inputStream, null, options);

                if(inputStream != null)
                    inputStream.close();

                if (decodedSampleBitmap != null) {
                    /*
                     * if the orientation is not 0 (or -1, which means we don't know), we
                     * have to do a rotation.
                     */
                    if (checkOrientation && (orientation > 0)) {
                        //PPApplication.logE("---- BitmapManipulator.resampleBitmapUri", "rotated");

                        Matrix matrix = new Matrix();
                        matrix.postRotate(orientation);

                        decodedSampleBitmap = Bitmap.createBitmap(decodedSampleBitmap, 0, 0, decodedSampleBitmap.getWidth(),
                                decodedSampleBitmap.getHeight(), matrix, true);
                    }
                    //PPApplication.logE("---- BitmapManipulator.resampleBitmapUri", "decodedSampleBitmap="+decodedSampleBitmap);
                }
                return decodedSampleBitmap;
            } catch (Exception ee) {
                //Log.e("BitmapManipulator.resampleBitmapUri", Log.getStackTraceString(ee));
                PPApplication.recordException(ee);
                return null;
            }
        }
        else
            return null;
    }

    private static int getBitmapUriOrientation(Context context, Uri photoUri) {
        try {
            //if (Build.VERSION.SDK_INT >= 24) {
                InputStream inputStream;
                inputStream = context.getContentResolver().openInputStream(photoUri);
                if (inputStream != null) {
                    ExifInterface exif = new ExifInterface(inputStream);
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    inputStream.close();

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            return 270;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            return  180;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            return  90;
                        default:
                            return 0;
                    }
                }
                else
                    return -1;
//            }
//            else {
//                Cursor cursor = context.getContentResolver().query(photoUri,
//                        new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);
//                if (cursor != null) {
//                    if (cursor.getCount() != 1) {
//                        cursor.close();
//                        return -1;
//                    }
//
//                    cursor.moveToFirst();
//
//                    int orientation = cursor.getInt(0);
//
//                    cursor.close();
//                    return orientation;
//                } else
//                    return -1;
//            }
        } catch (Exception e) {
            return -1;
        }
    }

    static boolean checkBitmapSize(String bitmapUri, int width, int height, Context context) {
        Uri uri = Uri.parse(bitmapUri);
        if (uri != null) {
            try {
                InputStream inputStream = context.getContentResolver().openInputStream(uri);
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(inputStream, null, options);
                if(inputStream != null)
                    inputStream.close();

                // raw height and width of image
                final int rawHeight = options.outHeight;
                final int rawWidth = options.outWidth;
                return (rawWidth <= ICON_BITMAP_SIZE_MULTIPLIER * width) && (rawHeight <= ICON_BITMAP_SIZE_MULTIPLIER * height);
            } catch (Exception e) {
                //Log.e("BitmapManipulator.resampleBitmapUri", Log.getStackTraceString(e));
                return false;
            }
        }
        return false;
    }

    /*
    static Bitmap resampleBitmapFile(String bitmapFile, int width, int height, Context context)
    {
        if (bitmapFile == null)
            return null;

        if (!Permissions.checkGallery(context))
            return null;

        int orientation = ExifInterface.ORIENTATION_UNDEFINED;
        try {
            ExifInterface exif = new ExifInterface(bitmapFile);
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        } catch (Exception ignored) {
        }

        File f = new File(bitmapFile);
        if (f.exists())
        {
            // first decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(bitmapFile, options);

            int rotate = 0;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                case ExifInterface.ORIENTATION_TRANSVERSE:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                case ExifInterface.ORIENTATION_TRANSPOSE:
                    rotate = 90;
                    break;
            }

            int rotatedWidth, rotatedHeight;
            if (rotate == 90 || rotate == 270) {
                //noinspection SuspiciousNameCombination
                rotatedWidth = height;
                //noinspection SuspiciousNameCombination
                rotatedHeight = width;
            } else {
                rotatedWidth = width;
                rotatedHeight = height;
            }

            // calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, rotatedWidth, rotatedHeight);

            // decode bitmap with inSampleSize
            options.inJustDecodeBounds = false;
            Bitmap decodedSampleBitmap = BitmapFactory.decodeFile(bitmapFile, options);
            decodedSampleBitmap = rotateBitmap(decodedSampleBitmap, orientation);

            return decodedSampleBitmap;
        }
        else
            return null;
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                //Log.d("BitmapManipulator.rotateBitmap","ORIENTATION_NORMAL");
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                //Log.d("BitmapManipulator.rotateBitmap","ORIENTATION_FLIP_HORIZONTAL");
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                //Log.d("BitmapManipulator.rotateBitmap","ORIENTATION_ROTATE_180");
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                //Log.d("BitmapManipulator.rotateBitmap","ORIENTATION_FLIP_VERTICAL");
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                //Log.d("BitmapManipulator.rotateBitmap","ORIENTATION_TRANSPOSE");
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                //Log.d("BitmapManipulator.rotateBitmap","ORIENTATION_ROTATE_90");
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                //Log.d("BitmapManipulator.rotateBitmap","ORIENTATION_TRANSVERSE");
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                //Log.d("BitmapManipulator.rotateBitmap","ORIENTATION_ROTATE_270");
                matrix.setRotate(-90);
                break;
            default:
                //Log.d("BitmapManipulator.rotateBitmap","default");
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            return null;
        }
    }
    */

    /*
    static Bitmap resampleResource(Resources resources, int bitmapResource, int width, int height)
    {
        // first decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, bitmapResource, options);
        // calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);
        // decode bitmap with inSampleSize
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, bitmapResource, options);
    }
    */

    static Bitmap recolorBitmap(Bitmap bitmap, int color/*, Context context*/)
    {
        if (bitmap == null)
            return null;

        Bitmap colorBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                                                        bitmap.getHeight(),
                                                        bitmap.getConfig());
                                                        //Config.ARGB_8888);

        Canvas canvas = new Canvas(colorBitmap);
        Paint paint = new Paint();
        Matrix matrix = new Matrix();

        ColorFilter filter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        paint.setColorFilter(filter);
        canvas.drawBitmap(bitmap, matrix, paint);

        return colorBitmap;
    }

    static Bitmap monochromeBitmap(Bitmap bitmap, int value/*, Context context*/) {
        int color = Color.argb(0xFF, value, value, value);
        return recolorBitmap(bitmap, color/*, context*/);
    }

    /*
    static Drawable tintDrawableByColor(Drawable drawable, int color) {
        Drawable wrapDrawable = DrawableCompat.wrap(drawable);
        //DrawableCompat.setTintMode(wrapDrawable,  PorterDuff.Mode.DST_ATOP);
        DrawableCompat.setTint(wrapDrawable, color);
        wrapDrawable.mutate();
        return wrapDrawable;
    }

    static Drawable tintDrawableByValue(Drawable drawable, int value) {
        int color  = Color.argb(0xFF, value, value, value);
        Drawable tintedDrawable = tintDrawableByColor(drawable, color);
        tintedDrawable.mutate();
        return tintedDrawable;
    }
    */

    static Bitmap getBitmapFromDrawable(Drawable drawable, boolean appIconSize/*, Context context*/) {
        if (drawable instanceof BitmapDrawable) {
            //PPApplication.logE("BitmapManipulator.getBitmapFromDrawable", "is BitmapDrawable");
            return ((BitmapDrawable)drawable).getBitmap();
        }
        //PPApplication.logE("BitmapManipulator.getBitmapFromDrawable", "is NOT BitmapDrawable");

        try {
            /*if (PPApplication.logEnabled()) {
                PPApplication.logE("BitmapManipulator.getBitmapFromDrawable", "drawable width=" + drawable.getIntrinsicWidth());
                PPApplication.logE("BitmapManipulator.getBitmapFromDrawable", "drawable height=" + drawable.getIntrinsicHeight());
            }*/
            int height;
            int width;
            if (appIconSize) {
                height = GlobalGUIRoutines.dpToPx(GlobalGUIRoutines.ICON_SIZE_DP);
                width = GlobalGUIRoutines.dpToPx(GlobalGUIRoutines.ICON_SIZE_DP);
            }
            else {
                height = drawable.getIntrinsicHeight();
                width = drawable.getIntrinsicWidth();
            }
            /*if (PPApplication.logEnabled()) {
                PPApplication.logE("BitmapManipulator.getBitmapFromDrawable", "width=" + width);
                PPApplication.logE("BitmapManipulator.getBitmapFromDrawable", "height=" + height);
                PPApplication.logE("BitmapManipulator.getBitmapFromDrawable", "50dp=" + GlobalGUIRoutines.dpToPx(GlobalGUIRoutines.ICON_SIZE_DP));
            }*/
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            //Log.e("BitmapManipulator.getBitmapFromDrawable", Log.getStackTraceString(e));
            PPApplication.recordException(e);
            return null;
        }
    }

    static Bitmap getBitmapFromResource(int drawableRes, boolean appIconSize, Context context) {
        //Drawable drawable = ContextCompat.getDrawable(context, drawableRes);
        Drawable drawable = AppCompatResources.getDrawable(context, drawableRes);
        //PPApplication.logE("BitmapManipulator.getBitmapFromResource", "drawable="+drawable);
        return getBitmapFromDrawable(drawable, appIconSize/*, context*/);
    }

    static Bitmap grayScaleBitmap(Bitmap bitmap)
    {
        if (bitmap == null)
            return null;

        Bitmap monochromeBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(),
                bitmap.getConfig());
        Canvas canvas = new Canvas(monochromeBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        Matrix matrix = new Matrix();
        canvas.drawBitmap(bitmap, matrix, paint);

        return monochromeBitmap;
    }

    static Bitmap setBitmapBrightness(Bitmap bitmap, float brightness)
    {
        if (bitmap == null)
            return null;

        float[] colorTransform = {
                1f, 0, 0, 0, brightness,
                0, 1f, 0, 0, brightness,
                0, 0, 1f, 0, brightness,
                0, 0, 0, 1f, 0};

        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(),
                bitmap.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(colorTransform);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        Matrix matrix = new Matrix();
        canvas.drawBitmap(bitmap, matrix, paint);

        return newBitmap;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 2; // (was 1) maybe fixes java.lang.OutOfMemoryError

        if (height > reqHeight || width > reqWidth)
        {
            // calculate ratios of height and width to requested height an width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // choose the smallest ratio as InSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width
            inSampleSize = Math.min(heightRatio, widthRatio);
            if (inSampleSize < 2)
                inSampleSize = 2; // maybe fixes java.lang.OutOfMemoryError
        }
        return inSampleSize;
    }

}
