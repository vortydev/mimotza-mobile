package com.example.mimotza;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class FileIO {

    /**
     * Load data from a file.
     * @param context Activity's context. (ex. this)
     * @param file String of file's name. (ex. "read.txt")
     * @return String containing all the lines of a file.
     */
    public static String loadFromFile(Context context, String file) {
        String str = "";
        InputStream inputStream = null;
        try {
            Log.d("debug", context.getFilesDir().toString());
            inputStream = context.openFileInput(file);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                Scanner scan = new Scanner(inputStreamReader);
                String scanString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while (scan.hasNextLine()) {
                    scanString = scan.nextLine();
                    stringBuilder.append(scanString).append("\n");
                }

                inputStream.close();
                str = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("Exception", "File not found: " + e.toString());
            Toast.makeText(context, "File doesn't exist.", Toast.LENGTH_LONG).show();
        }
        catch (IOException e) {
            Log.e("Exception", "Cannot read file: " + e.toString());
        }
        finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    /**
     * Save data to a file.
     * @param context Activity's context. (ex. this)
     * @param file String of file's name. (ex. "write.txt")
     * @param str2write String to write to the file. (ex. "I saw green vent.")
     */
    public static void saveToFile(Context context, String file, String str2write) {
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter(context.openFileOutput(file, Context.MODE_PRIVATE));
            outputStreamWriter.write(str2write);
            Toast.makeText(context, "Saved to " + context.getFilesDir() + "/" + file, Toast.LENGTH_LONG).show();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        finally {
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String saveBitmapToStorage(Context context, String directory, Bitmap bitmap, String fileName){
        ContextWrapper wrapper = new ContextWrapper(context.getApplicationContext());
        // path to /data/data/APPNAME/app_data/DIRECTORY/
        File dir = wrapper.getDir(directory, Context.MODE_PRIVATE);

        // creates the file
        File path = new File(dir, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // returns the path
        return dir.getAbsolutePath();
    }

    public static Bitmap getBitmapFromStorage(String path, String fileName)
    {
        Bitmap b = null;
        try {
            File f = new File(path, fileName);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            return b;
        }
    }
}
