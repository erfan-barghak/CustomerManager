package com.example.customerapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import com.example.customerapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class EditCustomerActivity extends AppCompatActivity {

    private CustomerDatabaseHelper dbHelper;
    private long customerId;

    private EditText nameEditText, modelEditText, serialEditText, passwordEditText, patternEditText;
    private Switch batterySwitch, simSwitch, slotSwitch, doorSwitch, memorySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);

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
                updateCustomer();
            }
        });

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCustomer();
            }
        });

        customerId = getIntent().getLongExtra("customerId", -1);
        if (customerId != -1) {
            loadCustomerData();
        }
    }

    private void loadCustomerData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                CustomerDatabaseHelper.TABLE_CUSTOMERS,
                null,
                CustomerDatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(customerId)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            nameEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_NAME)));
            modelEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_MODEL)));
            serialEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_SERIAL)));
            batterySwitch.setChecked(cursor.getInt(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_BATTERY)) == 1);
            simSwitch.setChecked(cursor.getInt(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_SIM)) == 1);
            slotSwitch.setChecked(cursor.getInt(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_SLOT)) == 1);
            doorSwitch.setChecked(cursor.getInt(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_DOOR)) == 1);
            memorySwitch.setChecked(cursor.getInt(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_MEMORY)) == 1);
            passwordEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_PASSWORD)));
            patternEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_PATTERN)));
            cursor.close();
        }
    }

    private void updateCustomer() {
        String name = nameEditText.getText().toString();
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
        values.put(CustomerDatabaseHelper.COLUMN_MODEL, model);
        values.put(CustomerDatabaseHelper.COLUMN_SERIAL, serial);
        values.put(CustomerDatabaseHelper.COLUMN_BATTERY, battery ? 1 : 0);
        values.put(CustomerDatabaseHelper.COLUMN_SIM, sim ? 1 : 0);
        values.put(CustomerDatabaseHelper.COLUMN_SLOT, slot ? 1 : 0);
        values.put(CustomerDatabaseHelper.COLUMN_DOOR, door ? 1 : 0);
        values.put(CustomerDatabaseHelper.COLUMN_MEMORY, memory ? 1 : 0);
        values.put(CustomerDatabaseHelper.COLUMN_PASSWORD, password);
        values.put(CustomerDatabaseHelper.COLUMN_PATTERN, pattern);

        int rowsUpdated = db.update(CustomerDatabaseHelper.TABLE_CUSTOMERS, values, CustomerDatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(customerId)});
        if (rowsUpdated > 0) {
            Toast.makeText(this, "Customer updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error updating customer", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteCustomer() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete(CustomerDatabaseHelper.TABLE_CUSTOMERS, CustomerDatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(customerId)});
        if (rowsDeleted > 0) {
            Toast.makeText(this, "Customer deleted", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error deleting customer", Toast.LENGTH_SHORT).show();
        }
    }
}
