package app.falconforex.com.falconforex;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {

    TextInputLayout firstNameEdt, lastNameEdt, passwordEdt;
    CheckBox agreeBox;
    TextView goOnText, signUptext;
    LinearLayout namesLayout;
    Button getStartedBtn;
    FirebaseAuth mFireAuth;
    DatabaseReference mUserDatabase;
    ProgressDialog mProgressDialog;

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
                    registerUser("a@gmail.com", "aaaaaaaa", "", "");
                }
            }
        });
    }

    private void registerUser(String email, String password, String firstname, String lastname) {
        final HashMap<Object, String> userMap = new HashMap<>();


        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setMessage("Your account is being created");
        mProgressDialog.show();

        mFireAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Your account has been created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    mProgressDialog.dismiss();
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
}
