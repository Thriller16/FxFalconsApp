package app.fxfalcons.com;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

import app.fxfalcons.com.falconforex.R;

public class FundWalletActivity extends AppCompatActivity {

    CheckBox proceedCheck;
    EditText fundAmountEdt;
    Toolbar mToolbar;
    Button confirmPaymentBtn;
    TextView confirmationTextView;
    String walletID;
    DatabaseAccess databaseAccess;
    Snackbar snackbar;
    EditText editText;
    RelativeLayout relativeLayout;
    String dollarRate = "";
    String nairaValue;
    String dollarValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_wallet);

        walletID = getIntent().getStringExtra("walletID");

        proceedCheck = findViewById(R.id.proceedCheck);
        fundAmountEdt = findViewById(R.id.fundAmount);
        confirmPaymentBtn = findViewById(R.id.confirm_purchase);
        confirmationTextView = findViewById(R.id.confirmationtv);
        relativeLayout = findViewById(R.id.fundaccountmain);

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();


        confirmationTextView.setText("A total of NGN 0 will be deducted from your bank account " +
                "and 0 USD will be credited to your FxFalcons wallet with ID " + walletID + ". This amount includes all transaction subcharges. do you wish to proceed?");

        mToolbar = findViewById(R.id.fund_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Fund Wallet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        StringTokenizer stringTokenizer = new StringTokenizer(databaseAccess.getExchangeRate(), ".");
        dollarRate = stringTokenizer.nextToken();
//        Toast.makeText(this, "" + dollarRate, Toast.LENGTH_SHORT).show();


        fundAmountEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(fundAmountEdt.getText().toString().startsWith("0")){
                    Toast.makeText(FundWalletActivity.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                }

                else{
                    if (fundAmountEdt.getText().toString().equals("") || fundAmountEdt.getText().toString().equals(null)) {
                        confirmationTextView.setText("A total of NGN " + "0" + "  will be deducted from your bank account and 0 USD will be credited to your FxFalcons wallet with ID " + walletID + ". This amount includes all transaction subcharges. do you wish to proceed?");
                    }

                    else{
                        int amountInUSDollars = Integer.parseInt(fundAmountEdt.getText().toString());

                        int amountInNaira = amountInUSDollars * (Integer.parseInt(dollarRate)+1);

                        nairaValue = String.valueOf(amountInNaira);
                        dollarValue = String.valueOf(amountInUSDollars);

                        confirmationTextView.setText("A total of NGN " + parseAmount(amountInNaira) + "  will be deducted from your bank account and " + amountInUSDollars +" USD will be credited to your FxFalcons wallet with ID " + walletID + ". This amount includes all transaction subcharges. do you wish to proceed?");
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        confirmPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(proceedCheck.isChecked()){

                    if(fundAmountEdt.getText().toString().equals("") || fundAmountEdt.getText().toString().equals("0")){
                        Toast.makeText(FundWalletActivity.this, "Invalid fund amount", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        if (Integer.parseInt(fundAmountEdt.getText().toString()) < 30){
                            snackbar = Snackbar.make(relativeLayout, "Sorry, we currently do not allow payments smaller than 30USD", Snackbar.LENGTH_LONG).setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    snackbar.dismiss();
                                }
                            });
                            snackbar.setActionTextColor(getResources().getColor(R.color.bgcolor));
                            snackbar.show();
                        }


                        else if(Integer.parseInt(fundAmountEdt.getText().toString()) >= 30){
                            startActivity(new Intent(FundWalletActivity.this, CardDetailsActivity.class).
                                    putExtra("nairaValue", nairaValue).
                                    putExtra("dollarValue", dollarValue));
                        }
                    }
                }

                else{

                    if(fundAmountEdt.getText().toString().equals("") || fundAmountEdt.getText().toString().equals("0")){
                        Toast.makeText(FundWalletActivity.this, "Invalid fund amount", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        snackbar = Snackbar.make(relativeLayout, "Please check the box above to confirm that you have read the text above", Snackbar.LENGTH_LONG).setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                            }
                        });
                        snackbar.setActionTextColor(getResources().getColor(R.color.bgcolor));
                        snackbar.show();
                    }

                }
            }
        });
    }

    private String parseAmount(int amount) {

        String finalString = "";

        String amountToString = String.valueOf(amount);
        if (amountToString.length() > 3 && amountToString.length() < 7) {
//            String lastThree = amountToString.substring(amountToString.length() - 3);
//
//            finalString = amountToString.replace(lastThree, "," + lastThree);
//
            StringBuilder stringBuilder = new StringBuilder(amountToString);
            stringBuilder.insert(amountToString.length()-3, ",");

            finalString = stringBuilder.toString();
//            Toast.makeText(this, ""  + finalString, Toast.LENGTH_SHORT).show();

        } else if(amountToString.length() >= 7 && amountToString.length() < 10) {

            StringBuilder stringBuilder = new StringBuilder(amountToString);

            stringBuilder.insert(amountToString.length()-3, ",");
            stringBuilder.insert(amountToString.length()-6, ",");

            finalString = stringBuilder.toString();
//            Toast.makeText(this, "Not yet", Toast.LENGTH_SHORT).show();
        }else if(amountToString.length() >= 10 && amountToString.length() < 12){
            StringBuilder stringBuilder = new StringBuilder(amountToString);

            stringBuilder.insert(amountToString.length()-3, ",");
            stringBuilder.insert(amountToString.length()-6, ",");
            stringBuilder.insert(amountToString.length()-9, ",");

            finalString = stringBuilder.toString();
        }
        else{
            finalString = amountToString;
        }

        return finalString;
    }
}
