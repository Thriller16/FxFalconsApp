package app.fxfalcons.com;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import app.fxfalcons.com.falconforex.R;
import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

import static co.paystack.android.model.Card.CardType.MASTERCARD;
import static co.paystack.android.model.Card.CardType.VERVE;
import static co.paystack.android.model.Card.CardType.VISA;

public class CardDetailsActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ProgressDialog mProgressDialog;
    Card card;
    Charge charge;
    EditText cardNumberEdt, expMonthEdt, expYearEdt, cvvEdt;
    Button paySecurely;
    String nairaValue;
    String dollarValue;
    String reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);


        nairaValue = getIntent().getStringExtra("nairaValue");
        dollarValue = getIntent().getStringExtra("dollarValue");
//        Toast.makeText(CardDetailsActivity.this, "Proceed" + dollarValue, Toast.LENGTH_SHORT).show();


        mToolbar = findViewById(R.id.carddetailsbar);
        cardNumberEdt = findViewById(R.id.cardNumber);
        expMonthEdt = findViewById(R.id.expMonth);
        expYearEdt = findViewById(R.id.expYear);
        paySecurely = findViewById(R.id.paySecurely);
        cvvEdt = findViewById(R.id.cardCVV);

        mProgressDialog = new ProgressDialog(this, R.style.myAlertDialogStyle);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Card");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cardNumberEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                checkCardType(cardNumberEdt.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        paySecurely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProgressDialog.setTitle("Please wait");
                mProgressDialog.setMessage("Processing your payment");
                mProgressDialog.setCanceledOnTouchOutside(false);

                if(cardNumberEdt.getText().toString().equals("") || cardNumberEdt.getText().toString().equals(null)){
                    Toast.makeText(CardDetailsActivity.this, "Card number cannot be left blank", Toast.LENGTH_SHORT).show();
                }

                else if(expMonthEdt.getText().toString().equals("") || expMonthEdt.getText().toString().equals(null)){
                    Toast.makeText(CardDetailsActivity.this, "Expiry month cannot be left blank", Toast.LENGTH_SHORT).show();
                }
                else if(expYearEdt.getText().toString().equals("") || expYearEdt.getText().toString().equals(null)){
                    Toast.makeText(CardDetailsActivity.this, "Expiry year cannot be left blank", Toast.LENGTH_SHORT).show();
                }
                else if(cvvEdt.getText().toString().equals("") || cvvEdt.getText().toString().equals(null)){
                    Toast.makeText(CardDetailsActivity.this, "CVV pin cannot be left blank", Toast.LENGTH_SHORT).show();
                }
                else{
//                    chargeCard("Edung Divinefavour",
//                            cardNumberEdt.getText().toString(),
//                            Integer.parseInt(expMonthEdt.getText().toString()),
//                            Integer.parseInt(expYearEdt.getText().toString()),
//                            cvvEdt.getText().toString());
                }

                chargeCard("Edung Divinefavour",
                        "4084084084084081",
                       11,
                        19,
                       "404");
            }
        });


        paySecurely.setText("Securely pay $" + dollarValue);
    }

    private void chargeCard(String cardName, String cardnumber, int expMonth, int expYear, String cvv) {

        card = new Card(cardnumber, expMonth, expYear, cvv, cardName);
        card.setName(cardName);

        if (card.isValid()) {
            mProgressDialog.show();

            charge = new Charge();
            charge.setCard(card)
                    .setAmount(convertToKobo(nairaValue))
                    .setEmail("fxfalcons12@gmail.com")
                    .setCurrency("NGN")
                    .setReference(generateReference(dollarValue, "82397"));

            PaystackSdk.chargeCard(CardDetailsActivity.this, charge, new Paystack.TransactionCallback() {
                @Override
                public void onSuccess(Transaction transaction) {
                    Toast.makeText(CardDetailsActivity.this, "Success!!!" + charge.getAccessCode(), Toast.LENGTH_SHORT).show();
                    mProgressDialog.cancel();


                }

                @Override
                public void beforeValidate(Transaction transaction) {

                }

                @Override
                public void onError(Throwable error, Transaction transaction) {

                    Toast.makeText(CardDetailsActivity.this, "" + transaction.getReference(), Toast.LENGTH_SHORT).show();
                    mProgressDialog.cancel();
                }
            });
        } else {
            Toast.makeText(this, "Invalid card please check details and try again", Toast.LENGTH_SHORT).show();
        }
    }
//
//    private void checkCardType(String cardnumber){
//
//        if(cardnumber.startsWith("5")){
//            Toast.makeText(this, "Mastercard", Toast.LENGTH_SHORT).show();
//
//        }
//        else if(card.getType() == VERVE){
//            Toast.makeText(this, "Verve", Toast.LENGTH_SHORT).show();
//        }
//        else if(card.getType() == VISA){
//            Toast.makeText(this, "Visa", Toast.LENGTH_SHORT).show();
//        }
//    }

    private int convertToKobo(String nairaAmount) {
        int kobovalue = 0;
        kobovalue = Integer.parseInt(nairaAmount) * 100;
        return kobovalue;
    }

    private String generateReference(String dollaramount, String walletID) {
        String transactionReference = "";

        int min = 1000;
        int max = 9000;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;

        String partOne = String.valueOf(i1);
        String partTwo = "VAL";
        String partThree = dollaramount;
        String partFour = "ID";
        String partFive = walletID;

        transactionReference = partOne + partTwo + partThree + partFour + partFive;
        return transactionReference;

    }

    @Override
    public void onBackPressed() {
        if (mProgressDialog.isShowing()) {
            Toast.makeText(this, "gsdfgdfgdf", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }
}
