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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scuev.trabajointermediopeliculas.utils.PeliculasSQLiteHelper;

public class RegistroActivity extends AppCompatActivity {

    Button registrar;
    EditText usuario;
    EditText clave;
    EditText nombre;
    EditText apellidos;
    TextView regresarLogin;
    PeliculasSQLiteHelper dbHelper;
    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        registrar = (Button) findViewById(R.id.btn_registrar);
        usuario = (EditText) findViewById(R.id.registro_usuario);
        clave = (EditText) findViewById(R.id.registro_clave);
        nombre = (EditText) findViewById(R.id.registro_nombre);
        apellidos = (EditText) findViewById(R.id.registro_apellidos);

        regresarLogin = (TextView) findViewById(R.id.regresoLogin);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                dbHelper = new PeliculasSQLiteHelper(RegistroActivity.this);
                db = dbHelper.getWritableDatabase();

                if(db != null){

                    dbHelper.guardarUsuario(db,usuario.getText().toString(),nombre.getText().toString(),apellidos.getText().toString()
                            ,clave.getText().toString());
                }

                db.close();
                dbHelper.close();

                sp.edit().putBoolean("logged",true).apply();
                sp.edit().putString("usuarioLogeado",usuario.getText().toString()).apply();
               // sp.edit().putInt("usuarioId",usuarioId).apply();
                goToMainActivity();

                Toast toast1 = Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT);
                toast1.setGravity(Gravity.CENTER,0, 0);
                toast1.show();


            }
        });
    }


    public void onClick(View v) {
        Intent i = new Intent(this,LogonActivity.class);
        startActivity(i);
    }

    public void goToMainActivity(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
