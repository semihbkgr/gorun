package com.semihbg.gorun.util;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import java.io.*;
import java.util.function.Supplier;

public class InputStreamHandler {

    public static void handle(Supplier<InputStream> inputStreamSupplier, TextView textView, Context context){
        new Thread(()->{
            try(InputStream inputStream=inputStreamSupplier.get()){
                StringBuilder stringBuilder=new StringBuilder();
                int b;
                while( (b= inputStream.read())!=-1 ){
                    System.out.println("***********************************************");
                    stringBuilder.append((char)b);

                }
                System.out.println("------------------------------------------------------");
                System.out.println(stringBuilder);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();

    }


}
