package com.example.customerapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import com.example.customerapp.R;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class CustomerActivity extends AppCompatActivity {

    private CustomerDatabaseHelper dbHelper;

    private EditText nameEditText, modelEditText, serialEditText, passwordEditText, patternEditText;
    private Switch batterySwitch, simSwitch, slotSwitch, doorSwitch, memorySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        dbHelper = new CustomerDatabaseHelper(this);

        nameEditText = findViewById(R.id.nameEditText);
        modelEditText = findViewById(R.id.modelEditText);
        serialEditText = findViewById(R.id.serialEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        patternEditText = findViewById(R.id.patternEditText);
        batterySwitch = findViewById(R.id.batterySwitch);
        simSwitch = findViewById(R.id.simSwitch);
        slotSwitch = findViewById(R.id.slotSwitch);
        doorSwitch = findViewById(R.id.doorSwitch);
        memorySwitch = findViewById(R.id.memorySwitch);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCustomer();
            }
        });
    }

    private void saveCustomer() {
        String name = nameEditText.getText().toString();
        String code = String.format("%06d", new Random().nextInt(999999));
        String model = modelEditText.getText().toString();
        String serial = serialEditText.getText().toString();
        boolean battery = batterySwitch.isChecked();
        boolean sim = simSwitch.isChecked();
        boolean slot = slotSwitch.isChecked();
        boolean door = doorSwitch.isChecked();
        boolean memory = memorySwitch.isChecked();
        String password = passwordEditText.getText().toString();
        String pattern = patternEditText.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CustomerDatabaseHelper.COLUMN_NAME, name);
        values.put(CustomerDatabaseHelper.COLUMN_CODE, code);
        values.put(CustomerDatabaseHelper.COLUMN_MODEL, model);
        values.put(CustomerDatabaseHelper.COLUMN_SERIAL, serial);
        values.put(CustomerDatabaseHelper.COLUMN_BATTERY, battery ? 1 : 0);
        values.put(CustomerDatabaseHelper.COLUMN_SIM, sim ? 1 : 0);
        values.put(CustomerDatabaseHelper.COLUMN_SLOT, slot ? 1 : 0);
        values.put(CustomerDatabaseHelper.COLUMN_DOOR, door ? 1 : 0);
        values.put(CustomerDatabaseHelper.COLUMN_MEMORY, memory ? 1 : 0);
        values.put(CustomerDatabaseHelper.COLUMN_PASSWORD, password);
        values.put(CustomerDatabaseHelper.COLUMN_PATTERN, pattern);

        long newRowId = db.insert(CustomerDatabaseHelper.TABLE_CUSTOMERS, null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Customer saved with code: " + code, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error saving customer", Toast.LENGTH_SHORT).show();
        }
    }
}

