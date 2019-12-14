package com.akash.myfirstunittestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etMailId;
    private DatePicker pickerDob;
    private SharedPreferencesHelper mSharedPreferencesHelper;
    private Button btnSave;
    private Button btnRevert;
    private EmailValidator mEmailValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferencesHelper = new SharedPreferencesHelper(sharedPreferences);
    }

    private void init() {
        etName=findViewById(R.id.et_name);
        etMailId=findViewById(R.id.et_mail_id);
        pickerDob=findViewById(R.id.picker_dob);
        btnSave=findViewById(R.id.btn_save);
        btnRevert=findViewById(R.id.btn_revert);
        mEmailValidator=new EmailValidator();
        etMailId.addTextChangedListener(mEmailValidator);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Don't save if the fields do not validate.
                if (!mEmailValidator.isValid()) {
                    etMailId.setError("Invalid email");
                    return;
                }
                // Get the text from the input fields.
                String name = etName.getText().toString();
                Calendar dateOfBirth = Calendar.getInstance();
                dateOfBirth.set(pickerDob.getYear(), pickerDob.getMonth(), pickerDob.getDayOfMonth());
                String email = etMailId.getText().toString();
                // Create a Setting model class to persist.
                SharedPreferenceEntry sharedPreferenceEntry =
                        new SharedPreferenceEntry(name, dateOfBirth, email);
                // Persist the personal information.
                boolean isSuccess = mSharedPreferencesHelper.savePersonalInfo(sharedPreferenceEntry);
                if (isSuccess) {
                    Toast.makeText(MainActivity.this, "Personal information saved", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("test_main", "Failed to write personal information to SharedPreferences");
                }
            }
        });

        btnRevert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateUi();
                Toast.makeText(MainActivity.this, "Personal information reverted", Toast.LENGTH_LONG).show();
                Log.d("test_main", "Personal information reverted");
            }
        });
    }

    private void populateUi() {
        SharedPreferenceEntry sharedPreferenceEntry;
        sharedPreferenceEntry = mSharedPreferencesHelper.getPersonalInfo();
        etName.setText(sharedPreferenceEntry.getName());
        Calendar dateOfBirth = sharedPreferenceEntry.getDateOfBirth();
        pickerDob.init(dateOfBirth.get(Calendar.YEAR), dateOfBirth.get(Calendar.MONTH),
                dateOfBirth.get(Calendar.DAY_OF_MONTH), null);
        etMailId.setText(sharedPreferenceEntry.getEmail());
    }
}
