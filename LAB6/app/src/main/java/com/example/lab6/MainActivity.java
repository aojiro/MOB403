package com.example.lab6; // khai báo package

import androidx.appcompat.app.AppCompatActivity; // import lớp AppCompatActivity

import android.os.AsyncTask; // import lớp AsyncTask
import android.os.Bundle; // import lớp Bundle
import android.util.Log; // import lớp Log dùng để ghi log
import android.view.View; // import lớp View
import android.widget.Button; // import lớp Button
import android.widget.EditText; // import lớp EditText
import android.widget.TextView; // import lớp TextView

import org.ksoap2.SoapEnvelope; // import lớp SoapEnvelope của thư viện ksoap2
import org.ksoap2.serialization.SoapObject; // import lớp SoapObject của ksoap2
import org.ksoap2.serialization.SoapPrimitive; // import lớp SoapPrimitive của ksoap2
import org.ksoap2.serialization.SoapSerializationEnvelope; // import lớp SoapSerializationEnvelope của ksoap2
import org.ksoap2.transport.HttpTransportSE; // import lớp HttpTransportSE của ksoap2
import org.xmlpull.v1.XmlPullParserException; // import lớp XmlPullParserException

import java.io.IOException; // import lớp IOException

public class MainActivity extends AppCompatActivity { // khai báo lớp MainActivity kế thừa AppCompatActivity

    private EditText edInput; // khai báo biến edInput là EditText
    private Button btnCtoF, btnFtoC; // khai báo 2 nút btnCtoF và btnFtoC
    private TextView tvResult; // khai báo biến tvResult là TextView

    public final static String SOAP_ACTION = "https://www.w3schools.com/xml/"; // khai báo hằng số URL soap action
    public final static String NAME_SPACE = "https://www.w3schools.com/xml/"; // khai báo tên namespace
    public final static String URL = "https://www.w3schools.com/xml/tempconvert.asmx?WSDL"; // khai báo URL dịch vụ web
    public final static String PARAMS_F = "Fahrenheit"; // tên tham số độ F
    public final static String PARAMS_C = "Celsius"; // tên tham số độ C
    public final static String F_TO_C_METHOD_NAME = "FahrenheitToCelsius"; // tên phương thức F sang C
    public final static String C_TO_F_METHOD_NAME = "CelsiusToFahrenheit"; // tên phương thức C sang F
    public final static String F_TO_C_SOAP_ACTION = SOAP_ACTION + F_TO_C_METHOD_NAME; // hằng số soap action F sang C
    public final static String C_TO_F_SOAP_ACTION = SOAP_ACTION + C_TO_F_METHOD_NAME; // hằng số soap action C sang F
    String result; // khai báo biến result
    int valueConvert; // khai báo biến valueConvert

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // gán giao diện

        edInput = findViewById(R.id.edtF_C); // ánh xạ edit text
        btnCtoF = findViewById(R.id.btnC); // ánh xạ nút C sang F
        btnFtoC = findViewById(R.id.btnF); // ánh xạ nút F sang C
        tvResult = findViewById(R.id.tvResult); // ánh xạ textview

        // Set labels for buttons
        btnCtoF.setText("Chuyển từ C sang F"); // set text cho nút C sang F
        btnFtoC.setText("Chuyển từ F sang C"); // set text cho nút F sang C

        btnCtoF.setOnClickListener(new View.OnClickListener() { // bắt sự kiện click cho nút C sang F
            @Override
            public void onClick(View v) {
                valueConvert = Integer.parseInt(edInput.getText().toString().trim()); // lấy giá trị từ edit text
                new convertCtoFAsyncTask().execute(valueConvert); // gọi asynchronous task để chuyển C sang F
            }
        });

        btnFtoC.setOnClickListener(new View.OnClickListener() { // bắt sự kiện cho nút F sang C
            @Override
            public void onClick(View v) {
                valueConvert = Integer.parseInt(edInput.getText().toString().trim());// lấy giá trị từ edit text
                convertCtoFAsyncTask outerClassInstance = new convertCtoFAsyncTask();
                outerClassInstance.new convertFtoCAsyncTask().execute(valueConvert); // gọi asynchronous task lồng nhau để chuyển F sang C
            }
        });

    }

    private class convertCtoFAsyncTask extends AsyncTask<Integer, Void, String> { // định nghĩa lớp asynchronous task để chuyển C sang F

        @Override
        protected String doInBackground(Integer... params) {
            int valueConvert = params[0]; // lấy giá trị cần chuyển đổi

            SoapObject soapObject = new SoapObject(NAME_SPACE, C_TO_F_METHOD_NAME); // tạo đối tượng SoapObject
            soapObject.addProperty(PARAMS_C, valueConvert); // thêm tham số
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // tạo envelope
            envelope.setOutputSoapObject(soapObject); // set object
            envelope.dotNet = true; // chỉ ra là dịch vụ .NET

            HttpTransportSE httpTransportSE = new HttpTransportSE(URL); // tạo kết nối
            try {
                httpTransportSE.call(C_TO_F_SOAP_ACTION, envelope); // gọi dịch vụ
                SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse(); // lấy kết quả
                return soapPrimitive.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && !result.equalsIgnoreCase("Error")) { // nếu kết quả khác null và khác "Error"
                float resultValue = Float.parseFloat(result); // ép kiểu kết quả sang float
                tvResult.setText(valueConvert + " C = " + resultValue + " F"); // hiển thị kết quả
            } else {
                tvResult.setText("Error: Không thể chuyển đổi nhiệt độ."); // thông báo lỗi
            }
        }


        private class convertFtoCAsyncTask extends AsyncTask<Integer, Void, String> { // định nghĩa lớp con để chuyển F sang C

            @Override
            protected String doInBackground(Integer... params) {
                int valueConvert = params[0];

                SoapObject soapObject = new SoapObject(NAME_SPACE, F_TO_C_METHOD_NAME);
                soapObject.addProperty(PARAMS_F, valueConvert);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(soapObject);
                envelope.dotNet = true;

                HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
                try {
                    httpTransportSE.call(F_TO_C_SOAP_ACTION, envelope);
                    SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
                    return soapPrimitive.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null && !result.equalsIgnoreCase("Error")) {
                    float resultValue = Float.parseFloat(result);
                    tvResult.setText(valueConvert + " F = " + resultValue + " C");
                } else {
                    tvResult.setText("Error: Không thể chuyển đổi nhiệt độ.");
                }
            }
        }
    }

}