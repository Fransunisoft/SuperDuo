package com.fransunisoft.stockhawk.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesManager {
  private static Context context;

  public SharedPreferencesManager(Context context){
    this.context = context;
  }

  public static SharedPreferences getSharedPreferences(){
    return PreferenceManager.getDefaultSharedPreferences(context);
  }
}
