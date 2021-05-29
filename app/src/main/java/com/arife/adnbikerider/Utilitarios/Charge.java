package com.arife.adnbikerider.Utilitarios;

import com.arife.adnbikerider.AppData.Sesion;
import com.arife.adnbikerider.mvc.m.GroupModel;
import com.arife.adnbikerider.mvc.m.LoginModel;
import com.arife.adnbikerider.mvc.m.PerfilModel;
import com.arife.adnbikerider.mvc.m.RouteModel;
import com.arife.adnbikerider.mvc.m.UbicacionModel;
import com.arife.adnbikerider.mvc.m.UnionModel;
import com.arife.adnbikerider.mvc.m.UserModel;

import java.util.HashMap;
import java.util.Map;

public class Charge {

    public static String Link_Base = "http://181.224.251.170:9090/AdnRoutes/Routes/";
    public static String Base_get = "http://181.224.251.170:9090/AdnRoutes/Routes";
    public static String GetRoutePerson = Link_Base+"myroutes";
    public static String Base_img ="http://181.224.251.170:9090/asset/adnroutes/images/";
    public static String Base_img_prf ="http://181.224.251.170:9090/asset/adnroutes/perfil/";

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

    public Map genGroup(GroupModel groupModel){
        this.sesion = Sesion.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put("opcion",groupModel.getOpcion());
        map.put("id",String.valueOf(groupModel.getId()));
        map.put("nomgrupo",groupModel.getGroupName());
        map.put("desgrupo",groupModel.getGroupDescription());
        map.put("ubigeo",groupModel.getUbigeo());
        map.put("tgrupo",groupModel.getTipo());
        map.put("codestado",groupModel.getCodEstado());
        map.put("usuario",this.sesion.readSesion().getUsername());
        map.put("image",groupModel.getImage());

        return map;
    }

    public String genGetGroup(){
        String aurl="get_groups";
        this.sesion = Sesion.getInstance();

        String url =  Link_Base+aurl+"?usuario="+this.sesion.readSesion().getUsername();
        return url;
    }

    public String genGetRoutePerson(RouteModel routeModel){
        String aurl="myroutes";
        this.sesion = Sesion.getInstance();

        String url =  Link_Base+aurl+"?idruta="+routeModel.getId()+"&usuario="+this.sesion.readSesion().getUsername();

        return url;
    }

    public Map genRoute(RouteModel routeModel){
        this.sesion = Sesion.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put("opcion",routeModel.getOpcion());
        map.put("id",String.valueOf(routeModel.getId()));
        map.put("idgrupo",String.valueOf(routeModel.getIdGrupo()));
        map.put("nomruta",routeModel.getNameRuta());
        map.put("desruta",routeModel.getDescRuta());
        map.put("usuario",this.sesion.readSesion().getUsername());

        return map;
    }

    public String genGetRoute(GroupModel groupModel){
        String aurl="get_routesgroup";
        this.sesion = Sesion.getInstance();

        String url =  Link_Base+aurl+"?idgrupo="+groupModel.getId()+"&usuario="+this.sesion.readSesion().getUsername();
        return url;
    }

    public Map genUnion(UnionModel unionModel){
        this.sesion = Sesion.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put("opcion",unionModel.getOpcion());
        map.put("id",String.valueOf(unionModel.getId()));
        map.put("idruta",String.valueOf(unionModel.getIdRuta()));
        map.put("usuario",this.sesion.readSesion().getUsername());

        return map;
    }

    public Map genUbication(UbicacionModel ubicacionModel){
        this.sesion = Sesion.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put("opcion","N");
        map.put("id",String.valueOf(ubicacionModel.getIdUbicacion()));
        map.put("idruta",String.valueOf(ubicacionModel.getIdRuta()));
        map.put("latlong", ubicacionModel.getLatlong());
        map.put("usuario",this.sesion.readSesion().getUsername());

        return map;
    }

    public String genGetMyRoute(GroupModel groupModel){
        String aurl="get_routesgroup";
        this.sesion = Sesion.getInstance();

        String url =  Link_Base+aurl+"?idgrupo="+groupModel.getId()+"&usuario="+this.sesion.readSesion().getUsername();
        return url;
    }

    public String getGetPerfil(){
        String aurl="dat_perfil";
        this.sesion = Sesion.getInstance();

        String url =  Link_Base+aurl+"?usuario="+this.sesion.readSesion().getUsername();
        return url;
    }

    public Map genSavePerfil(PerfilModel perfilModel){
        this.sesion = Sesion.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put("opcion","N");
        map.put("id",String.valueOf(perfilModel.getId()));
        String name[] = (perfilModel.getNombres().split(" "));
        String last[] = (perfilModel.getApellidos().split(" "));
        String pnombre="",snombre="";
        String apaterno="",amaterno="";
        int a,b;
        if ((name.length)>1){
            a = (int) Math.ceil((float) (name.length)/2);
            for (int i=0;i<a;i++){
                pnombre += name[i]+" ";
            }
            for (int j=a; j<name.length; j++){
                snombre += name[j]+" ";
            }
            map.put("pnombre", pnombre.trim());
            map.put("snombre", snombre.trim());
        }

        if ((last.length)>1){
            a = (int) Math.ceil((float) (last.length)/2);
            for (int i=0;i<a;i++){
                apaterno += last[i]+" ";
            }
            for (int j=a; j<last.length; j++){
                amaterno += last[j]+" ";
            }
            map.put("apaterno", apaterno.trim());
            map.put("amaterno", amaterno.trim());
        }

        //map.put("pnombre",perfilModel.getNombres());
        //map.put("snombre", perfilModel.getNombres());
        //map.put("apaterno",perfilModel.getApellidos());
        //map.put("amaterno",perfilModel.getApellidos());
        map.put("celular",perfilModel.getCelular());
        map.put("direccion",perfilModel.getDireccion());
        map.put("image",perfilModel.getImagen());
        map.put("usuario",this.sesion.readSesion().getUsername());

        return map;
    }


}
