package mob403.ph27046.slide8.exercise1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mob403.ph27046.slide8.exercise3.UploadImgActivity;
import mob403.ph27046.slide8.helpers.MyCallback;
import mob403.ph27046.slide8.R;

public class SettingsActivity extends AppCompatActivity {
    private TextView tv_user;
    private TextInputLayout inputLayout_email;
    private LinearLayout layout_password;
    private TextInputEditText input_email, input_password, input_newPassword, input_newPasswordMatch;

    void initUi() {
        tv_user = findViewById(R.id.tv_user);
        input_email = findViewById(R.id.input_email);
        inputLayout_email = findViewById(R.id.inputLayout_email);
        layout_password = findViewById(R.id.layout_password);
        input_password = findViewById(R.id.input_password);
        input_newPassword = findViewById(R.id.input_newPassword);
        input_newPasswordMatch = findViewById(R.id.input_newPasswordMatch);
    }

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initUi();
        authListener();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        checkSignedIn(user);

    }

    //  __Firebase Handler
    void authListener() {
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                checkSignedIn(user);
            }
        };
    }

    void resetPassword(String email) {
        auth
                .sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.e("__onComplete ", "__" + task.isSuccessful() + "\n__" + task.getException());

                        String message = task.getException() == null ? "" : task.getException().getMessage();

                        if (task.isSuccessful()) {
                            Toast.makeText(SettingsActivity.this, "Sent reset password to "+user.getEmail(), Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(SettingsActivity.this, "Failure: "+message, Toast.LENGTH_SHORT).show();
                        }
                        dialogConfirm.dismiss();
                    }
                });
    }

    void signOut() {
        Toast.makeText(this, "Please sign in again", Toast.LENGTH_SHORT).show();
        auth.signOut();
    }

    void updateEmail(String email) {
        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.e("__onComplete ", "__" + task.isSuccessful() + "\n__" + task.getException());

                        String message = task.getException() == null ? "" : task.getException().getMessage();

                        if (task.isSuccessful()) {
                            Toast.makeText(SettingsActivity.this, "Updated email successfully", Toast.LENGTH_SHORT).show();
                            signOutUser(null);
                        }else{
                            Toast.makeText(SettingsActivity.this, "Failure: "+message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void updatePassword(String email, String password, String newPassword) {
        user
                .reauthenticate(EmailAuthProvider.getCredential(email, password))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.e("__onComplete ", "__" + task.isSuccessful() + "\n__" + task.getException());

                        String message = task.getException() == null ? "" : task.getException().getMessage();

                        if (task.isSuccessful()) {
                            user.updatePassword(newPassword)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.e("__onComplete ", "__" + task.isSuccessful() + "\n__" + task.getException());

                                            String message = task.getException() == null ? "" : task.getException().getMessage();

                                            if (task.isSuccessful()) {
                                                Toast.makeText(SettingsActivity.this, "Updated password successfully", Toast.LENGTH_SHORT).show();
                                                signOutUser(null);
                                            }else{
                                                Toast.makeText(SettingsActivity.this, "Failure: "+message, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(SettingsActivity.this, "Failure: "+message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void deleteUser() {
        user
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.e("__onComplete ", "__" + task.isSuccessful() + "\n__" + task.getException());

                        String message = task.getException() == null ? "" : task.getException().getMessage();


                        if (task.isSuccessful()) {
                            Toast.makeText(SettingsActivity.this, "Deleted user successfully", Toast.LENGTH_SHORT).show();
                            signOutUser(null);
                        }else{
                            Toast.makeText(SettingsActivity.this, "Failure: "+message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //  __Event Listener
    public void signOutUser(View view) {
        signOut();
        startActivity(new Intent(SettingsActivity.this, SignInActivity.class));
    }

    public void updateEmailUser(View view) {
        if (inputLayout_email.getVisibility() == View.VISIBLE) {
            //  _validate
            String newEmail = input_email.getText().toString().trim();

            if (newEmail.isEmpty()) {
                Toast.makeText(this, "Empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isValidEmail(newEmail)) {
                Toast.makeText(this, "Email validate!", Toast.LENGTH_SHORT).show();
                return;
            }

            //  _handle
            updateEmail(newEmail);


        } else {
            //  _toggle visible
            inputLayout_email.setVisibility(View.VISIBLE);
            layout_password.setVisibility(View.GONE);
        }
    }


    public void updatePasswordUser(View view) {
        if (layout_password.getVisibility() == View.VISIBLE) {
            //  _validate
            String password = input_password.getText().toString().trim();
            String newPassword = input_newPassword.getText().toString().trim();
            String newPasswordMatch = input_newPasswordMatch.getText().toString().trim();

            if (password.isEmpty() || newPassword.isEmpty() || newPasswordMatch.isEmpty()) {
                Toast.makeText(this, "Empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (newPassword.length() < 6) {
                Toast.makeText(this, "Password too short!", Toast.LENGTH_SHORT).show();

                return;
            }
            if (!newPassword.equals(newPasswordMatch)) {
                Toast.makeText(this, "Password not match!", Toast.LENGTH_SHORT).show();

                return;
            }


            //  _handle
            updatePassword(user.getEmail(), password, newPassword);
        } else {
            //  _toggle visible
            layout_password.setVisibility(View.VISIBLE);
            inputLayout_email.setVisibility(View.GONE);
        }
    }

    public void deleteUser(View view) {
        createDialog(new MyCallback() {
            @Override
            public void handle() {
                deleteUser();
            }

        });
        showDialog();
    }

    public void resetPasswordUser(View view) {
        createDialog(new MyCallback() {
            @Override
            public void handle() {
                resetPassword(user.getEmail());
            }
        });
        showDialog();
    }

    //  __dialog
    private BottomSheetDialog dialogConfirm;

    void createDialog(MyCallback myCallBack) {
        //  _init UI
        View view = getLayoutInflater().inflate(R.layout.dialog_confirm, null);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView btn_negative = view.findViewById(R.id.btn_negative);
        TextView btn_positive = view.findViewById(R.id.btn_positive);
        tv_title.setText("Confirm");

        //  _init dialog
        dialogConfirm = new BottomSheetDialog(this);
        dialogConfirm.setContentView(view);


        //  _event listener
        btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogConfirm.dismiss();
            }
        });
        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCallBack.handle();
            }
        });
    }


    void showDialog() {
        if (dialogConfirm != null && dialogConfirm.isShowing()) {
            return;
        }
        dialogConfirm.show();
    }

    //  __validate
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    //
    void checkSignedIn(FirebaseUser user){
        if (user == null) {
            startActivity(new Intent(SettingsActivity.this, SignInActivity.class));
            finish();
        } else {
            tv_user.setText("User: " + user.getEmail());
        }
    }

    public void hideTextInputView(View view) {
        layout_password.setVisibility(View.GONE);
        inputLayout_email.setVisibility(View.GONE);
    }

    public void switchToUpload(View view) {
        startActivity(new Intent(SettingsActivity.this, UploadImgActivity.class));
    }
}