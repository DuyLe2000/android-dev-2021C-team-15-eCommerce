package group15.finalassignment.ecommerce.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import group15.finalassignment.ecommerce.R;

public class DetailActivity extends AppCompatActivity {

    ImageView detailImg, addItems, removeItems;
    TextView name, description, price, rating;
    Button addCart, buy;

    // New Products
    NewProductsModel newProductsModel = null;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        firestore = FirebaseFirestore.getInstance();

        final Object obj = getIntent().getSerializableExtra("detail");
        if (obj instanceof NewProductsModel){
            newProductsModel = (NewProductsModel) obj;

        }

        detailImg = findViewById(R.id.detail_img);
        name = findViewById(R.id.detail_name);
        rating = findViewById(R.id.text_rating);
        description = findViewById(R.id.detail_description);
        price = findViewById(R.id.detail_price);
        addCart = findViewById(R.id.add_cart);
        buy = findViewById(R.id.buy);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);

        // New Products
        if (newProductsModel != null) {
            Glide.with(getApplicationContext()).load(newProductsModel.getImage_url()).into(detailImg);
            name.setText(newProductsModel.getName());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));
            rating.setText(newProductsModel.getRating());
        }
    }
}