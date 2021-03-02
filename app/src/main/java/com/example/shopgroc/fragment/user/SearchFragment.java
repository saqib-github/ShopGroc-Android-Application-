package com.example.shopgroc.fragment.user;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgroc.R;
import com.example.shopgroc.adapter.SearchItemAdapter;
import com.example.shopgroc.controller.ProductController;
import com.example.shopgroc.interfaces.ChildToParentCallback;
import com.example.shopgroc.model.Product;

import java.util.List;


/**
 * @author Saqib Javed
 */
public class SearchFragment extends Fragment implements View.OnClickListener, SearchItemAdapter.ProductClickListener,ChildToParentCallback {
    ChildToParentCallback varChildToParentCallback;
    SearchItemAdapter searchItemAdapter;
    TextView searchItem;
    Button searchButton;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InIt(view);
    }

    private void InIt(View view) {
        searchItem = view.findViewById(R.id.searchItem);
        searchButton = view.findViewById(R.id.searchButton);
        recyclerView = view.findViewById(R.id.searchRecycler);
        linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchItemAdapter = new SearchItemAdapter();
        searchItemAdapter.setClickListener(this);
        recyclerView.setAdapter(searchItemAdapter);
        searchButton.setOnClickListener(this);
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        varChildToParentCallback = (ChildToParentCallback)context;
        varChildToParentCallback.hideBottomNav(false);
        varChildToParentCallback.hideStoreBottomNav(true);
        varChildToParentCallback.hideRiderBottomNav(true);
    }

    private void searchProductList(String searchKeyword){
        ProductController.getInstance().getSearchItem(searchKeyword,new ProductController.ProductCallbackListener() {
            @Override
            public void onSuccess(boolean isSuccess, List<Product> productList) {
                searchItemAdapter.setProductList(productList);
            }
            @Override
            public void onFailure(boolean isFailure, Exception e) {
                Toast.makeText(getContext(), "No Item Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.searchButton){
            if(searchItem.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Please Enter Something in Search Bar", Toast.LENGTH_SHORT).show();
            }
            else{
                searchProductList(searchItem.getText().toString());
            }
        }
    }

    @Override
    public void onProductClick(Bundle bundle) {

    }
    @Override
    public void hideBottomNav(boolean hide) {

    }
    @Override
    public void hideStoreBottomNav(boolean hide) {

    }
    @Override
    public void hideRiderBottomNav(boolean hide) {

    }
}
