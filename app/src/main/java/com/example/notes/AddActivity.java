package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class AddActivity extends AppCompatActivity {

    TextView noteTitle_input,
            noteText_input;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        noteTitle_input = findViewById(R.id.editTitle1);
        noteText_input = findViewById(R.id.multiText1);
        addButton = findViewById(R.id.ButtonAdd);

        addButton.setOnClickListener((view) ->  {   // OnClick
            MyDataBaseHelper myDB = new MyDataBaseHelper(AddActivity.this);

            myDB.addNote(noteTitle_input.getText().toString().trim(),
                    noteText_input.getText().toString().trim());
            Toast.makeText(AddActivity.this, getString(R.string.ToastSuccess), LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}