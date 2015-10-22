package org.sc.cbm.e193.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.sc.cbm.e193.BuildConfig;
import org.sc.cbm.e193.R;
import org.sc.cbm.e193.pojo.Cidade;
import org.sc.cbm.e193.pojo.Servidor_193;
import org.sc.cbm.e193.util.ConexaoHttpClient;
import org.sc.cbm.e193.util.Globals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CidadeActivity extends Activity {

    private Cidade cidadeSelected;
    private ProgressDialog pDialog;
    private List<NameValuePair> params = new ArrayList<>();
    private List<Cidade> cidades = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private String json;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_cidade);

        new AsyncTask<Void, Void, List>() {

            @Override
            protected void onPreExecute() {
                pDialog = ProgressDialog.show(CidadeActivity.this,"Verificando cidades no E193", getString(R.string.please_hold), true);
                params.add(new BasicNameValuePair("request", BuildConfig.reqListaCidades));
            }

            @Override
            protected List doInBackground(Void... unused) {
                try {
                    json = ConexaoHttpClient.executaHttpPost(Globals.REQ_LISTA_CIDADES, params);
                    Gson gson = new Gson();
                    Cidade[] listCidades = gson.fromJson(json, Cidade[].class);
                    cidades = Arrays.asList(listCidades);
                } catch (Exception e) {
                    e.printStackTrace();
                    cidades.add(new Cidade(0,"ERRO!"));
                }
                return cidades;
            }

            @Override
            protected void onPostExecute(List aVoid) {
                super.onPostExecute(aVoid);
                Spinner sp_cidades = (Spinner) findViewById(R.id.sp_cidades);
                ArrayAdapter<Cidade> spinnerArrayAdapter = new ArrayAdapter<>(CidadeActivity.this, R.layout.spinner_item, cidades);
                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
                sp_cidades.setAdapter(spinnerArrayAdapter);
                sp_cidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                        cidadeSelected = (Cidade)parent.getItemAtPosition(posicao);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                try {
                    pDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                }
                findViewById(R.id.btn_next).setEnabled(true);
            }
        }.execute();

        final Button btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.setCidadeSelecionada(cidadeSelected);
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected void onPreExecute() {
                        pDialog = ProgressDialog.show(CidadeActivity.this, "Processando...", getString(R.string.please_hold), true);
                        params.add(new BasicNameValuePair("request", BuildConfig.reqSelectServidor));
                        params.add(new BasicNameValuePair("id_cidade", String.valueOf(cidadeSelected.getId_cidade())));
                    }

                    @Override
                    protected Void doInBackground(Void... unused) {
                        try {
                            json = ConexaoHttpClient.executaHttpPost(Globals.REQ_SELECT_SERVER, params);
                            Gson gson = new Gson();
                            Servidor_193 servidor = gson.fromJson(json, Servidor_193.class);
                            Globals.setServidor(servidor);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                    }
                }.execute();

                Intent intent = new Intent(CidadeActivity.this, LoginActivity.class);
                startActivity(intent);
                CidadeActivity.this.finish();
            }
        });

        final Button btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CidadeActivity.this.finish();
            }
        });
    }
}