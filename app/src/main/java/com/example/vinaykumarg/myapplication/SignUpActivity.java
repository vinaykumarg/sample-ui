package com.example.vinaykumarg.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import io.blackbox_vision.datetimepickeredittext.view.DatePickerEditText;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private DatePickerEditText datePickerEditText;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        datePickerEditText = (DatePickerEditText) findViewById(R.id.datePickerEditText);
        datePickerEditText.setManager(getSupportFragmentManager());
        Spinner location_spinner = (Spinner) findViewById(R.id.locationspinner);
        Spinner experience_spinner = (Spinner) findViewById(R.id.experiencespinner);
        Spinner qualification_spinner = (Spinner) findViewById(R.id.qualificationspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.locations_array,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location_spinner.setAdapter(adapter);
        experience_spinner.setAdapter(adapter);
        qualification_spinner.setAdapter(adapter);
        Button submit_button = (Button) findViewById(R.id.submit);
        mIntent = new Intent(this,RegistrationActivity.class);
        submit_button.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.submit:
                startActivity(mIntent);
                break;
        }
    }
}
