package com.example.LAB4.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lab4.APIServer.ServerResponseSelectAccount;
import com.example.lab4.Interface.AccountInterfaceAPI;
import com.example.lab4.Model.Account;
import com.example.lab4.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Fragment_Login extends Fragment {
    EditText edEmail, edPassWord;
    Button btnLogin;
    TextView tvSignUp;
    ProgressDialog progressDialog;
    private static final String TAG = Fragment_Login.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initViewByID(view);

        initClickListener();

        initProgressDialog();

        return view;
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Đang kiểm tra ...");
        progressDialog.setCancelable(false);
    }

    private void initClickListener() {
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Fragment_Sign_Up());
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString();
                String password = edPassWord.getText().toString();
                if (email.length() != 0 && password.length() != 0) {
                    showProgress();
                    checkForm(email, password);
                } else {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void checkForm(String email, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AccountInterfaceAPI accountInterfaceAPI = retrofit.create(AccountInterfaceAPI.class);
        Call<ServerResponseSelectAccount> call = accountInterfaceAPI.getSelectAccount(email, password);
        call.enqueue(new Callback<ServerResponseSelectAccount>() {
            @Override
            public void onResponse(Call<ServerResponseSelectAccount> call, Response<ServerResponseSelectAccount> response) {
                ServerResponseSelectAccount responseSelectAccount = response.body();
                Toast.makeText(getContext(), responseSelectAccount.getMessage(), Toast.LENGTH_LONG).show();
                hideProgress();
                try {
                    List<Account> accountList = new ArrayList<>(Arrays.asList(responseSelectAccount.getAccount()));
                    int dataID = 0;
                    String dataName = "";
                    String dataEmail = "";
                    String dataPassword = "";
                    for (int i = 0; i < accountList.size(); i++) {
                        Account account = accountList.get(i);
                        dataID = account.getId();
                        dataName = account.getName();
                        dataEmail = account.getEmail();
                        dataPassword = account.getEncrypted_password();
                    }
                    if (accountList.size() != 0) {
                        Fragment_Information_Account fragment_information_account = new Fragment_Information_Account();
                        Bundle bundle = new Bundle();
                        bundle.putInt("dataID", dataID);
                        bundle.putString("dataName", dataName);
                        bundle.putString("dataEmail", dataEmail);
                        bundle.putString("dataPassword", dataPassword);
                        fragment_information_account.setArguments(bundle);
                        loadFragment(fragment_information_account);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    hideProgress();
                }

            }

            @Override
            public void onFailure(Call<ServerResponseSelectAccount> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
                hideProgress();
            }
        });
    }

    private void initViewByID(View view) {
        edEmail = view.findViewById(R.id.ed_email);
        edPassWord = view.findViewById(R.id.ed_password);
        btnLogin = view.findViewById(R.id.btn_login);
        tvSignUp = view.findViewById(R.id.tv_register);

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view_tag, fragment);
        fragmentTransaction.commit();
    }

    private void showProgress() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hideProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private final String baseUrl = "";
}
