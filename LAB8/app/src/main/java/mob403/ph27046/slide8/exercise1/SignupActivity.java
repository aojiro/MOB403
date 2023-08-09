package mob403.ph27046.slide8.exercise1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import mob403.ph27046.slide8.R;

public class SignupActivity extends AppCompatActivity {
    private TextInputEditText input_email,input_password,input_passwordMatch;
    void initUi(){
        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
        input_passwordMatch = findViewById(R.id.input_passwordMatch);
    }
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initUi();

        //
        auth = FirebaseAuth.getInstance();


    }

    void createUser(String email, String password) {
        auth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e("__onComplete ", "__" + task.isSuccessful() + "\n__" + task.getException());

                        String message = task.getException() == null ? "" : task.getException().getMessage();

                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Signed up successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this, SignInActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("BUNDLE_EMAIL",email);
                            bundle.putString("BUNDLE_PASSWORD",password);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SignupActivity.this, "Failure: "+message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void createUser(View view) {
        String email = input_email.getText().toString().trim();
        String password = input_password.getText().toString().trim();
        String passwordMatch = input_passwordMatch.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isValidEmail(email)){
            Toast.makeText(this, "Email validate!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length() < 6){
            Toast.makeText(this, "Password too short!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(passwordMatch)){
            Toast.makeText(this, "Password not match!", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progressDialog = ProgressDialog.show(this, "", "loading...");
        createUser(email,password);
        progressDialog.dismiss();

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void switchToSignIn(View view) {
        startActivity(new Intent(this, SignInActivity.class));
    }
}