package com.example.lab5.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lab5.Adapter.AdapterListViewItemProduct;
import com.example.lab5.Interface.ItemClickListener;
import com.example.lab5.Interface.ProductAPI;
import com.example.lab5.Model.Product;
import com.example.lab5.R;
import com.example.lab5.RetrofitServer.ServerDeleteProduct;
import com.example.lab5.RetrofitServer.ServerSelectProduct;
import com.example.lab5.RetrofitServer.ServerUpdateProduct;
import com.example.lab5.URLServer.PathURLServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_View_Product extends Fragment {
    ListView lvProduct;
    Button btnBack;
    private AdapterListViewItemProduct adapterListViewItemProduct;
    ProgressDialog progressDialog;
    private List<Product> productList;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog;
    TextView tvID;
    EditText edName, edPrice, edDescription;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_product, container, false);

        initViewByID(view);

        initProgressDialog();

        initGetData();

        initAdater();

        initClickListener();

        return view;
    }

    private void initAdater() {
        adapterListViewItemProduct = new AdapterListViewItemProduct(getContext());

        adapterListViewItemProduct.setOnItemDeleteClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                deleteProductAPI(position);
            }
        });
        adapterListViewItemProduct.setOnItemUpdateClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Product product = productList.get(position);
                dialogUpdate(product);
                show();

            }
        });
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Đang tải xuống ...");
        progressDialog.setCancelable(false);


    }

    private void initClickListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view_tag, new Fragment_Home());
                fragmentTransaction.commit();
            }
        });
    }

    private void initGetData() {
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductAPI productAPI = retrofit.create(ProductAPI.class);
        Call<ServerSelectProduct> call = productAPI.selectProduct();
        call.enqueue(new Callback<ServerSelectProduct>() {
            @Override
            public void onResponse(Call<ServerSelectProduct> call, Response<ServerSelectProduct> response) {
                ServerSelectProduct serverSelectProduct = response.body();
                try {

                    productList = new ArrayList<>(Arrays.asList(serverSelectProduct.getProduct()));
                    adapterListViewItemProduct.setListProduct(productList);
                    lvProduct.setAdapter(adapterListViewItemProduct);
                    hideProgress();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    hideProgress();
                }

            }

            @Override
            public void onFailure(Call<ServerSelectProduct> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                hideProgress();
            }
        });
    }

    private void initViewByID(View view) {
        lvProduct = view.findViewById(R.id.lv_product);
        btnBack = view.findViewById(R.id.btn_back);
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

    private void deleteProductAPI(int position) {
        Product product = productList.get(position);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductAPI productAPI = retrofit.create(ProductAPI.class);
        Call<ServerDeleteProduct> call = productAPI.deleteProduct(product.getId());
        call.enqueue(new Callback<ServerDeleteProduct>() {
            @Override
            public void onResponse(Call<ServerDeleteProduct> call, Response<ServerDeleteProduct> response) {
                ServerDeleteProduct serverDeleteProduct = response.body();
                Toast.makeText(getContext(), serverDeleteProduct.getMessage(), Toast.LENGTH_LONG).show();
                reFresh();
            }

            @Override
            public void onFailure(Call<ServerDeleteProduct> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void reFresh() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view_tag, new Fragment_View_Product());
        fragmentTransaction.commit();
    }

    public void dialogUpdate(Product product) {
        layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.dialog_update_product, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(view)
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Update", null);
        alertDialog = builder.create();
        tvID = view.findViewById(R.id.tv_id);
        edName = view.findViewById(R.id.ed_name);
        edDescription = view.findViewById(R.id.ed_description);
        edPrice = view.findViewById(R.id.ed_price);
        tvID.setText(String.valueOf(product.getId()));
        edName.setText(product.getName());
        edPrice.setText(String.valueOf(product.getPrice()));
        edDescription.setText(product.getDescription());

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            int id = Integer.parseInt(tvID.getText().toString());
                            String name = edName.getText().toString();
                            int price = Integer.parseInt(edPrice.getText().toString());
                            String description = edDescription.getText().toString();
                            if (name.length() != 0 && edPrice.getText().length() != 0 && description.length() != 0) {
                                updateDataAPI(id, name, price, description);

                            } else {
                                Toast.makeText(getContext(), "vui lòng nhập dữ liệu vào", Toast.LENGTH_LONG).show();
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Sai định dạng", Toast.LENGTH_LONG).show();
                        }


                    }
                });
            }
        });
    }

    public void show() {
        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
    }

    public void hide() {
        if (alertDialog.isShowing()) {
            alertDialog.dismiss();

        }
    }


    private void updateDataAPI(int id, String name, int price, String description) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductAPI productAPI = retrofit.create(ProductAPI.class);
        Call<ServerUpdateProduct> call = productAPI.updateProduct(id, name, price, description);
        call.enqueue(new Callback<ServerUpdateProduct>() {
            @Override
            public void onResponse(Call<ServerUpdateProduct> call, Response<ServerUpdateProduct> response) {
                ServerUpdateProduct serverUpdateProduct = response.body();
                Toast.makeText(getContext(), serverUpdateProduct.getMessage(), Toast.LENGTH_LONG).show();
                hide();
                reFresh();
            }

            @Override
            public void onFailure(Call<ServerUpdateProduct> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                hide();
            }
        });
    }

}
