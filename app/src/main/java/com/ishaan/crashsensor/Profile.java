package com.ishaan.crashsensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

public class Profile extends AppCompatActivity {

    private EditText nameTextView, addressTextView, bloodTextView, contactOneName, contactOneNumber, contactTwoName, contactTwoNumber;
    private MaterialButton editButton;
    private Boolean edit = false;
    public static String name, address, blood, oneName, oneNumber, twoName, twoNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getWindow().setStatusBarColor(getApplicationContext().getColor(R.color.red));

        nameTextView = findViewById(R.id.nameTextView);
        addressTextView = findViewById(R.id.addressTextView);
        bloodTextView = findViewById(R.id.bloodTextView);
        contactOneName = findViewById(R.id.contactOneName);
        contactOneNumber = findViewById(R.id.contactOneNumber);
        contactTwoName = findViewById(R.id.contactTwoName);
        contactTwoNumber = findViewById(R.id.contactTwoNumber);
        editButton = findViewById(R.id.editButton);

        nameTextView.setEnabled(false);
        addressTextView.setEnabled(false);
        bloodTextView.setEnabled(false);
        contactOneName.setEnabled(false);
        contactOneNumber.setEnabled(false);
        contactTwoName.setEnabled(false);
        contactTwoNumber.setEnabled(false);

        name = nameTextView.getText().toString();
        address = addressTextView.getText().toString();
        blood = bloodTextView.getText().toString();
        oneName = contactOneName.getText().toString();
        oneNumber = contactOneNumber.getText().toString();
        twoName = contactTwoName.getText().toString();
        twoNumber = contactTwoNumber.getText().toString();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!edit)
                {
                    nameTextView.setEnabled(true);
                    addressTextView.setEnabled(true);
                    bloodTextView.setEnabled(true);
                    contactOneName.setEnabled(true);
                    contactOneNumber.setEnabled(true);
                    contactTwoName.setEnabled(true);
                    contactTwoNumber.setEnabled(true);
                    edit = true;
                    editButton.setText("SAVE");
                }
                else
                {
                    nameTextView.setEnabled(false);
                    addressTextView.setEnabled(false);
                    bloodTextView.setEnabled(false);
                    contactOneName.setEnabled(false);
                    contactOneNumber.setEnabled(false);
                    contactTwoName.setEnabled(false);
                    contactTwoNumber.setEnabled(false);

                    name = nameTextView.getText().toString();
                    address = addressTextView.getText().toString();
                    blood = bloodTextView.getText().toString();
                    oneName = contactOneName.getText().toString();
                    oneNumber = contactOneNumber.getText().toString();
                    twoName = contactTwoName.getText().toString();
                    twoNumber = contactTwoNumber.getText().toString();
                    edit = false;
                    editButton.setText("EDIT");
                    Intent intent = new Intent(Profile.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });


    }
}
