package com.example.sqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editSurname, editMarks, editId;
    Button btnAddData;

    Button btnUpdate;
    Button btnDelete;
    TextView textViewShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        textViewShow = (TextView)findViewById(R.id.textView_show);
        editName = (EditText)findViewById(R.id.editText_name);
        editSurname = (EditText)findViewById(R.id.editText_surname);
        editMarks = (EditText)findViewById(R.id.editText_marks);
        editId = (EditText)findViewById(R.id.editText_id);
        btnAddData = (Button)findViewById(R.id.button_add);

        btnUpdate = (Button)findViewById(R.id.button_update);
        btnDelete = (Button)findViewById(R.id.button_delete);

        AddData();

        updateData();
        deleteData();
        resetData();


    }

    public void deleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Integer deletedRows = myDb.deleteData(editId.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Data has been deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data has not been deleted", Toast.LENGTH_LONG).show();
                        resetData();
                    }
                }

        );
    }


    public void updateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean isUpdated = myDb.updateData(editId.getText().toString(), editName.getText().toString(), editSurname.getText().toString(),editMarks.getText().toString());

                        if(isUpdated == true)
                            Toast.makeText(MainActivity.this, "Data is updated", Toast.LENGTH_LONG).show();


                        else
                            Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_LONG).show();

                        resetData();
                    }

                }
        );
    }


    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editName.getText().toString(), editSurname.getText().toString(), editMarks.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();

                        resetData();
                    }
                }
        );
    }



    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void resetData(){
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0){
            showMessage("Error", "Pas de donnée trouvés");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("ID : "+ res.getString(0)+ "\n");
            buffer.append("Nom de l'étudiant : "+ res.getString(1)+ "\n");
            buffer.append("Matière : "+ res.getString(2)+ "\n");
            buffer.append("Note : "+ res.getString(3)+ " / 20" + "\n\n");
        }
        //showMessage("Data", buffer.toString());
        textViewShow.setText(buffer.toString());
    }
}
