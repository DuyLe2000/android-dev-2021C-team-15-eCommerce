package group15.finalassignment.ecommerce.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import group15.finalassignment.ecommerce.R;

public class OtpActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    FireBaseHandler fireBaseHandler = new FireBaseHandler();

    public static final String TAG = OtpActivity.class.getName();

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mForceResendingToken;

    private EditText otpEditText;
    private Button verifyBtn;
    private ProgressBar verifyProgressBar;
    private TextView otpAgainTextView;
    private String mName, mPhoneNum, mEmail, mAddress, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        // Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // retrieve data from Intent
        getDataContent();

        otpEditText = findViewById(R.id.otpEditText);
        verifyProgressBar = findViewById(R.id.verifyProgressBar);
        verifyBtn = findViewById(R.id.verifyBtn);
        otpAgainTextView = findViewById(R.id.otpAgainTextView);

//        sendVerificationCodeToUser(mPhoneNum);

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = otpEditText.getText().toString().trim();

                if (otp.isEmpty() || otp.length() < 6) {
                    otpEditText.setError("Wrong OTP...");
                    otpEditText.requestFocus();
                    return;
                }
                verifyProgressBar.setVisibility(View.VISIBLE);
//                Toast.makeText(OtpActivity.this, otp,
//                        Toast.LENGTH_SHORT).show();
                System.out.println(otp);
                onClickSendOtp(otp);
            }
        });

        otpAgainTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSendOtpAgain();
            }
        });

    }

    private void getDataContent() {
        mName = getIntent().getStringExtra("name");
        mEmail = getIntent().getStringExtra("email");
        mPhoneNum = getIntent().getStringExtra("phoneNum");
        mVerificationId = getIntent().getStringExtra("verificationId");
        mAddress = getIntent().getStringExtra("address");
        mPassword = getIntent().getStringExtra("password");

        System.out.println(mPhoneNum + "sdsadas");

    }

    private void onClickSendOtp(String otp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
        signInWithPhoneAuthCredential(credential);
    }

    private void onClickSendOtpAgain() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mPhoneNum)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setForceResendingToken(mForceResendingToken)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(OtpActivity.this, "Verification Failed!", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationId, forceResendingToken);
                                mVerificationId = verificationId;
                                mForceResendingToken = forceResendingToken;
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

//    private void sendVerificationCodeToUser(String mPhoneNum) {
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber(mPhoneNum)       // Phone number to verify
//                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                        .setActivity(this)                 // Activity (for callback binding)
//                        .setForceResendingToken(mForceResendingToken)
//                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                            @Override
//                            public void onVerificationCompleted(PhoneAuthCredential credential) {
//                                // This callback will be invoked in two situations:
//                                // 1 - Instant verification. In some cases the phone number can be instantly
//                                //     verified without needing to send or enter a verification code.
//                                // 2 - Auto-retrieval. On some devices Google Play services can automatically
//                                //     detect the incoming verification SMS and perform verification without
//                                //     user action.
//                                Log.d(TAG, "onVerificationCompleted:" + credential);
//
//                                signInWithPhoneAuthCredential(credential);
//                            }
//
//                            @Override
//                            public void onVerificationFailed(FirebaseException e) {
//                                // This callback is invoked in an invalid request for verification is made,
//                                // for instance if the the phone number format is not valid.
//                                Log.w(TAG, "onVerificationFailed", e);
//
//                                if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                                    // Invalid request
//                                } else if (e instanceof FirebaseTooManyRequestsException) {
//                                    // The SMS quota for the project has been exceeded
//                                }
//
//                                // Show a message and update the UI
//                                Toast.makeText(OtpActivity.this, "Verification Failed!",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onCodeSent(@NonNull String verificationId,
//                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
//                                // The SMS verification code has been sent to the provided phone number, we
//                                // now need to ask the user to enter the code and then construct a credential
//                                // by combining the code with a verification ID.
//                                Log.d(TAG, "onCodeSent:" + verificationId);
//
//                                super.onCodeSent(verificationId, token);
//                                // Save verification ID and resending token so we can use them later
//                                mVerificationId = verificationId;
//                                mForceResendingToken = token;
//                            }
//                        })
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            Toast.makeText(OtpActivity.this, "Your Account has been created successfully!", Toast.LENGTH_SHORT).show();

//                            FirebaseUser user = task.getResult().getUser();

                            mAuth.createUserWithEmailAndPassword(mEmail, mPassword);
                            FirebaseUser user = mAuth.getCurrentUser();
                            System.out.println(user.getPhoneNumber().toString());
                            fireBaseHandler.addAccount(mName, mPhoneNum, mEmail, mAddress);
                            System.out.println("haha" + mName + mPhoneNum + mEmail + mAddress);
                            Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("resMessage","Account created");
                            intent.putExtra("name" ,mName);
                            intent.putExtra("phoneNum", mPhoneNum);
                            intent.putExtra("email", mEmail);
                            intent.putExtra("address", mAddress);
                            setResult(RESULT_OK,intent);
                            startActivity(intent);
                            finish();
//                            System.out.println("haaa" + mEmail + mPassword + mPhoneNum + mName + mAddress);
//                            register(mEmail, mPassword, mPhoneNum, mName, mAddress);
//                            System.out.println("awww");
                            // Update UI
                            Toast.makeText(OtpActivity.this, "correct OPT", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(OtpActivity.this, MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                            finish();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OtpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

//    private void register(String email, String password, String phoneNum, String name, String address) {
//        mAuth.signOut();
//        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    FirebaseUser user = mAuth.getCurrentUser();
//                    System.out.println(user.getPhoneNumber().toString());
//                    fireBaseHandler.addAccount(name, phoneNum, email, address);
//                    System.out.println("haha" + name + phoneNum + email + address);
//                    Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.putExtra("resMessage","Account created");
//                    intent.putExtra("name" ,name);
//                    intent.putExtra("phoneNum", phoneNum);
//                    intent.putExtra("email", email);
//                    intent.putExtra("address", address);
//                    setResult(RESULT_OK,intent);
//                    finish();
//                } else {
//                    System.out.println("abc" + email + password);
//                    System.out.println("Failll");
//                }
//            }
//        });
//    }
}