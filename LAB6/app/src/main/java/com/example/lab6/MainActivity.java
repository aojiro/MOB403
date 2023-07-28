package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtFC;
    Button btnF, btnC;
    String strFC;
    TextView tvResult;
    int convertStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtFC = (EditText) findViewById(R.id.edtF_C);
        btnF = (Button) findViewById(R.id.btnF);
        btnC = (Button) findViewById(R.id.btnC);
        tvResult = (TextView) findViewById(R.id.tvResult);
        btnF.setOnClickListener(this);
        btnC.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnF) {
            invokeAsyncTask("Độ F", Constants.F_TO_C_SOAP_ACTION, Constants.F_TO_C_METHOD_NAME);
            convertStyle = 1;
        } else if (view.getId() == R.id.btnC) {
            invokeAsyncTask("Độ C", Constants.C_TO_F_SOAP_ACTION, Constants.C_TO_F_METHOD_NAME);
            convertStyle = 0;
        }
    }

    // tạo và thực thi asynctask để lấy phản hồi từ máy chủ W3school
    private void invokeAsyncTask(String convertParams, String soapAction,
                                 String methodName) {
        try {
            new ConvertTemperatureTask(this, soapAction, methodName,
                    convertParams).execute(edtFC.getText()
                    .toString().trim());
        } catch (Exception e) {
            Log.e("Error", "Lỗi khởi tạo ConvertTemperatureTask: " + e.getMessage());
        }
    }

    // gọi lại dữ liệu từ luồng nền và đặt vào các view
    public void callBackDataFromAsyncTask(String result) {
        try {
            double resultTemperature = Double.parseDouble(result); // chuyển đổi sang kiểu double
            if (convertStyle == 0) { // Độ C sang Độ F
                tvResult.setText(edtFC.getText().toString().trim() + " độ C = " + String.format("%.2f", resultTemperature) + " độ F");
            } else { // Độ F sang Độ C
                tvResult.setText(edtFC.getText().toString().trim() + " độ F = " + String.format("%.2f", resultTemperature) + " độ C");
            }
        } catch (Exception e) {
            Log.e("Error", "Lỗi parse kết quả: " + e.getMessage());
        }
    }

}