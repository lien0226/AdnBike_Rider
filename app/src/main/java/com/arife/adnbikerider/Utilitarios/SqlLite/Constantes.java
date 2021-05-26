package com.arife.adnbikerider.Utilitarios.SqlLite;

public class Constantes {

    public static final String NOMBRE_TABLE ="ADNRIDE";
    public static final String TABLE_POINTS = "POINTS";
    public static final String CAMPO_ID = "ID";
    public static final String CAMPO_ID_RUTA ="IDRUTA";
    public static final String CAMPO_LONGITUD = "LONGITUD";
    public static final String CAMPO_LATITUD = "LATITUD";
    public static final String TIMESECOND = "TIME";
    public static final String VELOCIDAD = "VELOCIDAD";
    public static final String KMR = "KMR";
    public static final String COUNT = "COUNT";
    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_POINTS+" ("+ CAMPO_ID+" INTEGER,"+CAMPO_ID_RUTA+" INTEGER,"+CAMPO_LONGITUD+" DOUBLE,"+CAMPO_LATITUD+" DOUBLE,"+TIMESECOND+" TEXT,"+VELOCIDAD+" DOUBLE,"+KMR+" TEXT,"+COUNT+" TEXT)";

}
