package group15.finalassignment.ecommerce.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import group15.finalassignment.ecommerce.R;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore db;

    Fragment homeFragment;
    ImageButton profileBtn, cartBtn, searchBtn;

    private final ActivityResultLauncher<Intent> registerOrLogin = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        if (auth.getCurrentUser() != null) {
                            cartBtn.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

    private final ActivityResultLauncher<Intent> viewProfile = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        if (auth.getCurrentUser() == null) {
                            cartBtn.setVisibility(View.GONE);
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        loadFragment(homeFragment);

        profileBtn = (ImageButton) findViewById(R.id.profileBtn);
        cartBtn = (ImageButton) findViewById(R.id.cartBtn);
        searchBtn = findViewById(R.id.searchBtn);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (auth.getCurrentUser() != null) {
            cartBtn.setVisibility(View.VISIBLE);
        }

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                    registerOrLogin.launch(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                    viewProfile.launch(intent);
                }
            }
        });

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() != null) {
                    Intent intent = new Intent(MainActivity.this, CartActivity.class);
                    startActivity(intent);
                }
            }
        });

        searchBtn.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, SearchProductActivity.class);
            startActivity(i);
        });
    }

    private void loadFragment(Fragment homeFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, homeFragment);
        transaction.commit();
    }
}