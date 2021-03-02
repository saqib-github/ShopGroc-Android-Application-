package com.example.shopgroc.fragment.user;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgroc.R;
import com.example.shopgroc.adapter.ProductAdapter;
import com.example.shopgroc.controller.OrderController;
import com.example.shopgroc.controller.ProductController;
import com.example.shopgroc.interfaces.ChildToParentCallback;
import com.example.shopgroc.model.Order;
import com.example.shopgroc.model.Product;

import java.util.List;


/**
 * @author  Abdul Rehman
 */
public class DashboardFragment extends Fragment implements View.OnClickListener,ProductAdapter.ProductClickListener,ChildToParentCallback {


    LinearLayout itemCardView;
    NavController navigationController;
    ChildToParentCallback varChildToParentCallback;
    RecyclerView recyclerViewBeverages, recyclerViewDrinks, recyclerViewDairy, recyclerViewPersonalCare, recyclerViewCleaners, recyclerViewBakingGoods;
    ProductAdapter productAdapterBeverages,productAdapterDrinks,productAdapterDairy,productAdapterPersonalCare,productAdapterCleaners,productAdapterBakingGoods;
    LinearLayoutManager linearLayoutManagerBeverages, linearLayoutManagerDrinks, linearLayoutManagerDairy, linearLayoutManagerPersonalCare, linearLayoutManagerCleaners, linearLayoutManagerBakingGoods;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);

    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        varChildToParentCallback = (ChildToParentCallback)context;
        varChildToParentCallback.hideBottomNav(false);
        varChildToParentCallback.hideStoreBottomNav(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemCardView = view.findViewById(R.id.itemId);
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
        productAdapterDrinks.setClickListener(this);
        recyclerViewBeverages.setLayoutManager(linearLayoutManagerBeverages);
        recyclerViewDrinks.setLayoutManager(linearLayoutManagerDrinks);
        recyclerViewDairy.setLayoutManager(linearLayoutManagerDairy);
        recyclerViewPersonalCare.setLayoutManager(linearLayoutManagerPersonalCare);
        recyclerViewCleaners.setLayoutManager(linearLayoutManagerCleaners);
        recyclerViewBakingGoods.setLayoutManager(linearLayoutManagerBakingGoods);

        recyclerViewBeverages.setAdapter(productAdapterBeverages);
        recyclerViewDrinks.setAdapter(productAdapterDrinks);
        recyclerViewDairy.setAdapter(productAdapterDairy);
        recyclerViewCleaners.setAdapter(productAdapterCleaners);
        recyclerViewPersonalCare.setAdapter(productAdapterPersonalCare);
        recyclerViewBakingGoods.setAdapter(productAdapterBakingGoods);
        getProductListBeverages();
        getProductListDrinks();
        getProductListDairy();
        getProductListCleaners();
        getProductListPersonalCare();
        getProductListBakingGoods();
        getOrders();
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
            public void onSuccess(boolean isSuccess, List<Product> productList) {
                productAdapterBeverages.setProductList(productList);

            }

            @Override
            public void onFailure(boolean isFailure, Exception e) {

            }
        });
    }
    private void getProductListDrinks(){
        ProductController.getInstance().getProductDrinks(new ProductController.ProductCallbackListener() {
            @Override
            public void onSuccess(boolean isSuccess, List<Product> productList) {
                productAdapterDrinks.setProductList(productList);
            }

            @Override
            public void onFailure(boolean isFailure, Exception e) {

            }
        });
    }

    private void getOrders(){
        OrderController.getInstance().getUserOrders(getContext(),new OrderController.OrderCallback() {
            @Override
            public void onSuccess(boolean isSuccess, List<Order> orderList) {

            }

            @Override
            public void onFailure(boolean isFailure, Exception e) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        navigationController.navigate(R.id.action_navigation_dashboard_to_itemDisplayFragment);
    }

    @Override
    public void onProductClick(Bundle bundle) {
        navigationController.navigate(R.id.action_navigation_dashboard_to_itemDisplayFragment,bundle);
    }

    @Override
    public void hideBottomNav(boolean hide) {
        hideBottomNav(false);
    }

    @Override
    public void hideStoreBottomNav(boolean hide) {
        hideStoreBottomNav(true);
    }

    @Override
    public void hideRiderBottomNav(boolean hide) {

    }

    public void raheemstorebtn(){
        CharSequence text = "Welcome to Raheem Store";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getActivity(), text, duration);
        toast.show();

    }



}
