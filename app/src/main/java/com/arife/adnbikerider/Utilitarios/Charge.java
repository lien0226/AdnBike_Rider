package com.arife.adnbikerider.Utilitarios;

import com.arife.adnbikerider.AppData.Sesion;
import com.arife.adnbikerider.mvc.m.LoginModel;
import com.arife.adnbikerider.mvc.m.UserModel;

import java.util.HashMap;
import java.util.Map;

public class Charge {

    public static String Link_Base = "http://181.224.251.170:9090/AdnRoutes/Routes/";
    public static String Base_get = "http://181.224.251.170:9090/AdnRoutes/Routes";
    private static Charge charge;
    private Sesion sesion;

    private Charge(){};

    public static Charge getInstance(){
        if (charge==null){
            charge = new Charge();
        }
        return charge;
    }

    public Map genLogin(LoginModel loginModel){
        Map<String, Object> map = new HashMap<>();
        map.put("loggin",loginModel.getUsername());
        map.put("password",loginModel.getPassword());

        return map;
    }

    public Map genRegisterUser(UserModel usuarioModel){
        Map<String, Object> map = new HashMap<>();
        if (usuarioModel.getOpcion().equals("N")){
            map.put("id",String.valueOf(usuarioModel.getId()));
            map.put("opcion",usuarioModel.getOpcion());
            map.put("email", usuarioModel.getEmail());
            map.put("password", usuarioModel.getPassword());
            map.put("login",usuarioModel.getLogin());
            map.put("verificacion", usuarioModel.getVerificacion());
        }else if(usuarioModel.getOpcion().equals("V")){
            map.put("id",String.valueOf(usuarioModel.getId()));
            map.put("opcion",usuarioModel.getOpcion());
            map.put("email", usuarioModel.getEmail());
            map.put("password", usuarioModel.getPassword());
            map.put("login",usuarioModel.getLogin());
            map.put("verificacion", usuarioModel.getVerificacion());
            map.put("codestado", usuarioModel.getCodEstado());
        }

        return map;
    }

    public Map genCity(LoginModel loginModel){
        Map<String, Object> map = new HashMap<>();
        map.put("loggin",loginModel.getUsername());
        map.put("password",loginModel.getPassword());

        return map;
    }

}
