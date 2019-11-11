package com.journaldev.barcodevisionapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class FileStorage {

    private static Context context;

    public static FileOutputStream getFileOutputStream(String filename)
            throws IOException {
        File f = null;
        f = new File(context.getCacheDir() + "/" + filename);
        Log.d("FileStorage", "writing file " + f.getAbsolutePath() + " - "
                + filename);
        if (f.createNewFile() && f.canWrite()) {
            return new FileOutputStream(f);
        }
        return null;
    }

    public static FileInputStream getFileInputStream(String filename)
            throws FileNotFoundException {
        File f = null;
        f = new File(context.getCacheDir() + "/" + filename);
        if (f.canRead()) {
            Log.d("FileStorage", "reading file " + f.getAbsolutePath() + " - "
                    + filename);
            return new FileInputStream(f);
        } else {
            Log.d("FileStorage", "Can't read file " + filename);
        }
        return null;

    }

    public static void deleteFile(String filename) {
        File f = new File(context.getCacheDir() + "/" + filename);
        if (f.canRead()) {
            f.delete();
        }
    }

    public static void clearFileCache(ArrayList<String> exceptionFilenames) {
        File dir = context.getCacheDir();
        if (dir != null && dir.isDirectory()) {
            try {
                for (File children : dir.listFiles()) {
                    Log.d("FileStorage", "checking " + children.getName());
                    if (exceptionFilenames != null
                            && !exceptionFilenames.contains(children.getName())) {
                        Log.d("FileStorage", "Deleting unbound file "
                                + children.getName());
                        children.delete();
                    }
                }
            } catch (Exception e) {
                Log.e("FileStorage", "failed to clean cache", e);
            }
        }

    }

    public static void setContext(Context c) {
        context = c;
    }

}