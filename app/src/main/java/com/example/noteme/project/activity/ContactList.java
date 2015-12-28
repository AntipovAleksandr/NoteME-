package com.example.noteme.project.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.noteme.R;
import com.example.noteme.project.adapter.PickPhotoDialog;
import com.example.noteme.project.database.DataHandler;
import com.example.noteme.project.database.Contact;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ContactList extends AppCompatActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 0;
    public static final int REQUEST_GALLERY = 1;

    EditText editText, editText1, editText2, editText3;
    TextInputLayout til, til1, til2, til3;
    Button btrSave;
    ImageView photoPick;

    private DataHandler dataHandler;
    private String filePath;
    public static String CONTACT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        btrSave = (Button) findViewById(R.id.btnSave);

        til = (TextInputLayout) findViewById(R.id.textInputLayout);
        til1 = (TextInputLayout) findViewById(R.id.textInputLayout1);
        til2 = (TextInputLayout) findViewById(R.id.textInputLayout2);
        til3 = (TextInputLayout) findViewById(R.id.textInputLayout3);

        editText = (EditText) til.findViewById(R.id.editText);
        editText1 = (EditText) til1.findViewById(R.id.editText1);
        editText2 = (EditText) til2.findViewById(R.id.editText2);
        editText3 = (EditText) til3.findViewById(R.id.editText3);

        til.setHint(getString(R.string.hint));
        til1.setHint(getString(R.string.hint1));
        til2.setHint(getString(R.string.hint2));
        til3.setHint(getString(R.string.hint3));

        dataHandler = new DataHandler(this);
        dataHandler.open();


        btrSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText1.getText().toString();
                String mail = editText.getText().toString();
                String number = editText2.getText().toString();
                String description = editText3.getText().toString();

                Contact contact = new Contact(null, filePath, name, mail, number, description);
                dataHandler.saveContact(contact);
            }
        });


        photoPick = (ImageView) findViewById(R.id.photoPick);
        photoPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                PickPhotoDialog alertDialog = PickPhotoDialog.newInstance("Some title");
                alertDialog.show(fm, "fragment_alert");

            }
        });


        initContact();
    }

    private void initContact() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(CONTACT_ID)) {
            String contactId = extras.getString(CONTACT_ID);
            Contact mail = dataHandler.getMain(contactId);
            editText.setText(mail.getEmail());
            editText1.setText(mail.getName());
            editText2.setText(mail.getNumber());
            editText3.setText(mail.getDescription());
            filePath = mail.getFilePath();
            Bitmap imageBitmap = BitmapFactory.decodeFile(filePath);
            photoPick.setImageBitmap(imageBitmap);
            saveBitmap(imageBitmap);
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
            photoPick.setImageBitmap(imageBitmap);
        }

    }
    private void saveBitmap(Bitmap imageBitmap) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            File pickDirectory = new File("/sdcard/CamPickApp");
            pickDirectory.mkdirs();
            filePath = pickDirectory + "/Pick_" + System.currentTimeMillis() + ".png";
            FileOutputStream fo = new FileOutputStream(new File(filePath));
            fo.write(byteArray);
            fo.flush();
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void onDestroy() {
        super.onDestroy();
        dataHandler.close();
    }


}
