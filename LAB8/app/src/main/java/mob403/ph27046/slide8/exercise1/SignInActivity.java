package mob403.ph27046.slide8.exercise1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import mob403.ph27046.slide8.R;
import mob403.ph27046.slide8.exercise2.HomeActivity;

public class SignInActivity extends AppCompatActivity {
    private TextInputEditText input_email,input_password;
    void initUi(){
        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
    }
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_signin);

        initUi();

        //  _
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String email = bundle.getString("BUNDLE_EMAIL");
            String password = bundle.getString("BUNDLE_PASSWORD");

            if(!email.isEmpty() && !password.isEmpty()){
                input_email.setText(email);
                input_password.setText(password);
            }
        }

    }

    void signIn(String email, String password) {
        auth
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e("__onComplete ", "__" + task.isSuccessful() + "\n__" + task.getException());

                        String message = task.getException() == null ? "" : task.getException().getMessage();

                        if (task.isSuccessful()) {
                            Toast.makeText(SignInActivity.this, "Signed in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignInActivity.this, SettingsActivity.class));
                        }else{
                            Toast.makeText(SignInActivity.this, "Failure: "+message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signInUser(View view) {
        String email = input_email.getText().toString().trim();
        String password = input_password.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "Empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progressDialog = ProgressDialog.show(this,"","loading...");
        signIn(email,password);
        progressDialog.dismiss();
    }

    public void switchToSignUp(View view) {
        startActivity(new Intent(SignInActivity.this, SignupActivity.class));
    }


}