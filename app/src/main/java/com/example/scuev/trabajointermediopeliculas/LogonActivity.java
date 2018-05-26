package com.example.scuev.trabajointermediopeliculas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogonActivity extends AppCompatActivity {

    Button login;
    EditText usuario;
    EditText clave;
    TextView registrarse;

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);

        login = (Button) findViewById(R.id.btnLogin);
        usuario = (EditText) findViewById(R.id.txtUsuario);
        clave = (EditText) findViewById(R.id.txtClave);
        registrarse = (TextView) findViewById(R.id.txtRegistrarse);
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().putBoolean("logged",true).apply();
                sp.edit().putString("usuario",usuario.getText().toString()).apply();
                goToMainActivity();
            }
        });
    }


    public void goToMainActivity(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
