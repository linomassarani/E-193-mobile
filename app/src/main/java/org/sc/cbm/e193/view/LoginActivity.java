package org.sc.cbm.e193.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import org.sc.cbm.e193.BuildConfig;
import org.sc.cbm.e193.R;
import org.sc.cbm.e193.pojo.Servidor_193;
import org.sc.cbm.e193.pojo.Usuario;
import org.sc.cbm.e193.util.ConexaoHttpClient;
import org.sc.cbm.e193.util.Globals;
import org.sc.cbm.e193.util.ManageSharedPreferences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class LoginActivity extends Activity {

    public static String TAG = LoginActivity.class.getName();
    AutoCompleteTextView txtId;
    EditText txtPwd;
    ProgressDialog pDialog;
    public Set<String> logins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button btn_ajuda = (Button) findViewById(R.id.btn_ajuda);
        btn_ajuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                builder.setTitle(getString(R.string.title_help))
                        .setMessage(getString(R.string.help_text))
                        .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                }
            });

                txtId = (AutoCompleteTextView) findViewById(R.id.txtLoginUser);
        try{
            logins = ManageSharedPreferences.getSetStringFromSharedPreference(LoginActivity.this, Globals.PREF_FILE_NAMES, "login");

            Iterator it = logins.iterator();
            int cont = 0;
            String[] arr = new String[logins.size()];
            while (it.hasNext()) {
                String aux = (String)it.next();
                arr[cont] = aux;
                cont++;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
            txtId.setAdapter(adapter);
        }catch (Exception e){

        }

               // txtId.setText(Login);

                txtPwd = (EditText) findViewById(R.id.txtLoginPassword);

                /**
                 * Appears a hack
                 * On login_activity I added
                 * android:focusable="true"
                 * android:focusableInTouchMode="true"
                 */
                txtId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            txtId.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    InputMethodManager keyboard = (InputMethodManager)
                                            getSystemService(Context.INPUT_METHOD_SERVICE);
                                    keyboard.showSoftInput(txtId, 0);
                                }
                            }, 800);
                        }
                    }
                });

        final CheckBox cbShowPassword = (CheckBox) findViewById(R.id.show_password);
        cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    txtPwd.setTransformationMethod(null);
                else
                    txtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        final ImageButton icon_face = (ImageButton) findViewById(R.id.icon_face);
        icon_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Globals.setUrlSocial("http://pt-br.facebook.com/CBMSC");
//                Intent intent = new Intent(LoginActivity.this, SocialActivity.class);
//                startActivity(intent);
            }
        });

        final ImageButton icon_twitter = (ImageButton) findViewById(R.id.icon_twitter);
        icon_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.setUrlSocial("http://twitter.com/CBMSC193");
//                Intent intent = new Intent(LoginActivity.this, SocialActivity.class);
//                startActivity(intent);
            }
        });

        final ImageButton icon_cbm = (ImageButton) findViewById(R.id.icon_cbm);
        icon_cbm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.setUrlSocial("http://portal.cbm.sc.gov.br/");
//                Intent intent = new Intent(LoginActivity.this, SocialActivity.class);
//                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return item.getItemId() == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public void makeLoginRequest(View view) {
        Authenticate auth = new Authenticate(getBaseContext());
        auth.execute();
    }

    public class Authenticate extends AsyncTask<Void, String, String> {

        private String retornoHttp = "";
        private Context context;
        private List<NameValuePair> params = new ArrayList<NameValuePair>();
        private String txtID= txtId.getText().toString();
        private String txtPwD= txtPwd.getText().toString();
        private boolean falhaConexaoLdap, falhaAutenticacaoLdap, falhaAutenticacaoE193 = false;

        public Authenticate(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            //ANTES DE EXECUTAR (JANELA)
            pDialog = ProgressDialog.show(LoginActivity.this, getString(R.string.login_in), getString(R.string.please_hold), true);
        }

        @Override
        protected String doInBackground(Void... unused) {
            try {
                String URL = Globals.REQ_AUTENTICACAO;
                params.add(new BasicNameValuePair("u", txtID));
                params.add(new BasicNameValuePair("p",txtPwD));

                retornoHttp = ConexaoHttpClient.executaHttpPost(URL, params);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (retornoHttp.equalsIgnoreCase("1")) {
                Globals.setUserName(txtID);
                Globals.setUserPwd(txtPwD);

                try {
                    logins = ManageSharedPreferences.getSetStringFromSharedPreference(LoginActivity.this, Globals.PREF_FILE_NAMES, "login");
                }catch (Exception e){}
                if (logins == null){
                    logins = new LinkedHashSet<String>();
                }
                logins.add(txtID);
                Iterator it = logins.iterator();

                while (it.hasNext()) {
                    String aux = (String)it.next();
                }

                ManageSharedPreferences.putInSharedPreferences(LoginActivity.this, Globals.PREF_FILE_NAMES, "login", logins);

                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("request", BuildConfig.reqVerificaUsuarioExiste));
                params.add(new BasicNameValuePair("ip_servidor", Globals.getServidor().getIp_servidor()));
                params.add(new BasicNameValuePair("nm_login", txtID));
                String json;
                try {
                    json = ConexaoHttpClient.executaHttpPost(Globals.REQ_VERIFICA_USUARIO_EXISTE, params);
                    Gson gson = new Gson();
                    /**
                     * Retornará false se não encontrar Usuário, então o método gson.fromJson() lancará uma exceção
                     */
                    Usuario usuario = gson.fromJson(json, Usuario.class);
                    Globals.setUsuario(usuario);
                } catch (Exception e) {
                    falhaAutenticacaoE193 = true;
                }

            }else{
                if (retornoHttp.equalsIgnoreCase("0")) {
                    falhaAutenticacaoLdap = true;
                } else {
                    falhaConexaoLdap = true;
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try{
                pDialog.dismiss();
                if(falhaAutenticacaoLdap){
                    Toast toast = Toast.makeText(getApplicationContext(), "Usuário ou senha incorretos", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 100);
                    toast.show();
                }else if(falhaConexaoLdap){
                    Toast toast = Toast.makeText(getApplicationContext(), "Problema ao conectar com o E193.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 100);
                    toast.show();
                }else if(falhaAutenticacaoE193){
                    Toast toast = Toast.makeText(getApplicationContext(), "Usuário não existe no E193", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 100);
                    toast.show();
                }else{
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

}
