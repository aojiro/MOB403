package com.example.assignment.Fragment;

import android.app.AlertDialog;
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


import com.example.assignment.API_Server.ServerResponseMessageChangePassword;
import com.example.assignment.Interface.AccountInterfaceAPI;
import com.example.assignment.R;
import com.example.assignment.URLServer.PathURLServer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Fragment_Information_Account extends Fragment {
    TextView tvID, tvName, tvEmail;
    Button btnChangePassword, btnLogout, btnDiaLogCancel, btnDialogSave;
    private int id;
    private String name, email, password;
    EditText edPasswordOld, edPasswordNew;
    AlertDialog alertDialog;
    private static final String TAG = Fragment_Information_Account.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information_account, container, false);

        initViewByID(view);

        initGetDataArguments();

        initTextView();

        initClickListener();

        return view;
    }

    private void initTextView() {
        tvID.setText("ID: " + id);
        tvName.setText("Xin chào bạn " + name);
        tvEmail.setText(email);
    }

    private void initClickListener() {
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDialog(id, password);


            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Fragment_Login());
            }
        });
    }

    private void initGetDataArguments() {
        id = getArguments().getInt("dataID");
        name = getArguments().getString("dataName");
        email = getArguments().getString("dataEmail");
        password = getArguments().getString("dataPassword");
    }

    private void initViewByID(View view) {
        tvID = view.findViewById(R.id.tv_id);
        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        btnChangePassword = view.findViewById(R.id.btn_changePassword);
        btnLogout = view.findViewById(R.id.btn_logout);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view_tag, fragment);
        fragmentTransaction.commit();
    }

    private void initDialog(int id, String password) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View view = getLayoutInflater().inflate(R.layout.layout_dialog_change_password, null);
        builder.setView(view);
        edPasswordOld = view.findViewById(R.id.ed_passwordold);
        edPasswordNew = view.findViewById(R.id.ed_passwordnew);
        btnDiaLogCancel = view.findViewById(R.id.btn_cancel);
        btnDialogSave = view.findViewById(R.id.btn_save);
        alertDialog = builder.create();
        btnDiaLogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideDialog();
            }
        });
        btnDialogSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordOld = edPasswordOld.getText().toString();
                String passwordNew = edPasswordNew.getText().toString();
                if (passwordNew.length() != 0 && passwordOld.length() != 0) {
                    if (passwordOld.equals(password)) {
                        changePasswordRetrofit(id, passwordNew);
                    } else {
                        Toast.makeText(getContext(), "Nhập không đúng mật khẩu cũ", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Vui lòng nhập dữ liệu vào !", Toast.LENGTH_LONG).show();
                }


            }
        });
        showDialog();
    }

    private void showDialog() {
        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
    }

    private void hideDialog() {
        if (alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    private void changePasswordRetrofit(int id, String new_password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AccountInterfaceAPI accountInterfaceAPI = retrofit.create(AccountInterfaceAPI.class);
        Call<ServerResponseMessageChangePassword> call = accountInterfaceAPI.getMessageChangePassword(id, new_password);
        call.enqueue(new Callback<ServerResponseMessageChangePassword>() {
            @Override
            public void onResponse(Call<ServerResponseMessageChangePassword> call, Response<ServerResponseMessageChangePassword> response) {
                ServerResponseMessageChangePassword serverResponseMessageChangePassword = response.body();
                Toast.makeText(getContext(), serverResponseMessageChangePassword.getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<ServerResponseMessageChangePassword> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }
}