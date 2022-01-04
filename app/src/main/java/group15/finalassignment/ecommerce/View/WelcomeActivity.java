package group15.finalassignment.ecommerce.View;

import androidx.appcompat.app.AppCompatActivity;
import group15.finalassignment.ecommerce.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {
  FirebaseAuth authenticator;

  private TextView skipTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome);

    //FireBase Authenticator
    authenticator = FirebaseAuth.getInstance();

    Button btnMainLogin = (Button) findViewById(R.id.main_login_btn);
    btnMainLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent moveToUserLogin = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(moveToUserLogin);
        finish();
      }
    });

    Button btnMainRegister = (Button) findViewById(R.id.main_join_now_btn);
    btnMainRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent moveToUserRegister = new Intent(WelcomeActivity.this, RegisterActivity.class);
        startActivity(moveToUserRegister);
        finish();
      }
    });

    skipTextView = (TextView) findViewById(R.id.skipTextView);
    /** Skip TextView*/
    skipTextView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        FirebaseAuth.getInstance().signOut();
        goToMainActivity();
      }
    });

  }

  private void goToMainActivity() {
    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
    //kill previous activities
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
    Toast.makeText(WelcomeActivity.this, "Welcome to the Team 15 Ecommerce App!", Toast.LENGTH_SHORT).show();
    finish();
  }

}
