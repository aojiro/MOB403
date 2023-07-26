package com.example.lab5.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lab5.Interface.ProductAPI;
import com.example.lab5.Model.Product;
import com.example.lab5.R;
import com.example.lab5.RetrofitServer.ServerInsertProduct;
import com.example.lab5.URLServer.PathURLServer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_Add_Product extends Fragment {
    EditText edName, edPrice, edDescription;
    Button btnAdd, btnExit;

    private static final String TAG = Fragment_Add_Product.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        initViewById(view);

        initClickListener();

        return view;
    }

    private void initClickListener() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Fragment_Home());
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edName.getText().toString();
                String priceString = edPrice.getText().toString();
                String description = edDescription.getText().toString();
                if (name.length() != 0 && priceString.length() != 0 && description.length() != 0) {
                    try {
                        int price = Integer.parseInt(priceString);
                        insertProduct(name, price, description);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Vui lòng nhập đúng định dạng giá", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void insertProduct(String name, int price, String description) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductAPI productAPI = retrofit.create(ProductAPI.class);
        Product product = new Product(name, price, description);
        Call<ServerInsertProduct> call = productAPI.insertProduct(product.getName(), product.getPrice(), product.getDescription());
        call.enqueue(new Callback<ServerInsertProduct>() {
            @Override
            public void onResponse(Call<ServerInsertProduct> call, Response<ServerInsertProduct> response) {
                ServerInsertProduct serverInsertProduct = response.body();
                Toast.makeText(getActivity(), serverInsertProduct.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ServerInsertProduct> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void initViewById(View view) {
        edName = view.findViewById(R.id.ed_name);
        edDescription = view.findViewById(R.id.ed_description);
        edPrice = view.findViewById(R.id.ed_price);
        btnAdd = view.findViewById(R.id.btn_add);
        btnExit = view.findViewById(R.id.btn_exit);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view_tag, fragment);
        fragmentTransaction.commit();
    }
}
