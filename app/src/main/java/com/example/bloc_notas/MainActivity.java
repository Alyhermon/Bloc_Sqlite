    package com.example.bloc_notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bloc_notas.DB.Conexion;

    public class MainActivity extends AppCompatActivity {

        EditText id, titulo, cuerpo;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            id = findViewById(R.id.id);
            titulo = findViewById(R.id.txt_titulo);
            cuerpo = findViewById(R.id.txt_cuerpo);
        }

        //Metodos Registrar

        public void registrar(View v) {

            Conexion admin = new Conexion(this, "Administracion", null, 1);
            SQLiteDatabase BaseDatos = admin.getWritableDatabase();

            String codigo = id.getText().toString();
            String _titulo = titulo.getText().toString();
            String _cuerpo = cuerpo.getText().toString();

            if (!codigo.isEmpty() && !_titulo.isEmpty() && !_cuerpo.isEmpty()) {
                ContentValues registro = new ContentValues();
                registro.put("codigo", codigo);
                registro.put("titulo", _titulo);
                registro.put("cuerpo", _cuerpo);

                BaseDatos.insert("articulo", null, registro);

                BaseDatos.close();

                id.setText("");
                titulo.setText("");
                cuerpo.setText("");

                Toast.makeText(this, "Ha sido Guardado exitosamente", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Debes de Llenar todos los campos", Toast.LENGTH_LONG).show();
            }

        }


            //Metodo para consultar

            public void Buscar (View v){
                Conexion admin = new Conexion(this, "administracion", null, 1);

                SQLiteDatabase BaseDatos = admin.getWritableDatabase();

                String codigo = id.getText().toString();

                if (!codigo.isEmpty()){
                    Cursor fila  = BaseDatos.rawQuery
                            ("select titulo, cuerpo from notas where id = " + codigo, null);

                    if (fila.moveToFirst()){
                        titulo.setText(fila.getString(0));
                        cuerpo.setText(fila.getString(1));
                        BaseDatos.close();

                    }else{
                        Toast.makeText(this, "No existe la Nota", Toast.LENGTH_LONG).show();
                        BaseDatos.close();

                    }

                }else{
                    Toast.makeText(this, "Debes de Introducir el Codigo", Toast.LENGTH_LONG).show();
                }

            }


            //Metodo Para Eliminar una Nota
        public void Eliminar(View V){
            Conexion admin = new Conexion
                    (this, "administracion", null, 1);

            SQLiteDatabase BaseDatos = admin.getWritableDatabase();

            String codigo = id.getText().toString();
            if (!codigo.isEmpty()){
                int cantidad = BaseDatos.delete("notas", "id= " + id, null);
                BaseDatos.close();
                id.setText("");
                titulo.setText("");
                cuerpo.setText("");

                if (cantidad == 1){
                    Toast.makeText(this, "Nota eliminada exitosamente", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(this, "La nota no existe", Toast.LENGTH_LONG).show();
                }
            }else
            {
                Toast.makeText(this, "Debes de Introducir el Codigo", Toast.LENGTH_LONG).show();
            }

        }

        //Metodo Modificar
        public void Modificar(View V){
            Conexion admin = new Conexion
                    (this, "administracion", null, 1);

            SQLiteDatabase BaseDatos = admin.getWritableDatabase();

            String codigo = id.getText().toString();

            String _titulo = titulo.getText().toString();

            String _cuerpo = cuerpo.getText().toString();

            if (!codigo.isEmpty() && !_titulo.isEmpty() && !_cuerpo.isEmpty()){

                ContentValues registro = new ContentValues();
                registro.put("codigo", codigo);
                registro.put("titulo", _titulo);
                registro.put("cuerpo", _cuerpo);

                int cantidad = BaseDatos.update("articulo", registro, "id = " + codigo, null);
                BaseDatos.close();
                if (cantidad == 1){
                    Toast.makeText(this, "Articulo Modificaddo Correctamente", Toast.LENGTH_LONG).show();


                }else{
                    Toast.makeText(this, "Debes llenar Todos los campos", Toast.LENGTH_LONG).show();
                }
            }else
            {
                Toast.makeText(this, "Debes de llenar todos los campos", Toast.LENGTH_LONG).show();
            }

        }

    }