package com.gaurav.studentdetail;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class IndiviewListAdapter extends ArrayAdapter<Student> {

    List<Student> studentList;
    Activity context;
    int resource;
    SQLiteDatabase db;


    public IndiviewListAdapter(@NonNull Activity context, int resource, List<Student> studentList) {
        super(context, resource, studentList);
        this.context = context;
        this.resource = resource;
        this.studentList = studentList;
        db = context.openOrCreateDatabase("studInfo", Context.MODE_PRIVATE, null);

    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(resource, null, false);

        final TextView tvStudCity = view.findViewById(R.id.sCityItem);
        TextView tvStudEmail = view.findViewById(R.id.sEmailItem);
        TextView tvStudName = view.findViewById(R.id.sNameItem);
        TextView tvStreams = view.findViewById(R.id.sStream);
        final TextView tvId = view.findViewById(R.id.sId);

        Button buttonDelete = view.findViewById(R.id.buttonDeleteStudent);
        Button buttonEdit = view.findViewById(R.id.buttonEditStudent);

        final Student student = studentList.get(position);

        tvStudName.setText(student.getName());
        tvStudCity.setText(student.getCity());
        tvStudEmail.setText(student.getEmail());
        tvStreams.setText(student.getStream());
        tvId.setText(student.getId());

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeStudent(position, student.getId());
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStudent(position);
            }
        });

        return view;
    }


    private void updateStudent(int position) {

        String nameValue = studentList.get(position).getName();
        String cityValue = studentList.get(position).getCity();
        String emailValue = studentList.get(position).getEmail();
        String streamValue = studentList.get(position).getStream();
        String idValue = studentList.get(position).getId();

        Log.i("Name : ", "Update Name is :---------------------" + nameValue);
        Log.i("City : ", "Update city is :---------------------" + cityValue);
        Log.i("Email : ", "Update email is :---------------------" + emailValue);
        Log.i("Stream : ", "Update stream is :---------------------" + streamValue);
        Log.i("Id : ", "Update id is :---------------------" + idValue);

        Intent intent = new Intent(context, UpdateActivity.class);
        intent.putExtra("NAME", nameValue);
        intent.putExtra("CITY", cityValue);
        intent.putExtra("EMAIL", emailValue);
        intent.putExtra("ID", idValue);

        context.startActivityForResult(intent,1);

    }

    private void removeStudent(final int position, final String tvId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure you want to delete " + tvId + " Record ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                studentList.remove(position);
                int status = db.delete("student", "_id=?", new String[]{tvId});
                if (status != 0) {
                    Toast.makeText(context, "Data is Deleted...", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Data Deletion Failed...", Toast.LENGTH_LONG).show();
                }
                //reloading the list
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // code for negative button
                Toast.makeText(context, "Your Data is Safe ...", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
