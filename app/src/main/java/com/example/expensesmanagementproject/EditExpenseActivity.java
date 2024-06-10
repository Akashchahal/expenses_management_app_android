package com.example.expensesmanagementproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_expense);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int id=getIntent().getIntExtra("id",0);
        String name=getIntent().getStringExtra("name");
        int amount=getIntent().getIntExtra("amount",0);

        EditText edt1=findViewById(R.id.edtname);
        EditText edt2=findViewById(R.id.edtamount);

        DatabaseHelper dbHelper=new DatabaseHelper(this);
        edt1.setText(name);
        edt2.setText(amount+"");

        Button btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String expName=edt1.getText().toString();
                int expAmount=Integer.parseInt(edt2.getText().toString()) ;

                dbHelper.updateExpense(id,expName,expAmount);

                Intent obj=new Intent(EditExpenseActivity.this,ShowExpensesDataActivity.class);
                startActivity(obj);
                finish();
            }
        });
    }
}