package org.sc.cbm.e193.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Log;

import org.sc.cbm.e193.BuildConfig;
import org.sc.cbm.e193.pojo.Cidade;
import org.sc.cbm.e193.pojo.Servidor_193;
import org.sc.cbm.e193.pojo.Usuario;

public class Globals {

    public static final String SENDER_ID = "559090531786";
    public static final String REQ_LISTA_CIDADES = BuildConfig.serverCBM+BuildConfig.reqListaCidades;
    public static final String REQ_AUTENTICACAO = BuildConfig.serverCBM+BuildConfig.reqAutenticacao;
    public static final String REQ_SELECT_SERVER = BuildConfig.serverCBM+BuildConfig.reqSelectServidor;
    public static final String REQ_VERIFICA_USUARIO_EXISTE = BuildConfig.serverCBM+BuildConfig.reqVerificaUsuarioExiste;

    public static final String PREF_FILE_NAMES = "PREF_FILE_NAMES";

    private static String TAG = Globals.class.getName();
    private static final String SERVER_CBM = BuildConfig.serverCBM;

    private static final String PREF_ACCESS_TOKEN = "PREF_ACCESS_TOKEN";
    private static final String PREF_TIME_LOGIN = "PREF_TIME_LOGIN";
    private static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String DIRECTORY_SIZE = "DIRECTORY_SIZE";
    private static final String AUTH = "AUTH";
    private static String urlSocial = "";
    private static Cidade cidadeSelecionada = null;
    private static Servidor_193 servidor = null;
    private static Usuario usuario = null;
    private static String accessToken = null;
    private static Bitmap userImage = null;
    private static String userName = null;
    private static String userPwd = null;

    public synchronized static String getRegistrationId(Context context) {
        final SharedPreferences sharedPrefs = context.getSharedPreferences("AUTH", Context.MODE_PRIVATE);
        String registrationId = sharedPrefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = sharedPrefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public synchronized static void setAccessToken(Context context, String token) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(AUTH, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(PREF_ACCESS_TOKEN, token);
        editor.putLong(PREF_TIME_LOGIN, java.lang.System.currentTimeMillis());
        editor.commit();
        accessToken = token;
        if (accessToken == null) {
            setUserImage(null);
        }
    }

    public static void setUserImage(Bitmap userImage) {
        Globals.userImage = userImage;
    }

    public synchronized static void storeRegistrationId(Context context, String regId) {
        final SharedPreferences sharedPrefs = context.getSharedPreferences("AUTH", Context.MODE_PRIVATE);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    public static Cidade getCidadeSelecionada() {
        return cidadeSelecionada;
    }

    public static void setCidadeSelecionada(Cidade cidadeSelecionada) {
        Globals.cidadeSelecionada = cidadeSelecionada;
    }

    public static String getUrlSocial() {
        return urlSocial;
    }

    public static void setUrlSocial(String urlSocial) {
        Globals.urlSocial = urlSocial;
    }

    public static String getUserPwd() {
        return userPwd;
    }

    public static void setUserPwd(String userPwd) {
        Globals.userPwd = userPwd;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        Globals.userName = userName;
    }

    public static Servidor_193 getServidor() {
        return servidor;
    }

    public static void setServidor(Servidor_193 servidor) {
        Globals.servidor = servidor;
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Globals.usuario = usuario;
    }

}
