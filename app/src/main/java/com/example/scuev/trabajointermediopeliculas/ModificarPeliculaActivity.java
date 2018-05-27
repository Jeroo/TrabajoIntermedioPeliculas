package com.example.scuev.trabajointermediopeliculas;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.scuev.trabajointermediopeliculas.utils.PeliculasSQLiteHelper;

import java.util.Calendar;

public class ModificarPeliculaActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Widgets
    EditText etFecha;
    ImageButton ibObtenerFecha;
    EditText titulo;
    EditText descripcion;
    EditText actorPrincipal;
    EditText ciudad;
    Button cancelar;
    Button modificar;

    //Variable de almacenamiento
    PeliculasSQLiteHelper dbHelper;
    SQLiteDatabase db;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_pelicula);



        //Widget EditText donde se mostrara la fecha obtenida
        etFecha = (EditText) findViewById(R.id.et_mostrar_fecha_picker_modificar);
        //Widget ImageButton del cual usaremos el evento clic para obtener la fecha
        ibObtenerFecha = (ImageButton) findViewById(R.id.ib_obtener_fecha_modificar);
        //Evento setOnClickListener - clic
        ibObtenerFecha.setOnClickListener(this);

        modificar = (Button) findViewById(R.id.boton_modificar);
        cancelar = (Button) findViewById(R.id.boton_cancelar_modificar);
        titulo = (EditText) findViewById(R.id.agregar_titulo_modificar);
        descripcion = (EditText) findViewById(R.id.agregar_descripcion_modificar);
        actorPrincipal = (EditText) findViewById(R.id.agregar_actor_modificar);
        ciudad = (EditText) findViewById(R.id.agregar_ciudad_modificar);

        sp = getSharedPreferences("objPeliculaModificar", Context.MODE_PRIVATE);
        int peliculaId = sp.getInt("peliculaId",0);

        if(peliculaId > 0){

            dbHelper = new PeliculasSQLiteHelper(ModificarPeliculaActivity.this);
            db = dbHelper.getReadableDatabase();
            if(db != null) {

                Cursor fila = dbHelper.obtenerPeliculaPorId(db,peliculaId);

                if (fila.moveToFirst()){

                    do {
                            titulo.setText(fila.getString(2));
                            descripcion.setText(fila.getString(3));
                            actorPrincipal.setText(fila.getString(4));
                            ciudad.setText(fila.getString(5));
                            etFecha.setText(fila.getString(6));

                            break;


                    }while (fila.moveToNext());
                }


            }

        }

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModificarPeliculaActivity.this,PeliculasActivity.class);
                startActivity(i);
            }
        });

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                sp = getSharedPreferences("objPeliculaModificar", Context.MODE_PRIVATE);
                dbHelper = new PeliculasSQLiteHelper(ModificarPeliculaActivity.this);
                db = dbHelper.getWritableDatabase();
                int usuarioid = sp.getInt("usuarioId",0);
                int peliculaId = sp.getInt("peliculaId",0);
                byte[] sevenItems = new byte[] { 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20 };//etFecha.getText().toString()

                if(db != null){


                    if(!TextUtils.isEmpty(titulo.getText().toString()) && !TextUtils.isEmpty(descripcion.getText().toString())
                            && !TextUtils.isEmpty(actorPrincipal.getText().toString())  && !TextUtils.isEmpty(ciudad.getText().toString())
                            && !TextUtils.isEmpty(etFecha.getText().toString())){


                        dbHelper.actualizarPelicula(db,peliculaId,usuarioid,titulo.getText().toString(),descripcion.getText().toString()
                                ,actorPrincipal.getText().toString(),ciudad.getText().toString(),etFecha.getText().toString(),sevenItems);

                        db.close();
                        dbHelper.close();

                        goToPeliculasActivity();



                    }else {

                        Toast toast1 = Toast.makeText(getApplicationContext(), "Faltan campos por completar, favor verificar", Toast.LENGTH_SHORT);
                        toast1.setGravity(Gravity.CENTER,0, 0);
                        toast1.show();

                    }

                }


            }
        });
    }

    public void goToPeliculasActivity(){
        Intent i = new Intent(this,PeliculasActivity.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_obtener_fecha_modificar:
                obtenerFecha();
                break;
        }
    }

    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                etFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


                Toast toast1 = Toast.makeText(getApplicationContext(), "Fecha Seleccionada: "+etFecha.getText().toString(), Toast.LENGTH_SHORT);
                toast1.setGravity(Gravity.CENTER,0, 0);
                toast1.show();


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();


    }

}
