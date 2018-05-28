package com.example.scuev.trabajointermediopeliculas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scuev.trabajointermediopeliculas.utils.PeliculasSQLiteHelper;

public class LogonActivity extends AppCompatActivity {

    Button login;
    EditText usuario;
    EditText clave;
    TextView registrarse;
    PeliculasSQLiteHelper dbHelper;
    SQLiteDatabase db;
    SharedPreferences sp;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);

        login = (Button) findViewById(R.id.btnLogin);
        usuario = (EditText) findViewById(R.id.txtUsuario);
        clave = (EditText) findViewById(R.id.txtClave);
        registrarse = (TextView) findViewById(R.id.txtRegistrarse);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(usuario.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(clave.getWindowToken(), 0);

       /* InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(usuario, InputMethodManager.SHOW_IMPLICIT);
        imm.showSoftInput(clave, InputMethodManager.SHOW_IMPLICIT);*/


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinner.setVisibility(View.VISIBLE);
                sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                dbHelper = new PeliculasSQLiteHelper(LogonActivity.this);
                db = dbHelper.getReadableDatabase();

                String nombreCompleto = "";
                String usuarioLogeado = "";
                int usuarioId = 0;

                if(db != null){

                    Cursor fila = dbHelper.login(db,usuario.getText().toString().toLowerCase(),clave.getText().toString());

                    if (fila.moveToFirst()){

                        do {
                            usuarioId = fila.getInt(0);
                            usuarioLogeado = fila.getString(1);
                            nombreCompleto = fila.getString(2)+" "+fila.getString(3);
                            break;

                        }while (fila.moveToNext());
                    }

                }

                 db.close();
                 dbHelper.close();

               if (usuarioLogeado != "" && usuarioLogeado != null){

                    sp.edit().putBoolean("logged",true).apply();
                    sp.edit().putString("usuarioLogeado",usuarioLogeado).apply();
                    sp.edit().putInt("usuarioId",usuarioId).apply();

                   Toast toast1 = Toast.makeText(getApplicationContext(), "Usuario logeado correctamente", Toast.LENGTH_SHORT);
                   toast1.setGravity(Gravity.CENTER,0, 0);
                   toast1.show();
                   goToMainActivity();

                }else{

                    Toast toast1 = Toast.makeText(getApplicationContext(), "Usuario o Clave Incorrecto", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER,0, 0);
                    toast1.show();
                }

            }
        });
    }

    public void onClick(View v) {
        Intent i = new Intent(this,RegistroActivity.class);
        startActivity(i);
    }


    public void goToMainActivity(){

        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
