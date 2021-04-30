package com.arife.adnbikerider.Utilitarios.Gps;

import android.content.Context;

import com.arife.adnbikerider.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class UtilsGps {

    public static String DistanceMyVehicle(float distance){

        String val = "Distancia recorrida ";

        if (distance>1000){

            distance=distance/1000;
            val+= new DecimalFormat("#.00").format(distance) + " Km";

        }else{

            val+= ((int ) distance) +" Mtrs";

        }
        return val;
    }

    public static long DateToMillisecons() {

        Date currentTime = new Date();
        long curMillis = currentTime.getTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(curMillis);
        return seconds;

    }

    public static long TimeGpsResponse(int time){

        final long long1 = Long.parseLong(Integer.toString(time).concat("000"));
        TimeZone.setDefault(TimeZone.getTimeZone("America/Lima"));
        Calendar.getInstance(Locale.getDefault()).setTimeInMillis(long1);
        Date fecha = new Date();
        long millis  = fecha.getTime();

        return (TimeUnit.MILLISECONDS.toMinutes(millis)-TimeUnit.MILLISECONDS.toMinutes(long1));
    }

}
