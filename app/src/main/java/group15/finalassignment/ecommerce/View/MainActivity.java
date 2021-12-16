package group15.finalassignment.ecommerce.View;

import androidx.appcompat.app.AppCompatActivity;
import group15.finalassignment.ecommerce.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    setContentView(R.layout.activity_main);


    Button btnMainLogin = (Button) findViewById(R.id.main_login_btn);
    btnMainLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent moveToUserLogin = new Intent(MainActivity.this,UserLoginActivity.class);
        startActivity(moveToUserLogin);
        finish();
      }
    });

    Button btnMainRegister = (Button) findViewById(R.id.main_join_now_btn);
    btnMainRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent moveToUserRegister = new Intent(MainActivity.this,UserRegisterActivity.class);
        startActivity(moveToUserRegister);
        finish();
      }
    });


  }
}

// Duy was here Muahahahahaa