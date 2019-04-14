package com.example.javamail.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javamail.FileUtils;
import com.example.javamail.R;
import com.example.javamail.SendMail;

import java.io.File;

public class SubActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int READ_REQUEST_CODE = 42;
    private static final int REQUEST_CODE = 6384;
    private String path;
    Uri URI = null;
    int columnIndex;
    String attachmentFile;

    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private TextView textViewAttachment;

    private Button buttonSend;
    private Button buttonAttachment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        textViewAttachment = (TextView) findViewById(R.id.textViewAttachment);

        buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonAttachment = (Button) findViewById(R.id.buttonAttachment);

        buttonSend.setOnClickListener(this);
        buttonAttachment.setOnClickListener(this);
    }

    private void sendEmail() {
        //Getting content for email
        String email = editTextEmail.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        //Creating SendMail object
        SendMail sm;
        if(path != null) {
            sm = new SendMail(this, email, subject, message, path);
        } else {
            sm = new SendMail(this, email, subject, message);
        }

        //Executing sendmail to send email
        sm.execute();
    }

    private void performFileSearch() {
        Intent target = FileUtils.createGetContentIntent();
        Intent intent = Intent.createChooser(target, "Lorem ipsum");
        startActivityForResult(intent, REQUEST_CODE);
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("*/*");
//        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAttachment) {
            performFileSearch();
        }

        if(v == buttonSend) {
            sendEmail();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                final Uri uri = data.getData();
//                Log.e("asdfa", uri.toString());
                path = FileUtils.getPath(this, uri);
                Log.e("path", path);
//                Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), data.getData().toString()));
////                Uri uri = data.getData();
//                Log.e("test", uri.toString());
//                Log.e("test!!", uri.getPath());
//                Log.e("tes1", Environment.getExternalStorageDirectory().toString());
//                Log.e("tes1", Environment.getExternalStorageDirectory().getAbsolutePath());
//                path = data.getData().toString();
////                String path = uri.getPath();
////                path = path.substring(path.indexOf(":") + 1);
////                Toast.makeText(this, "" + path, Toast.LENGTH_LONG).show();
//////                textViewAttachment.setText(path);
//
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//                columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                attachmentFile = cursor.getString(columnIndex);
//                Log.e("Attachment : ", "why..? : " + attachmentFile);
//                URI = Uri.parse("file://" + attachmentFile);
//                cursor.close();
            }
        }
    }
}
