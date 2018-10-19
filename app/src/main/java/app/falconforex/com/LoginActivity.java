package app.falconforex.com;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import app.falconforex.com.falconforex.R;

public class LoginActivity extends AppCompatActivity {
    EditText passwordEdt, emailEdt;
    Button mSignInBtn;
    FirebaseAuth mFireAuth;
    ProgressDialog mProgressDialog;
    TextView gotoReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mProgressDialog = new ProgressDialog(this, R.style.myAlertDialogStyle);

        emailEdt = findViewById(R.id.email_login);
        passwordEdt = findViewById(R.id.passw_login);
        gotoReg = findViewById(R.id.go_to_reg);
        mSignInBtn = findViewById(R.id.login_btn);
        mFireAuth = FirebaseAuth.getInstance();

        emailEdt.setText("lawrenedickson49@gmail.com");
        passwordEdt.setText("stellaandme");

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailEdt.getText().toString() == null || emailEdt.getText().toString() == "" || !emailEdt.getText().toString().contains("@") || !emailEdt.getText().toString().contains(".com")) {
                    Toast.makeText(LoginActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                } else if (passwordEdt.getText().toString() == null || passwordEdt.getText().toString() == "" || passwordEdt.getText().toString().length() < 6) {
                    Toast.makeText(LoginActivity.this, "Please input password correctly", Toast.LENGTH_SHORT).show();
                } else {
                    signInUser(emailEdt.getText().toString(), passwordEdt.getText().toString());
                }
            }
        });

        gotoReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void signInUser(String email, String password) {
        mProgressDialog.setTitle("Please Wait");
        mProgressDialog.setMessage("Logging In");
        mProgressDialog.show();
        mProgressDialog.setCanceledOnTouchOutside(false);

        mFireAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mProgressDialog.dismiss();
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
