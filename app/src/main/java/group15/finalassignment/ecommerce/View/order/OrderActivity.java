package group15.finalassignment.ecommerce.View.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import group15.finalassignment.ecommerce.R;
import group15.finalassignment.ecommerce.View.model.Cart;
import group15.finalassignment.ecommerce.View.model.Order;
import group15.finalassignment.ecommerce.View.model.Product;

public class OrderActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth auth;

    TextView totalCostLabel;
    EditText editTextAddress;
    Button confirmOrderBtn;
    ProgressDialog progressDialog;

    Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        totalCostLabel = findViewById(R.id.totalCostLabel);
        editTextAddress = findViewById(R.id.editTextAddress);
        confirmOrderBtn = findViewById(R.id.confirmOrderBtn);

        Object object = getIntent().getSerializableExtra("cart");
        if (object instanceof Cart) {
            cart = (Cart) object;
        }

        totalCostLabel.setText(String.valueOf(cart.getTotalCost()));
        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextAddress.getText().toString().equals("")) {
                    editTextAddress.setError("Delivery Address must not be empty!");
                } else {
                    progressDialog = new ProgressDialog(OrderActivity.this);
                    progressDialog.setMessage("Placing order!");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    Order order = new Order();
                    order.setEmail(auth.getCurrentUser().getEmail());
                    order.setAddress(editTextAddress.getText().toString());
                    order.setTotalCost(cart.getTotalCost());
                    order.setItemList(cart.getItemList());

                    db.collection("order")
                            .add(order)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        Intent finishIntent = new Intent();
                                        setResult(RESULT_OK, finishIntent);
                                        finish();
                                    } else {
                                        Toast.makeText(OrderActivity.this, "Fail to add order", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}