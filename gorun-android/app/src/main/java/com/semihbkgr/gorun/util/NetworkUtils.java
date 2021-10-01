package com.semihbkgr.gorun.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;
import com.semihbkgr.gorun.AppConstants;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class NetworkUtils {

    public static boolean hasNetworkConnection(@NonNull Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isServerUp(){
        try {
            URL url = new URL(AppConstants.Nets.SERVER_STATUS_URI);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            byte[] buffer=new byte[256];
            try(InputStream is=con.getInputStream()){
                int length=is.read(buffer);
                String response=new String(Arrays.copyOf(buffer,length), StandardCharsets.UTF_8);
                return response.equalsIgnoreCase("up");
            }
        } catch (IOException e) {
            return false;
        }

    }

}
