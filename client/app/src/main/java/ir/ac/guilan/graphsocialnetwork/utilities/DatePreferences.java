package ir.ac.guilan.graphsocialnetwork.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DatePreferences {
    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context context;

    // Shared pref mode
    int privateMode = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "graphsocialnetwork";

    private static final String TOKEN = "token";


    public DatePreferences(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, privateMode);
        editor = pref.edit();
    }

    public void setToken(int message) {
        editor.putInt(TOKEN, message);
        // commit changes
        editor.commit();

    }

    public int getToken() {
        return pref.getInt(TOKEN, 0);
    }

}