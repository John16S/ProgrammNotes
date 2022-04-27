package com.example.notes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, text_input;       // For setting data from DB
    Button buttonUpdate, buttonDelete;
    String id, title, text;                 // For getting data from DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.editTitle2);
        text_input = findViewById(R.id.multiText2);
        buttonUpdate = findViewById(R.id.ButtonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);

        /*------First get and set data from DB to UpdateActivity-----*/
        getAndSetIntentData();

        /*------Setting titleName as a ActionBar name----------------*/
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*------Then call method updateData-----*/
                MyDataBaseHelper myBD = new MyDataBaseHelper(UpdateActivity.this);
                title = title_input.getText().toString().trim();
                text = text_input.getText().toString().trim();
                myBD.updateData(id, title, text);
                Toast.makeText(UpdateActivity.this, "Data Updated Successfully!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("text")){
            /*------Getting data from intent------*/
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            text = getIntent().getStringExtra("text");

            /*------Setting intent data------*/
            title_input.setText(title);
            text_input.setText(text);

        } else {
            Toast.makeText(this, "No data to update!", Toast.LENGTH_LONG).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + "?");
        builder.setMessage("Are you sure to delete " + title + "?");
        builder.setPositiveButton("Yeah, Baby)", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDataBaseHelper myBD = new MyDataBaseHelper(UpdateActivity.this);
                myBD.deleteOneRow(id);
                finish();
                Toast.makeText(UpdateActivity.this, "Note deleted Successfully!", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(UpdateActivity.this, "Mission failed Successfully)))", Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();    // Shows AlertDialog!
    }
}