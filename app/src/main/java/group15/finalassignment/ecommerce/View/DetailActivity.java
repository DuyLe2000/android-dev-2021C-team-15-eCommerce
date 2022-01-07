package group15.finalassignment.ecommerce.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import group15.finalassignment.ecommerce.R;
import group15.finalassignment.ecommerce.View.model.Cart;
import group15.finalassignment.ecommerce.View.model.CartItem;
import group15.finalassignment.ecommerce.View.model.Product;
import group15.finalassignment.ecommerce.View.order.OrderActivity;

public class DetailActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth auth;

    ImageView detailImg, addItemBtn, removeItemBtn;
    TextView name, description, price, rating, quantityLabel;
    Button addCartBtn, buyBtn;
    ProgressDialog progressDialog;

    Long quantity = 1L;

    // New Products
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detail");
        if (obj instanceof Product) {
            product = (Product) obj;
        }

        detailImg = findViewById(R.id.detail_img);
        name = findViewById(R.id.detail_name);
        rating = findViewById(R.id.text_rating);
        description = findViewById(R.id.detail_description);
        price = findViewById(R.id.detail_price);
        addCartBtn = findViewById(R.id.addCartBtn);
        buyBtn = findViewById(R.id.buyBtn);
        addItemBtn = findViewById(R.id.addItemBtn);
        removeItemBtn = findViewById(R.id.removeItemBtn);
        quantityLabel = findViewById(R.id.quantityLabel);

        // New Products
        if (product != null) {
            Glide.with(getApplicationContext()).load(product.getImage_url()).into(detailImg);
            name.setText(product.getName());
            description.setText(product.getDescription());
            price.setText(String.valueOf(product.getPrice()));
            rating.setText(product.getRating());
        }

        // Button action
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                quantityLabel.setText(String.valueOf(quantity));
            }
        });

        removeItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    quantityLabel.setText(String.valueOf(quantity));
                }
            }
        });

        addCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    Toast.makeText(DetailActivity.this, "User must sign in!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog = new ProgressDialog(DetailActivity.this);
                progressDialog.setMessage("Adding order to cart!");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                CartItem cartItem = new CartItem(product.getName(), quantity, quantity * product.getPrice());
                db.collection("accounts")
                        .document("0945731031")
                        .update("cart", FieldValue.arrayUnion(cartItem))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(DetailActivity.this, "Cart item add successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    Toast.makeText(DetailActivity.this, "User must sign in!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cart cart = new Cart();
                cart.getItemList().add(new CartItem(product.getName(), quantity, product.getPrice()));

                Intent intent = new Intent(DetailActivity.this, OrderActivity.class);
                intent.putExtra("cart", cart);
                startActivity(intent);
            }
        });
    }
}