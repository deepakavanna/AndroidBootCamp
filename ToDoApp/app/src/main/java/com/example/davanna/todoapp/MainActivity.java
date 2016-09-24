package com.example.davanna.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> listItems = new ArrayList<String>();
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems = null;
    private int REQUEST_CODE = 20;
    private int RESULT_OK = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        lvItems = (ListView) findViewById(R.id.lvItems);

        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        lvItems.setAdapter(itemsAdapter);
        listItems.add("First Item");
        listItems.add("Second Item");
        setupListViewListener();
    }

    private void setupListViewListener() {

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                listItems.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                startEditActivity(pos);
            }

        });
    }

    private void startEditActivity(int pos) {
        Intent i = new Intent(this, EditItemActivity.class);
        i.putExtra("position", pos); // pass arbitrary data to launched activity
        i.putExtra("textBox", listItems.get(pos));
        startActivityForResult(i, REQUEST_CODE);
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        itemsAdapter.add(etNewItem.getText().toString());
        etNewItem.setText("");
        writeItems();
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            listItems = new ArrayList<String>(FileUtils.readLines(todoFile));
        }
        catch(IOException e) {
            listItems = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, listItems);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String text = data.getExtras().getString("textBox");
            int code = data.getExtras().getInt("code", 0);
            int position = data.getExtras().getInt("position", -1);

            if (position != -1) {
                listItems.set(position, text);

                itemsAdapter.notifyDataSetChanged();
                writeItems();

            }
            // Toast the name to display temporarily on screen
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
    }
}
