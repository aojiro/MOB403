package com.example.assignment.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.assignment.API_Server.ServerResponseSelectAccount;
import com.example.assignment.API_Server.ServerResponseShowAccount;
import com.example.assignment.Adapter.AdapterListViewItemAccount;
import com.example.assignment.Interface.AccountInterfaceAPI;
import com.example.assignment.Model.Account;
import com.example.assignment.R;
import com.example.assignment.URLServer.PathURLServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_User_Info extends Fragment {

    EditText edEmail, edName;

    ListView lvUsers;
    private AdapterListViewItemAccount adapterListViewItemAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        lvUsers = view.findViewById(R.id.lv_product);
        adapterListViewItemAccount = new AdapterListViewItemAccount(getContext());

        // Lấy danh sách người dùng và hiển thị lên ListView
        getAccountList(view.getId(), "", ""); // Gửi tham số rỗng

        return view;
    }




    private void getAccountList(int id, String email, String name) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AccountInterfaceAPI accountAPI = retrofit.create(AccountInterfaceAPI.class);
        Call<ServerResponseShowAccount> call = accountAPI.getShowAccount(id, email, name);
        call.enqueue(new Callback<ServerResponseShowAccount>() {
            @Override
            public void onResponse(Call<ServerResponseShowAccount> call, Response<ServerResponseShowAccount> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ServerResponseShowAccount responseShowAccount = response.body();
                    Account[] accounts = responseShowAccount.getUsers().toArray(new Account[0]);

                    if (accounts != null && accounts.length > 0) {
                        // Có dữ liệu, hiển thị ListView
                        List<Account> accountList = new ArrayList<>(Arrays.asList(accounts));
                        adapterListViewItemAccount.setListProduct(accountList);
                        lvUsers.setAdapter(adapterListViewItemAccount);
                    } else {
                        // Không có dữ liệu, hiển thị thông báo cho người dùng
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Không có dữ liệu người dùng");
                        builder.setPositiveButton("OK", null);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                } else {
                    // Xử lý khi response không thành công (có lỗi)
                    Toast.makeText(getContext(), "Có lỗi xảy ra trong quá trình lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseShowAccount> call, Throwable t) {
                // Xử lý khi lấy dữ liệu thất bại
                Toast.makeText(getContext(), "Lỗi kết nối tới server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
