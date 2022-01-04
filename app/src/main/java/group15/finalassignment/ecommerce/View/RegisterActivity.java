package group15.finalassignment.ecommerce.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import group15.finalassignment.ecommerce.R;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = RegisterActivity.class.getName();

    private FirebaseAuth mAuth;
    FireBaseHandler fireBaseHandler = new FireBaseHandler();
    private TextInputEditText mName, mPhoneNum, mEmail, mAddress, mPassword;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //FireBase
        mAuth = FirebaseAuth.getInstance();

        //Fields
        mName = (TextInputEditText) findViewById(R.id.name);
        mPhoneNum = (TextInputEditText) findViewById(R.id.phoneNum);
        mEmail = (TextInputEditText) findViewById(R.id.email);
        mAddress = (TextInputEditText) findViewById(R.id.address);
        mPassword = (TextInputEditText) findViewById(R.id.password);


        //Register Button
        registerBtn = (Button) findViewById(R.id.registerBtn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString().trim();
                String phoneNum = mPhoneNum.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString();
                String address = mAddress.getText().toString();

                if (name.length() != 0 && phoneNum.length() != 0 && email.length() != 0 &&
                        password.length() != 0 && address.length() != 0) { //All field must be filled

//                    Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
//                    intent.putExtra("resMessage", "Account created");
//                    intent.putExtra("name", name);
//                    intent.putExtra("phoneNum", phoneNum);
//                    intent.putExtra("password", password);
//                    intent.putExtra("email", email);
//                    intent.putExtra("address", address);
//                    startActivity(intent);
                    onClickVerifyPhoneNumber(phoneNum);
//                    Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
//                    intent.putExtra("resMessage", "Account created");
//                    intent.putExtra("name", name);
//                    intent.putExtra("phoneNum", phoneNum);
//                    intent.putExtra("password", password);
//                    intent.putExtra("email", email);
//                    intent.putExtra("address", address);
//                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Make sure you enter every field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onClickVerifyPhoneNumber(String phoneNum) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNum)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(RegisterActivity.this, "Verification Failed!", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationId, forceResendingToken);
                                goToOptActivity(phoneNum, verificationId);
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private void goToOptActivity(String phoneNum, String verificationId) {
        String name = mName.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString();
        String address = mAddress.getText().toString();

        Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
        intent.putExtra("resMessage", "Account created");
        intent.putExtra("name", name);
        intent.putExtra("phoneNum", phoneNum);
        intent.putExtra("verificationId", verificationId);
        intent.putExtra("password", password);
        intent.putExtra("email", email);
        intent.putExtra("address", address);
        startActivity(intent);
    }
}
//    private void register(String email, String password, String name, String address) {
//        authenticator.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    FirebaseUser user = authenticator.getCurrentUser();
//                    String email2 = user.getEmail().toString();
//                    System.out.println(email2);
//                    fireBaseHandler.addAccount(name, phoneNumber, email, address);
//                    Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
//                    intent.putExtra("resMessage","Account created");
//                    intent.putExtra("name",name);
//                    intent.putExtra("email",email);
//                    intent.putExtra("address",address);
//                    startActivity(intent);
//                    setResult(RESULT_OK,intent);
//                    finish();
//                }
//                else {
//                    try {
//                        throw task.getException();
//                    } catch(FirebaseAuthWeakPasswordException e) {
//                        aPassword.setError("Password not long enough");
//                        aPassword.requestFocus();
//                    } catch(FirebaseAuthInvalidCredentialsException e) {
//                        aEmail.setError("Invalid Email");
//                        aEmail.requestFocus();
//                    } catch(FirebaseAuthUserCollisionException e) {
//                        aEmail.setError("User already exists");
//                        aEmail.requestFocus();
//                    } catch(Exception e) {
//                        Log.e("Other Exceptions", e.getMessage());
//                    }
//                }
//            }
//        });
//    }
