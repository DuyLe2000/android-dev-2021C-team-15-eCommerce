package group15.finalassignment.ecommerce.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import group15.finalassignment.ecommerce.R;
import group15.finalassignment.ecommerce.View.model.Product;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class SearchProductActivity extends AppCompatActivity {

  /*
  * Variables declared for testing purposes
   */
  private final Product testProduct = new Product();


  /**
   * On Create method
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_product);


    //testProduct.setImage_url("https://live.staticflickr.com/8890/18196097556_ac28500950_b.jpg");
    // testProduct.setImage_url("src/main/res/drawable/sample.png");
    testProduct.setName("Bitis Hunter");
    testProduct.setPrice(12L);

    LinearLayout resultArea = findViewById(R.id.displayArea);

    for (int i = 0; i < 3; i++) {
      resultArea.addView(createProductCard(testProduct));
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    db.collection("AllProducts")
            .get()
            .addOnCompleteListener(task -> {
              if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                    CategoryModel categoryModel = document.toObject(CategoryModel.class);
//                    categoryModelList.add(categoryModel);
//                    categoryAdapter.notifyDataSetChanged();

                  Product product = document.toObject(Product.class);
                  resultArea.addView(createProductCard(product));
                }
              } else {
                Toast.makeText(SearchProductActivity.this, "Error", Toast.LENGTH_SHORT).show();
              }
            });
  }


  /**
   * Method to programmatically generate a product card under the search result
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
    RelativeLayout.LayoutParams layoutParamsForImg = new RelativeLayout.LayoutParams(200, 200);
    productImg.setLayoutParams(layoutParamsForImg);
    productImg.setImageResource(R.drawable.sample);


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
    productName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
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
    priceValue.setTextSize(product.getPrice());
    LinearLayout.LayoutParams lpForPriceValue = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
    );
    lpForPriceValue.setMarginStart(30);
    priceValue.setLayoutParams(lpForPriceValue);
    priceValue.setTextSize(22);
    priceValue.setText(String.valueOf(product.getPrice()));


    // Add everything up
    priceContainer.addView(priceLabel);
    priceContainer.addView(priceValue);

    infoContainer.addView(productName);
    infoContainer.addView(priceContainer);

    productCard.addView(productImg);
    productCard.addView(infoContainer);

    return productCard;
  }
}