package com.jiangjun.library.utils;

import android.util.Log;

/**
 * Created by Administrator姜军 on 2018/3/10.
 */

public class RLog {

    private static final String TAG = "XZC";

    /**
     * Priority constant for the println method; use Log.v.
     */
    private static final int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    private static final int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    private static final int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    private static final int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    private static final int ERROR = 6;

    public static void setLevel(int value) {
        level = value;
    }

    private static int level = VERBOSE;

    /**
     * Send a VERBOSE log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int v(String tag, String msg) {
        if (level > VERBOSE) {
            return 0;
        }

        return Log.v(TAG, "[ " + tag + " ] " + msg);
    }

    /**
     * Send a DEBUG log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int d(String tag, String msg) {
        if (level > DEBUG) {
            return 0;
        }

        return Log.d(TAG, "[ " + tag + " ] " + msg);
    }

    /**
     * Send an INFO log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int i(String tag, String msg) {
        if (level > INFO) {
            return 0;
        }

        return Log.i(TAG, "[ " + tag + " ] " + msg);
    }

    /**
     * Send a WARN log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int w(String tag, String msg) {
        if (level > WARN) {
            return 0;
        }

        return Log.w(TAG, "[ " + tag + " ] " + msg);
    }

    /**
     * Send an ERROR log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int e(String tag, String msg) {
        if (level > ERROR) {
            return 0;
        }

        return Log.e(TAG, "[ " + tag + " ] " + msg);
    }

    /**
     * Send a ERROR log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static int e(String tag, String msg, Throwable tr) {
        if (level > ERROR) {
            return 0;
        }

        return Log.e(TAG, "[ " + tag + " ] " + msg, tr);
    }
}

