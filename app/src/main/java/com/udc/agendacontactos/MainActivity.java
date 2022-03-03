package com.udc.agendacontactos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et_identificacion, et_nombres, et_apellidos, et_telefono, et_email, et_direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_identificacion = (EditText) findViewById(R.id.et_identificacion);
        et_nombres = (EditText) findViewById(R.id.et_Nombre);
        et_apellidos = (EditText) findViewById(R.id.et_Apellido);
        et_telefono = (EditText) findViewById(R.id.et_telefono);
        et_email = (EditText) findViewById(R.id.et_Email);
        et_direccion = (EditText) findViewById(R.id.et_direccion);
    }

    // metodo para guardar

    public void Registrar(View view) {
        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper(this, "Contactos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String Identificacion = et_identificacion.getText().toString();
        String Nombre = et_nombres.getText().toString();
        String Apellidos = et_apellidos.getText().toString();
        String Telefono = et_telefono.getText().toString();
        String Email = et_email.getText().toString();
        String Direccion = et_direccion.getText().toString();

        if (!Identificacion.isEmpty() && !Nombre.isEmpty() && !Apellidos.isEmpty() && !Telefono.isEmpty() && !Email.isEmpty() && !Direccion.isEmpty()) {
            ContentValues Registro = new ContentValues();
            Registro.put("identificacion", Identificacion);
            Registro.put("nombres", Nombre);
            Registro.put("apellidos", Apellidos);
            Registro.put("telefono", Telefono);
            Registro.put("email", Email);
            Registro.put("direccion", Direccion);

            BaseDeDatos.insert("contactos", null, Registro);
            BaseDeDatos.close();

            et_identificacion.setText("");
            et_nombres.setText("");
            et_apellidos.setText("");
            et_telefono.setText("");
            et_email.setText("");
            et_direccion.setText("");

            Toast.makeText(this, "Registro Agregado Exitosamente", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Debes Llenar todos los Campos - No se Acepta Campos Vacios", Toast.LENGTH_LONG).show();
        }

    }

    // metodo para consultar un contacto

    public void Buscar(View view) {
        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper(this, "contactos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String Identificacion = et_identificacion.getText().toString();

        if (Identificacion.isEmpty()) {
            Cursor fila = BaseDeDatos.rawQuery
                    ("select nombres, apellidos, telefono, email, direccion from contactos where id =" + Identificacion, null);
            if (fila.moveToFirst()) {

                et_nombres.setText(fila.getString(0));
                et_apellidos.setText(fila.getString(1));
                et_telefono.setText(fila.getString(2));
                et_email.setText(fila.getString(3));
                et_direccion.setText(fila.getString(4));
                BaseDeDatos.close();
            } else {
                Toast.makeText(this, "ese articulo no existe", Toast.LENGTH_LONG).show();
                BaseDeDatos.close();
            }
        } else {
            Toast.makeText(this, "Debes llegar el campo Identificacion", Toast.LENGTH_LONG).show();
        }
    }

    // METODO PARA ELIMINAR
    public void Eliminar(View view) {
        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper(this, "contactos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String Identificacion = et_identificacion.getText().toString();
        if (!Identificacion.isEmpty()) {
            int cantidad = BaseDeDatos.delete("contactos", "id" + Identificacion, null);
            BaseDeDatos.close();

            et_identificacion.setText("");
            et_nombres.setText("");
            et_apellidos.setText("");
            et_telefono.setText("");
            et_email.setText("");
            et_direccion.setText("");

            if (cantidad == 1) {
                Toast.makeText(this, "Registro Eliminado Exitosamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Identificacion no existe en la base de datos", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "Debe Introducir la Identificacion", Toast.LENGTH_LONG).show();
        }
    }

    // metodo para actualizar un registro

    public void Actualizar(View view) {
        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper(this, "contactos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String Identificacion = et_identificacion.getText().toString();
        String Nombre = et_nombres.getText().toString();
        String Apellidos = et_apellidos.getText().toString();
        String Telefono = et_telefono.getText().toString();
        String Email = et_email.getText().toString();
        String Direccion = et_direccion.getText().toString();


        if (!Identificacion.isEmpty() && !Nombre.isEmpty() && !Apellidos.isEmpty() && !Telefono.isEmpty() && !Email.isEmpty() && !Direccion.isEmpty()) {

            ContentValues Registro = new ContentValues();
            Registro.put("identificacion", Identificacion);
            Registro.put("nombres", Nombre);
            Registro.put("apellidos", Apellidos);
            Registro.put("telefono", Telefono);
            Registro.put("email", Email);
            Registro.put("direccion", Direccion);

            int cantidad = BaseDeDatos.update("contactos", Registro, "id=" + Identificacion, null);
            BaseDeDatos.close();

            if (cantidad == 1) {
                Toast.makeText(this, "articulo actualizado Correctamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "no existe ese registro en la base de datos", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "debe introducir datos - no se permite datos vacios", Toast.LENGTH_LONG).show();
        }


    }


}