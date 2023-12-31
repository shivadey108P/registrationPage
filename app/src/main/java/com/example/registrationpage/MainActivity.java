package com.example.registrationpage;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText username, password, dateOfBirth, address;
    RadioGroup gender;
    RadioButton male, female;
    Spinner statesSpinner;
    Button submit;
    TextView viewData, age;

    DatePickerDialog.OnDateSetListener dateSetListener;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        age = findViewById(R.id.age);
        dateOfBirth = findViewById(R.id.dob);
        address = findViewById(R.id.address);
        gender = findViewById(R.id.gender);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        statesSpinner = findViewById(R.id.stateSpinner);
        submit = findViewById(R.id.submit);
        viewData = findViewById(R.id.displayDataTextView);

        List<String> states = new ArrayList<>(Arrays.asList(
                "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh",
                "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
                "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
                "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab",
                "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura",
                "Uttar Pradesh", "Uttarakhand", "West Bengal"
        ));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, states);
        AlertDialog.Builder stateSpinner;
        statesSpinner.setAdapter(adapter);

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

    }

        public void onSubmit (View view){

            StringBuilder data = new StringBuilder();
            data.append("User Name: ").append(username.getText().toString()).append("\n");
            data.append("Password: ").append(password.getText().toString()).append("\n");
            data.append("Address: ").append(address.getText().toString()).append("\n");

            int genderId = gender.getCheckedRadioButtonId();
            String gender = (genderId == R.id.male) ? "Male" : "Female";
            data.append("Gender: ").append(gender).append("\n");

            data.append("Age: ").append(age.getText().toString()).append("\n");
            data.append("Date of Birth: ").append(dateOfBirth.getText().toString()).append("\n");
            data.append("State: ").append(statesSpinner.getSelectedItem().toString()).append("\n");

            // Display the data
            viewData.setText(data.toString());
            viewData.setVisibility(View.VISIBLE);


        }

        private void updateDateOfBirth() {
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            dateOfBirth.setText(sdf.format(calendar.getTime()));
        }

    private void calculateAge(String dob) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        try {
            Date dateOfBirth = sdf.parse(dob);
            Calendar dobCalendar = Calendar.getInstance();
            if (dateOfBirth != null) {
                dobCalendar.setTime(dateOfBirth);
            }

            Calendar todayCalendar = Calendar.getInstance();

            int ageCount = todayCalendar.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR);
            if (todayCalendar.get(Calendar.DAY_OF_YEAR) < dobCalendar.get(Calendar.DAY_OF_YEAR)) {
                ageCount--;
            }

            age.setText(String.valueOf(ageCount));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void showDatePickerDialog(View view) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Handle the date set event
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        dateOfBirth.setText(selectedDate);

                        // Calculate and set the age
                        calculateAge(selectedDate);
                    }
                },
                year, month, day);

        datePickerDialog.show();


    }
}