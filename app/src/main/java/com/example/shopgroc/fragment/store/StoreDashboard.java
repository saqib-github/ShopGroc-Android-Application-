package com.example.shopgroc.fragment.store;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgroc.R;
import com.example.shopgroc.adapter.ProductAdapter;
import com.example.shopgroc.controller.ProductController;
import com.example.shopgroc.interfaces.ChildToParentCallback;
import com.example.shopgroc.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


/**
 * @author Abdul Rehman
 */
public class StoreDashboard extends Fragment implements View.OnClickListener, ProductAdapter.ProductClickListener {


    private static final String TAG = "storeDashbpard";
    ProductAdapter productAdapterBeverages,productAdapterDrinks,productAdapterDairy,productAdapterPersonalCare,productAdapterCleaners,productAdapterBakingGoods;
    RecyclerView recyclerViewBeverages,recyclerViewDrinks,recyclerViewDairy, recyclerViewPersonalCare, recyclerViewCleaners, recyclerViewBakingGoods;
    LinearLayoutManager linearLayoutManagerBeverages,linearLayoutManagerDrinks,linearLayoutManagerDairy, linearLayoutManagerPersonalCare, linearLayoutManagerCleaners, linearLayoutManagerBakingGoods;
    NavController navigationController;
    ChildToParentCallback varChildToParentCallback;

    FloatingActionButton addItem;

    ProductController productController = ProductController.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InIt(view);
    }

    private void InIt(View view) {
        navigationController = Navigation.findNavController(view);
        recyclerViewBeverages = view.findViewById(R.id.recyclerViewBeverages);
        recyclerViewDrinks = view.findViewById(R.id.recyclerViewDrinks);
        recyclerViewDairy = view.findViewById(R.id.recyclerViewDairy);
        recyclerViewPersonalCare = view.findViewById(R.id.recyclerViewPersonalCare);
        recyclerViewCleaners = view.findViewById(R.id.recyclerViewCleaners);
        recyclerViewBakingGoods = view.findViewById(R.id.recyclerViewBakingGoods);
        addItem = view.findViewById(R.id.addItem);

        linearLayoutManagerBeverages = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        linearLayoutManagerDrinks = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        linearLayoutManagerDairy = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        linearLayoutManagerPersonalCare = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        linearLayoutManagerCleaners = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        linearLayoutManagerBakingGoods = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);

        productAdapterBeverages = new ProductAdapter();
        productAdapterDrinks = new ProductAdapter();
        productAdapterDairy = new ProductAdapter();
        productAdapterCleaners = new ProductAdapter();
        productAdapterPersonalCare = new ProductAdapter();
        productAdapterBakingGoods = new ProductAdapter();
        productAdapterBeverages.setClickListener(this);
        recyclerViewBeverages.setLayoutManager(linearLayoutManagerBeverages);
        recyclerViewDrinks.setLayoutManager(linearLayoutManagerDrinks);
        recyclerViewDairy.setLayoutManager(linearLayoutManagerDairy);
        recyclerViewPersonalCare.setLayoutManager(linearLayoutManagerPersonalCare);
        recyclerViewCleaners.setLayoutManager(linearLayoutManagerCleaners);
        recyclerViewBakingGoods.setLayoutManager(linearLayoutManagerBakingGoods);



        productAdapterDrinks.setClickListener(this);

        recyclerViewBeverages.setAdapter(productAdapterBeverages);
        recyclerViewDrinks.setAdapter(productAdapterDrinks);
        recyclerViewDairy.setAdapter(productAdapterDairy);
        recyclerViewCleaners.setAdapter(productAdapterCleaners);
        recyclerViewPersonalCare.setAdapter(productAdapterPersonalCare);
        recyclerViewBakingGoods.setAdapter(productAdapterBakingGoods);
        addItem.setOnClickListener(this);
        getProductListBeverages();
        getProductListDrinks();
        getProductListDairy();
        getProductListCleaners();
        getProductListPersonalCare();
        getProductListBakingGoods();
    }
    private void getProductListBakingGoods() {
        ProductController.getInstance().getProductBakingGoods(new ProductController.ProductCallbackListener() {
            @Override
            public void onSuccess(boolean isSuccess, List<Product> productList) {
                productAdapterBakingGoods.setProductList(productList);

            }

            @Override
            public void onFailure(boolean isFailure, Exception e) {

            }
        });
    }

    private void getProductListPersonalCare() {
        ProductController.getInstance().getProductPersonalCare(new ProductController.ProductCallbackListener() {
            @Override
            public void onSuccess(boolean isSuccess, List<Product> productList) {
                productAdapterPersonalCare.setProductList(productList);

            }

            @Override
            public void onFailure(boolean isFailure, Exception e) {

            }
        });
    }

    private void getProductListCleaners() {
        ProductController.getInstance().getProductCleaners(new ProductController.ProductCallbackListener() {
            @Override
            public void onSuccess(boolean isSuccess, List<Product> productList) {
                productAdapterCleaners.setProductList(productList);

            }

            @Override
            public void onFailure(boolean isFailure, Exception e) {

            }
        });
    }

    private void getProductListDairy() {
        ProductController.getInstance().getProductDairy(new ProductController.ProductCallbackListener() {
            @Override
            public void onSuccess(boolean isSuccess, List<Product> productList) {
                productAdapterDairy.setProductList(productList);

            }

            @Override
            public void onFailure(boolean isFailure, Exception e) {

            }
        });
    }

    private void getProductListBeverages(){
        ProductController.getInstance().getProductBeverages(new ProductController.ProductCallbackListener() {
            @Override
            public void onSuccess(boolean isSuccess, List<Product> productLista) {
                productAdapterBeverages.setProductList(productLista);
            }

            @Override
            public void onFailure(boolean isFailure, Exception e) {

            }
        });
    }
    private void getProductListDrinks(){
        ProductController.getInstance().getProductDrinks(new ProductController.ProductCallbackListener() {
            @Override
            public void onSuccess(boolean isSuccess, List<Product> productLista) {
                productAdapterDrinks.setProductList(productLista);
            }

            @Override
            public void onFailure(boolean isFailure, Exception e) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.addItem){
            navigationController.navigate(R.id.action_storeDashboard_to_addItemToStore);
        }
    }

    @Override
    public void onProductClick(Bundle bundle) {
        navigationController.navigate(R.id.action_store_navigation_dashboard_to_itemDisplayFragmentStore,bundle);
    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        varChildToParentCallback = (ChildToParentCallback)context;
        varChildToParentCallback.hideBottomNav(true);
        varChildToParentCallback.hideStoreBottomNav(false);
        varChildToParentCallback.hideRiderBottomNav(true);
    }
}
