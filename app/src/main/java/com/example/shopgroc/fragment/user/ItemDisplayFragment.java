package com.example.shopgroc.fragment.user;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.example.shopgroc.R;
import com.example.shopgroc.controller.ImageController;
import com.example.shopgroc.manager.CartManager;
import com.example.shopgroc.model.CartItem;
import com.example.shopgroc.model.Product;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.shopgroc.utility.Constant.DataType.CART_ITEM;
import static com.example.shopgroc.utility.Constant.DataType.PRODUCT;


/**
 * @author  Abdul Rehman
 */
public class ItemDisplayFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "ItemDisplayFragment";

    Button buttonPlus, buttonMinus,buttonUpdateCart;
    ImageButton buttonAddToCart;
    TextView counter;
    int itemCount=0, maxLength = 10 , minLength = 0;
    Product product;
    ImageView displayImage;
    TextView textViewTitle,textViewPrice,textViewDescription;
    CartManager cartManager = CartManager.getInstance();
    CartItem cartItem;
    LinearLayout cartButtonContainer;
    boolean updateButtonVisibility=false;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_display, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        product = getProduct();
        if (product==null){
            cartItem = getCartItem();
            product=cartItem.getProduct();
            updateButtonVisibility=getUpdateButtonVisibility();
        }
        InIt(view);

    }


    private void InIt(View view) {
        displayImage = view.findViewById(R.id.displayImage);
        textViewTitle = view.findViewById(R.id.textViewTitle);
        textViewPrice = view.findViewById(R.id.textViewPrice);
        textViewDescription = view.findViewById(R.id.textViewDescription);
        buttonUpdateCart = view.findViewById(R.id.buttonUpdateCart);

        buttonMinus = view.findViewById(R.id.buttonMinus);
        buttonPlus = view.findViewById(R.id.buttonPlus);
        counter = view.findViewById(R.id.itemCount);
        buttonAddToCart = view.findViewById(R.id.buttonAddToCart);
        cartButtonContainer = view.findViewById(R.id.cartDisplayButtons);

        setButtonVisibility(updateButtonVisibility);

        if(product == null)return;
//        Drawable drawable = view.getContext().getResources().getDrawable(product.getImage());

        if (cartItem!=null){
            itemCount=cartItem.getQuantity();
            counter.setText(""+itemCount);
        }

//        displayImage.setImageDrawable(drawable);
        textViewTitle.setText(product.getTitle());
        textViewPrice.setText((int) product.getPrice()+"");
        textViewDescription.setText(product.getDescription());
        StorageReference ref = storageReference.child("productImage/"+product.getImage());
        ImageController.getInstance().loadImage(displayImage,ref);

        buttonPlus.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        buttonAddToCart.setOnClickListener(this);
        buttonUpdateCart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.buttonPlus){
            maxLength = Integer.parseInt(counter.getText().toString());
            if(maxLength < 10) {
                itemCount = itemCount + 1;
                counter.setText(Integer.toString(itemCount));
            }
        }
        else if(id == R.id.buttonMinus){
            minLength = Integer.parseInt(counter.getText().toString());
            if(minLength >= 1) {
                itemCount = itemCount - 1;
                counter.setText(Integer.toString(itemCount));
            }
        }
        else if(id==R.id.buttonAddToCart){
            if(itemCount<=0)return;
            Log.i(TAG,"Item Count is: " + itemCount);
            CartItem item = new CartItem(product,itemCount);
            Log.i(TAG, "onClick: " + product.getId());
//            Log.i(TAG, "onClick: " + item.getProduct().getId());
            if(cartManager.hasItem(item))cartManager.updateItem(item);
            else cartManager.addToCart(item);
            Navigation.findNavController(v).navigate(R.id.action_itemDisplayFragment_to_navigation_dashboard);
        }
        else if(id==R.id.buttonUpdateCart){
            CartManager.getInstance().getItemQuantity(cartItem);
            cartItem.setQuantity(itemCount);
            cartManager.updateItem(cartItem);
            Navigation.findNavController(v).navigate(R.id.action_itemDisplayFragment_to_navigation_cart);
        }
    }
    private void setButtonVisibility(boolean shouldShowUpdateButton){
        cartButtonContainer.setVisibility(shouldShowUpdateButton?View.GONE:View.VISIBLE);
        buttonUpdateCart.setVisibility(shouldShowUpdateButton?View.VISIBLE:View.GONE);
    }

    private Product getProduct(){
        return (Product) getBundle().getSerializable(PRODUCT);
    }
    private CartItem getCartItem() {
        return (CartItem) getBundle().getSerializable(CART_ITEM);
    }

    private boolean getUpdateButtonVisibility() {
        return getBundle().getBoolean("updateButtonVisibility",false);
    }
}
