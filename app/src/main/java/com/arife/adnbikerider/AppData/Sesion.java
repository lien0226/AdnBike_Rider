package com.arife.adnbikerider.AppData;

import android.content.Context;
import android.content.SharedPreferences;

import com.arife.adnbikerider.R;
import com.arife.adnbikerider.mvc.m.LoginModel;

import static android.content.Context.MODE_PRIVATE;

public class Sesion {

    private static Sesion sesion;
    private static Context context;
    private static SharedPreferences sharedPreferences;

    private Sesion(){};

    public static Sesion getInstance(Context context){
        if (sesion==null){
            sesion = new Sesion();
            Sesion.context = context;
            sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.app_name), MODE_PRIVATE);
        }
        return sesion;
    }

    public void saveSesion(LoginModel loginModel){
        sharedPreferences.edit().putString("Username", loginModel.getUsername().trim()).apply();
        sharedPreferences.edit().putString("Password", loginModel.getPassword().trim()).apply();
        sharedPreferences.edit().putString("Name", loginModel.getName()).apply();
    }

    public LoginModel readSesion(){
        String username = sharedPreferences.getString("Username","");
        LoginModel sesion = null;

        if (username.equals("")){
            return sesion;
        }else{
            sesion = new LoginModel();
            sesion.setUsername(sharedPreferences.getString("Username",""));
            sesion.setPassword(sharedPreferences.getString("Password",""));
            sesion.setName(sharedPreferences.getString("Name",""));
            return sesion;
        }

    }

    public LoginModel closeSesion(){
        sharedPreferences.edit().clear().apply();
        LoginModel sesion = null;
        return sesion;
    }

    public static Sesion getInstance(){
        return sesion;
    }
}
