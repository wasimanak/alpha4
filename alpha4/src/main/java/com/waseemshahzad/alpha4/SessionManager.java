package com.waseemshahzad.alpha4;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Adnan Bashir manak on 01,December,2021
 * AIS company,
 * Krachi, Pakistan.
 */
public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences("Session",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public void setFirstRun(boolean firstrun){
        editor.putBoolean("firstrun",firstrun);
        editor.commit();
        editor.apply();
    }
    public boolean getFirstRun(){
        return   sharedPreferences.getBoolean("firstrun",false);
    }

}
