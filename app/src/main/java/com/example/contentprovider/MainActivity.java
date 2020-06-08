package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button btnActualizarUpdate;
    private Button btnInsertar;
    private Button btnEliminar;
    private TextView txtResultados;
    private EditText txtEliminar;
    String[] projection = new String[] {
            MiProveedorContenido.Clientes._ID,
            MiProveedorContenido.Clientes.COL_NOMBRE,
            MiProveedorContenido.Clientes.COL_TELEFONO,
            MiProveedorContenido.Clientes.COL_EMAIL };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnActualizarUpdate =(Button)findViewById(R.id.btnActualizarUpdate);
        btnInsertar=(Button)findViewById(R.id.btnInsertar);
        btnEliminar=(Button)findViewById(R.id.btnEliminar);
        txtResultados=(TextView)findViewById(R.id.txtResultados);
        txtEliminar=(EditText)findViewById(R.id.txtEliminar);
        ActualizarTabla();


        btnActualizarUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatos();
                ActualizarTabla();
            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertandoNuevosBoton();
                ActualizarTabla();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminandoRegistrosBoton(txtEliminar.getText().toString());
                ActualizarTabla();
            }
        });

    }

    private void updateDatos() {
        ContentValues values = new ContentValues();

        values.put(MiProveedorContenido.Clientes.COL_NOMBRE, "ActualizadoCliente" + txtEliminar.getText().toString());
        values.put(MiProveedorContenido.Clientes.COL_TELEFONO, ""+ new Random().nextInt(1000));
        values.put(MiProveedorContenido.Clientes.COL_EMAIL, "nuevoActualizado@email.com");

        ContentResolver cr = getContentResolver();
        String cad="='Cliente"+txtEliminar.getText().toString()+"'";
        cr.update(MiProveedorContenido.CONTENT_URI,values,MiProveedorContenido.Clientes.COL_NOMBRE+cad,null);
        Toast.makeText(this,"Actualizando Valor",Toast.LENGTH_LONG).show();
    }

    private void eliminandoRegistrosBoton(String value) {
        ContentResolver cr = getContentResolver();
        String cad="='Cliente"+value+"'";
        cr.delete(MiProveedorContenido.CONTENT_URI,
                MiProveedorContenido.Clientes.COL_NOMBRE + cad, null);
        Toast.makeText(this,"Registro Eliminado!",Toast.LENGTH_LONG).show();
    }


    private void ActualizarTabla(){
        Uri clientesUri =  MiProveedorContenido.CONTENT_URI;
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(clientesUri,
                projection, //Columnas a devolver
                null,
                null,
                null);


        if (cur.moveToFirst())
        {
            String nombre;
            String telefono;
            String email;

            int colNombre = cur.getColumnIndex(MiProveedorContenido.Clientes.COL_NOMBRE);
            int colTelefono = cur.getColumnIndex(MiProveedorContenido.Clientes.COL_TELEFONO);
            int colEmail = cur.getColumnIndex(MiProveedorContenido.Clientes.COL_EMAIL);
            txtResultados.setText("");
            do
            {
                nombre = cur.getString(colNombre);
                telefono = cur.getString(colTelefono);
                email = cur.getString(colEmail);

                txtResultados.append(nombre + " - " + telefono + " - " + email + "\n");

            } while (cur.moveToNext());
        }
    }

    public void insertandoNuevosBoton(){
        Random num=new Random();
        ContentValues values = new ContentValues();

        values.put(MiProveedorContenido.Clientes.COL_NOMBRE, "NuevoCliente" + num.nextInt(1000));
        values.put(MiProveedorContenido.Clientes.COL_TELEFONO, ""+num.nextInt(1000));
        values.put(MiProveedorContenido.Clientes.COL_EMAIL, "nuevo@email.com");

        ContentResolver cr = getContentResolver();

        cr.insert(MiProveedorContenido.CONTENT_URI, values);
        Toast.makeText(this,"Insertando nuevo valor",Toast.LENGTH_LONG).show();
    }

}
