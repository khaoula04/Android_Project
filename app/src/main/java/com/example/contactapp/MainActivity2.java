package com.example.contactapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText edSearch;
    ArrayList<ContactModel> contactModelArrayList = new ArrayList<>();
    ContactHelper contactHelper;
    ContactAdepter adepter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        edSearch = findViewById(R.id.edSearch);

        // NEW: Initialize export button
        findViewById(R.id.btnExport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportContacts();
            }
        });

        // RecyclerView setup
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Database helper
        contactHelper = new ContactHelper(this);

        // Load initial data
        loadData("");

        // Setup adapter
        adepter = new ContactAdepter(contactModelArrayList, this);
        recyclerView.setAdapter(adepter);

        // Live filtering with text listener
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadData(String keyword) {
        contactModelArrayList.clear();
        Cursor cursor = contactHelper.showData();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                String number = cursor.getString(2);
                String email = cursor.getString(3);
                int id = cursor.getInt(0);

                // If no filter or name matches keyword
                if (keyword.isEmpty() || name.toLowerCase().contains(keyword.toLowerCase())) {
                    contactModelArrayList.add(new ContactModel(name, number, email, id));
                }
            }
            cursor.close();
        }

        if (adepter != null) {
            adepter.notifyDataSetChanged(); // Refresh the adapter after filtering
        }
    }

    private void exportContacts() {
        String contactsText = contactHelper.getAllContactsAsText();
        if (contactsText.isEmpty()) {
            Toast.makeText(this, "No contacts to export.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Exported Contacts");
        shareIntent.putExtra(Intent.EXTRA_TEXT, contactsText);
        startActivity(Intent.createChooser(shareIntent, "Share contacts via"));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity2.this, MainActivity.class));
        super.onBackPressed();
    }
}
