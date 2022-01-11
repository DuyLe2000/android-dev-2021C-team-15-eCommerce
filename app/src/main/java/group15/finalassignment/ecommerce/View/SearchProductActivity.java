package group15.finalassignment.ecommerce.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import group15.finalassignment.ecommerce.R;
import group15.finalassignment.ecommerce.View.model.Product;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Locale;
import java.util.Objects;

public class SearchProductActivity extends AppCompatActivity {
    FirebaseFirestore db;

    ImageButton searchBtn;
    EditText searchField;
    LinearLayout resultArea;

    String category, searchName = "";

    /**
     * On Create method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        Intent intent = getIntent();
        category = intent.getExtras().getString("category");

        db = FirebaseFirestore.getInstance();

        resultArea = findViewById(R.id.displayArea);
        searchBtn = findViewById(R.id.button);
        searchField = findViewById(R.id.search_field);

        fetchProduct();

        searchBtn.setOnClickListener(view -> {
            searchName = searchField.getText().toString();
            fetchProduct();
        });
    }


    /**
     * Method to programmatically generate a product card under the search result
     *
     * @param product Product
     * @return A product card wrapped using a Linear Layout
     */
    @SuppressLint("SetTextI18n")
    private LinearLayout createProductCard(Product product) {

        // Create Linear Layout object
        LinearLayout productCard = new LinearLayout(SearchProductActivity.this);
        LinearLayout.LayoutParams lpForProductCard = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lpForProductCard.setMargins(60, 60, 60, 0);
        productCard.setLayoutParams(lpForProductCard);
        productCard.setOrientation(LinearLayout.HORIZONTAL);


        // Add product image
        ImageView productImg = new ImageView(SearchProductActivity.this);
        productImg.setBackground(Drawable.createFromPath(product.getImage_url()));
        LinearLayout.LayoutParams layoutParamsForImg = new LinearLayout.LayoutParams(350, 350);
        productImg.setLayoutParams(layoutParamsForImg);
        productImg.setImageResource(R.drawable.sample);
        Glide.with(SearchProductActivity.this).load(product.getImage_url()).into(productImg);


        // Add Linear Layout to contain product info
        LinearLayout infoContainer = new LinearLayout(SearchProductActivity.this);
        LinearLayout.LayoutParams lpForInfoContainer = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        lpForInfoContainer.setMarginStart(20);
        infoContainer.setLayoutParams(lpForInfoContainer);
        infoContainer.setOrientation(LinearLayout.VERTICAL);


        // Add product name
        TextView productName = new TextView(SearchProductActivity.this);
        LinearLayout.LayoutParams lpForName = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        productName.setLayoutParams(lpForName);
        productName.setTextColor(Color.parseColor("#000000"));
        productName.setTextSize(25);
        //productName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        productName.setText(product.getName());


        // Add product price
        LinearLayout priceContainer = new LinearLayout(SearchProductActivity.this);
        LinearLayout.LayoutParams lpForPriceContainer = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        priceContainer.setLayoutParams(lpForPriceContainer);
        priceContainer.setOrientation(LinearLayout.HORIZONTAL);


        TextView priceLabel = new TextView(SearchProductActivity.this);
        priceLabel.setText("Price:");
        priceLabel.setTextSize(22);


        TextView priceValue = new TextView(SearchProductActivity.this);
        LinearLayout.LayoutParams lpForPriceValue = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lpForPriceValue.setMarginStart(30);
        priceValue.setLayoutParams(lpForPriceValue);
        priceValue.setTextSize(22);
        priceValue.setText("$" + String.valueOf(product.getPrice()));


        // Add product category
        LinearLayout categoryContainer = new LinearLayout(SearchProductActivity.this);
        LinearLayout.LayoutParams lpForCategoryContainer = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        categoryContainer.setLayoutParams(lpForCategoryContainer);
        categoryContainer.setOrientation(LinearLayout.HORIZONTAL);


        TextView categoryLabel = new TextView(SearchProductActivity.this);
        categoryLabel.setText("Category:");
        categoryLabel.setTextSize(22);


        TextView categoryValue = new TextView(SearchProductActivity.this);
        LinearLayout.LayoutParams lpForCategoryValue = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lpForCategoryValue.setMarginStart(30);
        categoryValue.setLayoutParams(lpForCategoryValue);
        categoryValue.setTextSize(22);
        categoryValue.setText(product.getCategory());


        // Add rating
        LinearLayout ratingContainer = new LinearLayout(SearchProductActivity.this);
        LinearLayout.LayoutParams lpForRatingContainer = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        ratingContainer.setLayoutParams(lpForRatingContainer);
        ratingContainer.setOrientation(LinearLayout.HORIZONTAL);


        TextView ratingLabel = new TextView(SearchProductActivity.this);
        ratingLabel.setText("Rating:");
        ratingLabel.setTextSize(22);


        TextView ratingValue = new TextView(SearchProductActivity.this);
        LinearLayout.LayoutParams lpForRatingValue = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lpForRatingValue.setMarginStart(30);
        ratingValue.setLayoutParams(lpForRatingValue);
        ratingValue.setTextSize(22);
        ratingValue.setText(String.valueOf(product.getRating()));


        // Add everything up
        priceContainer.addView(priceLabel);
        priceContainer.addView(priceValue);

        categoryContainer.addView(categoryLabel);
        categoryContainer.addView(categoryValue);

        ratingContainer.addView(ratingLabel);
        ratingContainer.addView(ratingValue);

        infoContainer.addView(productName);
        infoContainer.addView(priceContainer);
        infoContainer.addView(categoryContainer);
        infoContainer.addView(ratingContainer);

        productCard.addView(productImg);
        productCard.addView(infoContainer);

        productCard.setOnClickListener(view -> {
            Intent intent = new Intent(SearchProductActivity.this, DetailActivity.class);
            intent.putExtra("detail", product);
            startActivity(intent);
            // finish();
            Toast.makeText(SearchProductActivity.this, product.getName() + " clicked!", Toast.LENGTH_SHORT).show();
        });

        return productCard;
    }

    private void displayResult(QuerySnapshot queryDocumentSnapshots) {
        resultArea.removeAllViews();
        if (queryDocumentSnapshots == null) {
            return;
        }
        for (QueryDocumentSnapshot document:queryDocumentSnapshots) {
            if (document.getString("name").toLowerCase(Locale.ROOT).contains(searchName.toLowerCase(Locale.ROOT))) {
                Product product = document.toObject(Product.class);
                resultArea.addView(createProductCard(product));
            }
        }
    }

    private void fetchProduct() {
        CollectionReference collectionReference = db.collection("AllProducts");
        Query productQuery = null;
        if (!category.isEmpty()) {
            productQuery = collectionReference.whereEqualTo("category", category);
        }
        if (productQuery != null) {
            productQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        displayResult(task.getResult());
                    } else {
                        Toast.makeText(SearchProductActivity.this, "Fail to fetch product!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        displayResult(task.getResult());
                    } else {
                        Toast.makeText(SearchProductActivity.this, "Fail to fetch product!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}