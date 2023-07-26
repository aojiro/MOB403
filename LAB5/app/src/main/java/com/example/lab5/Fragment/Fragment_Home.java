package com.example.lab5.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lab5.R;

public class Fragment_Home extends Fragment {
    Button btnViewProduct, btnNewProduct;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViewById(view);
        initClickListener();
        return view;
    }

    private void initClickListener() {
        btnNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Fragment_Add_Product());
            }
        });
        btnViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Fragment_View_Product());
            }
        });
    }

    private void initViewById(View view) {
        btnNewProduct = view.findViewById(R.id.btn_add_product);
        btnViewProduct = view.findViewById(R.id.btn_view_product);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view_tag, fragment);
        fragmentTransaction.commit();
    }
}
