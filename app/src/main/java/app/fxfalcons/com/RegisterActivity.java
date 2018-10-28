package app.fxfalcons.com;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

import app.fxfalcons.com.falconforex.R;


public class RegisterActivity extends AppCompatActivity {

    TextInputLayout firstNameEdt, lastNameEdt, passwordEdt, emailEdt;
    CheckBox agreeBox;
    TextView goOnText, signUptext;
    LinearLayout namesLayout;
    Button getStartedBtn;
    FirebaseAuth mFireAuth;
    DatabaseReference mUserDatabase;
    ProgressDialog mProgressDialog;
    FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFireAuth = FirebaseAuth.getInstance();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Members");

        mProgressDialog = new ProgressDialog(this, R.style.myAlertDialogStyle);
        setUpViews();
        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(namesLayout.getVisibility() == View.GONE){
                    changeVisibilities();
                }

                else{

                    if(firstNameEdt.getEditText().getText().toString() == null || firstNameEdt.getEditText().getText().toString() == "" || firstNameEdt.getEditText().getText().toString().length() < 3){
                        Toast.makeText(RegisterActivity.this, "Please input firstname longer than 3 characters", Toast.LENGTH_SHORT).show();
                    }

                    if(lastNameEdt.getEditText().getText().toString() == null || lastNameEdt.getEditText().getText().toString() == "" || lastNameEdt.getEditText().getText().toString().length() < 3){
                        Toast.makeText(RegisterActivity.this, "Please input firstname longer than 3 characters", Toast.LENGTH_SHORT).show();
                    }

                    if(emailEdt.getEditText().getText().toString() == null || emailEdt.getEditText().getText().toString() == "" || !emailEdt.getEditText().getText().toString().contains("@") || !emailEdt.getEditText().getText().toString().contains(".com")){
                        Toast.makeText(RegisterActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    }
                    else if(passwordEdt.getEditText().getText().toString() == null || passwordEdt.getEditText().getText().toString() == "" || passwordEdt.getEditText().getText().toString().length() < 6){
                        Toast.makeText(RegisterActivity.this, "Please enter a password longer than 6 characters", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        registerUser(emailEdt.getEditText().getText().toString(), passwordEdt.getEditText().getText().toString(), firstNameEdt.getEditText().getText().toString(), lastNameEdt.getEditText().getText().toString());
                    }
                }
            }
        });
    }


    private void registerUser(String email, String password, String firstname, String lastname) {

        final HashMap<Object, String> userMap = new HashMap<>();
        userMap.put("firstname", firstname);
        userMap.put("lastname", lastname);
        userMap.put("walletId", generateWalletId());
        userMap.put("walletbalance", "0.00");
        userMap.put("phonenumber", "");
        userMap.put("country", "unknown");
        userMap.put("isVerified", "no");
        userMap.put("accountname", firstname + " " + lastname);
        userMap.put("bankname", "none");
        userMap.put("accountType", "none");
        userMap.put("isVerified", "no");
        userMap.put("isBeginner", "yes");
        userMap.put("isIntermediate", "no");
        userMap.put("isExpert", "no");
        userMap.put("noOfReferrals", "0");
        userMap.put("transactions", "0");


        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setMessage("Your account is being created");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        mFireAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    mCurrentUser = mFireAuth.getCurrentUser();
                    mUserDatabase.child(mCurrentUser.getUid()).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                mFireAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegisterActivity.this, "Your account has been created", Toast.LENGTH_SHORT).show();
                                            mProgressDialog.dismiss();
                                            Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(mainIntent);
                                            finish();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
                else{
                    mProgressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Sorry we couldnt create your account at this time please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setUpViews(){
        firstNameEdt = findViewById(R.id.firstNameReg);
        lastNameEdt = findViewById(R.id.lastNameReg);
        signUptext = findViewById(R.id.signUpText);
        emailEdt = findViewById(R.id.email_register);
        passwordEdt = findViewById(R.id.passwordReg);
        agreeBox = findViewById(R.id.agreeCheck);
        goOnText = findViewById(R.id.go_on);
        namesLayout = findViewById(R.id.nameslayout);
        getStartedBtn = findViewById(R.id.getStartedBtn);
    }

    public void changeVisibilities(){
        namesLayout.setVisibility(View.VISIBLE);
        passwordEdt.setVisibility(View.VISIBLE);
        agreeBox.setVisibility(View.VISIBLE);
        goOnText.setVisibility(View.VISIBLE);
    }

    public String generateWalletId() {
        int min = 100193;
        int max = 992838;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;

        return String.valueOf(i1);
    }

}
