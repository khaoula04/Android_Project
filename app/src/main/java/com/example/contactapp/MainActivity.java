package com.example.contactapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {



    EditText edName,edNumber,edEmail;
    Button addButton;
    ContactHelper contactHelper;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        edName = findViewById(R.id.edName);
        edNumber = findViewById(R.id.edNumber);
        edEmail = findViewById(R.id.edEmail);
        addButton = findViewById(R.id.addBtn);
        floatingActionButton = findViewById(R.id.floatingId);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(MainActivity.this, MainActivity2.class));


            }
        });

        contactHelper = new ContactHelper(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edName.length() > 0 && edNumber.length() > 0 && edEmail.length() > 0) {

                    contactHelper.insertData(edName.getText().toString(), edNumber.getText().toString(), edEmail.getText().toString());

                    edName.setText("");
                    edNumber.setText("");
                    edEmail.setText("");

                    Toast.makeText(MainActivity.this, "The contact is Successfully Added!", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(MainActivity.this, "Veuillez entrer un element", Toast.LENGTH_SHORT).show();


                }





            }
        });











    }
}