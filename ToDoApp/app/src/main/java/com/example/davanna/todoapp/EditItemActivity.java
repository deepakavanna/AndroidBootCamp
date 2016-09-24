package com.example.davanna.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(getIntent().getStringExtra("textBox"));
    }



    public void onSave(View v) {
        EditText editText = (EditText) findViewById(R.id.editText);

        Intent data = new Intent();
        // Pass relevant data back as a result
        int position = getIntent().getIntExtra("position", -1);
        String str = editText.getText().toString();
        data.putExtra("textBox", editText.getText().toString());
        data.putExtra("position",getIntent().getIntExtra("position", -1));
        data.putExtra("resultCode", 200);
        // Activity finished ok, return the data
        setResult(200, data); // set result code and bundle data for response
        finish();

    }

}
