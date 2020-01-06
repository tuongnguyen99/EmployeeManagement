package com.example.employeemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText txtRegisName, txtRegisPhone, txtRegisPosition, txtRegisDepartment, txtRegisPwd, txtRegisRePwd;
    Button btnRegis, btnCancle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initControls();
        addEvents();
    }

    private void addEvents() {
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtRegisName.getText().toString().trim();
                String phone = txtRegisPhone.getText().toString().trim();
                String postion = txtRegisPosition.getText().toString().trim();
                String department = txtRegisDepartment.getText().toString().trim();
                String pwd = txtRegisPwd.getText().toString().trim();
                String rePwd = txtRegisRePwd.getText().toString().trim();

                if (name.length() == 0 || phone.length() ==0 || postion.length() == 0 || department.length() == 0 || pwd.length() ==0 || rePwd.length() == 0){
                    Toast.makeText(RegisterActivity.this, "Please enter all fields fully!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pwd.equals(rePwd)){
                    Toast.makeText(RegisterActivity.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //
                // Call api to add new user
                //

            }
        });
    }

    private void initControls() {
        txtRegisName = findViewById(R.id.txtRegisName);
        txtRegisPhone  = findViewById(R.id.txtRegisPhone);
        txtRegisPosition = findViewById(R.id.txtRegisPosition);
        txtRegisDepartment = findViewById(R.id.txtRegisDepartment);
        txtRegisPwd = findViewById(R.id.txtRegisPwd);
        txtRegisRePwd = findViewById(R.id.txtRegisRePwd);
        btnRegis = findViewById(R.id.btnRegis);
        btnCancle = findViewById(R.id.btnCancle);
    }
}
