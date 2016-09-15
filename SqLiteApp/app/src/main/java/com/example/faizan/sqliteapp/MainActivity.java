package com.example.faizan.sqliteapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editSurName, editMarks, editId ;
    Button buttonAddData;
    Button buttonViewAll;
    Button buttonUpdate;
    Button buttonDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName=(EditText)findViewById(R.id.editText_Name);
        editSurName=(EditText)findViewById(R.id.editText_SurName);
        editMarks=(EditText)findViewById(R.id.editText_Marks);
        editId=(EditText) findViewById(R.id.editText_Id);
        buttonAddData=(Button) findViewById(R.id.button_add);
        buttonViewAll=(Button) findViewById(R.id.button_viewAll);
        buttonUpdate=(Button) findViewById(R.id.button_update);
        buttonDelete=(Button) findViewById(R.id.button_delete);
        AddData();
        ViewData();
        updateTable();
        DeleteRecord();
    }
    public void AddData() {
        buttonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editName.getText().toString(),editSurName.getText().toString(),
                        Integer.parseInt(editMarks.getText().toString()));
                if (isInserted == true)
                    Toast.makeText(v.getContext(),"Data has been inserted successfully",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(v.getContext(),"Data has not been inserted successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ViewData(){
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = myDb.getAllData();
                if (result.getCount() == 0){
                    ShowMessage("Error","No data found in table");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()) {
                    buffer.append("Id :"+result.getString(0)+"\t" +"Name : "+result.getString(1)+"\t"
                            +"\t"+"SurName :"+result.getString(2)+"\t" +"Marks :"+result.getString(3)+"\n\n");
                }
                ShowMessage("Student Table Data",buffer.toString());
            }
        });
    }

    public void ShowMessage(String title, String message) {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updateTable() {
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = myDb.updateData(editId.getText().toString(),editName.getText().toString()
                        ,editSurName.getText().toString(),editMarks.getText().toString());
                if (result == true)
                    Toast.makeText(v.getContext(),"Data has been updated successfully",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(v.getContext(),"Data has not been updated successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void DeleteRecord() {
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer result = myDb.deleteData(editId.getText().toString());
                if (result > 0)
                    Toast.makeText(v.getContext(),"Data has been Deleted successfully",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(v.getContext(),"Data has not been Deleted successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
