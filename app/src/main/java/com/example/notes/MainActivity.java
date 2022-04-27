package com.example.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatAddBut;

    MyDataBaseHelper myBD;
    ArrayList<String> note_id, note_title, note_text;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView1);
        floatAddBut = findViewById(R.id.floatAddBut1);

        /*-------------------------Jump to Adding Activity--------------------------------*/
        floatAddBut.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });
        /*--------------------------------------------------------------------------------*/

        myBD = new MyDataBaseHelper(MainActivity.this);
        note_id = new ArrayList<>();
        note_title = new ArrayList<>();
        note_text = new ArrayList<>();

        StoreDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this, this, note_id, note_title, note_text);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    /*---------------Refreshing MainActivity----------------- */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) recreate();
    }

    /*-----------Grabbing results from readAllData method to store them in Arrays-------------------*/
    void StoreDataInArrays(){
        Cursor cursor = myBD.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "There is no HowdyNote to read", Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                note_id.add(cursor.getString(0));
                note_title.add(cursor.getString(1));
                note_text.add(cursor.getString(2));
            }
        }
    }

    /*-----------------Menu for deleting All----------------------*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteAll){
            confirmDialogDelAll();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialogDelAll(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you wanna delete All?");
        builder.setPositiveButton("Yeah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDataBaseHelper myBD = new MyDataBaseHelper(MainActivity.this);
                myBD.deleteAll();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(MainActivity.this, "All the Notes were deleted Successfully!", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Mission failed Successfully)))", Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();    // Shows AlertDialog!
    }
}