package com.example.contactapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdepter extends RecyclerView.Adapter<ContactAdepter.Viewholder> {

    ArrayList<ContactModel> contactModelArrayList = new ArrayList<>();
    Context context;

    public ContactAdepter(ArrayList<ContactModel> contactModelArrayList, Context context) {
        this.contactModelArrayList = contactModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactAdepter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdepter.Viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(contactModelArrayList.get(position).getName());
        holder.number.setText(contactModelArrayList.get(position).getNumber());
        holder.email.setText(contactModelArrayList.get(position).getEmail());

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_dialog);

                EditText customName = dialog.findViewById(R.id.customName);
                EditText customNumber = dialog.findViewById(R.id.customNumber);
                EditText customEmail = dialog.findViewById(R.id.customEmail);
                Button customBtn = dialog.findViewById(R.id.customBtn);

                customName.setText(contactModelArrayList.get(position).getName());
                customNumber.setText(contactModelArrayList.get(position).getNumber());
                customEmail.setText(contactModelArrayList.get(position).getEmail());

                String iD = String.valueOf(contactModelArrayList.get(position).getId());

                customBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ContactHelper contactHelper = new ContactHelper(context);
                        contactHelper.updateData(customName.getText().toString(), customNumber.getText().toString(), customEmail.getText().toString(), iD);
                        Toast.makeText(context, "The contact is successfully Updated !", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, MainActivity2.class));
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactHelper contactHelper = new ContactHelper(context);
                Cursor cursor = contactHelper.showData();

                String iD = String.valueOf(contactModelArrayList.get(position).getId());

                new AlertDialog.Builder(context)
                        .setTitle("Supprimer element")
                        .setMessage("Etes-vous sure de vouloir supprimer l'element?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                contactHelper.deleteData(iD);
                                context.startActivity(new Intent(context, MainActivity2.class));
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView name, number, email;
        TextView delete, update;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);
            number = itemView.findViewById(R.id.tvNumber);
            email = itemView.findViewById(R.id.tvEmail);
            delete = itemView.findViewById(R.id.delete);
            update = itemView.findViewById(R.id.update);
        }
    }
}
