package com.example.noteme.project.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.noteme.R;
import com.example.noteme.project.database.DataHandler;
import com.example.noteme.project.dialog.PickPhotoDialog;
import com.example.noteme.project.model.Contact;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_IMAGE_CAPTURE = 0;
    public static final int REQUEST_GALLERY = 1;

    private EditText etEmail, etName, etPhone, etDescription;
    private ImageView ivPhotoPick;

    private DataHandler dataHandler;
    private String filePath = null;
    public static String CONTACT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        initializeDataBase();
        initializeViews();
    }

    private void initializeDataBase() {
        dataHandler =DataHandler.getInstance(this);
    }

    private void initializeViews() {
        ((TextInputLayout) findViewById(R.id.til_name)).setHint(getString(R.string.hint_name_txt));
        ((TextInputLayout) findViewById(R.id.til_email)).setHint(getString(R.string.hint_email_txt));
        ((TextInputLayout) findViewById(R.id.til_phone)).setHint(getString(R.string.hint_phone_txt));
        ((TextInputLayout) findViewById(R.id.til_description)).setHint(getString(R.string.hint_description_txt));

        ivPhotoPick = (ImageView) findViewById(R.id.iv_photo_pick);
        etName = (EditText) findViewById(R.id.et_name_field);
        etEmail = (EditText) findViewById(R.id.et_email_field);
        etPhone = (EditText) findViewById(R.id.et_phone_field);
        etDescription = (EditText) findViewById(R.id.et_description_field);

        (findViewById(R.id.frame_photo_pick)).setOnClickListener(this);
        (findViewById(R.id.btn_contact_save)).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frame_photo_pick:
                FragmentManager fm = getSupportFragmentManager();
                PickPhotoDialog alertDialog = PickPhotoDialog.newInstance("Some title");
                alertDialog.show(fm, "fragment_alert");

                break;
            case R.id.btn_contact_save:
                String name = etName.getText().toString();
                String mail = etEmail.getText().toString();
                String number = etPhone.getText().toString();
                String description = etDescription.getText().toString();
                Contact contact = new Contact(null, filePath, name, mail, number, description);
                dataHandler.saveContact(contact);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap;

            if (extras == null) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(
                        selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                filePath = cursor.getString(columnIndex);
                cursor.close();
                imageBitmap = BitmapFactory.decodeFile(filePath);
            } else {
                imageBitmap = (Bitmap) extras.get("data");
                imageBitmap.getHeight();
                saveBitmap(imageBitmap);
            }
            ivPhotoPick.setImageBitmap(imageBitmap);
        }

    }

    private void saveBitmap(Bitmap imageBitmap) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            File pickDirectory = new File(Environment.getExternalStorageDirectory() + "/NoteMe");
            pickDirectory.mkdirs();
            filePath = pickDirectory + "/Pick_" + System.currentTimeMillis() + ".png";
            FileOutputStream fo = new FileOutputStream(new File(filePath));
            fo.write(byteArray);
            fo.flush();
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
