package ru.vladislav_akulinin.githubauth.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

public class InternetConnection {
    //проверка интернет соединения
    public static boolean checkConnection(@NonNull Context context){
        return ((ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
