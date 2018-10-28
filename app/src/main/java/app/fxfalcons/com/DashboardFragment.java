package app.fxfalcons.com;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cz.msebera.android.httpclient.Header;

import com.flutterwave.raveandroid.RavePayManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import app.fxfalcons.com.falconforex.R;
import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;


public class DashboardFragment extends Fragment {

    DatabaseReference mUserDatabase;
    FirebaseUser mCurrentUser;
    FirebaseAuth mFireAuth;
    View mView;
    TextView mWalletIdTv;
    EditText editText;
    TextView mWalletBalanceTv;
    RelativeLayout relativeLayout;
    Button withdrawFundsBtn;
    RavePayManager ravePayManager;
    DatabaseAccess databaseAccess;
    Card card;
    Charge charge;
    FloatingActionButton fundAccountFab;
    String walletID;
    private static int count = 1;
    private static double num = 0, temp = 0;
    String dollarRate;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        setUpViews();
        mFireAuth = FirebaseAuth.getInstance();
        mCurrentUser = mFireAuth.getCurrentUser();
        databaseAccess = DatabaseAccess.getInstance(getContext());
        databaseAccess.open();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Members").child(mCurrentUser.getUid());

        PaystackSdk.initialize(getContext());
        loadUserAccounts();

//        Testing ravepay
//        ravePayManager = new RavePayManager(getActivity());
//
//        ravePayManager.setAmount(300).
//                setCountry("NIGERIA").setCurrency("NGN").
//                setEmail("lawrenedickson49@gmail.com")
//                .setfName("Edung").setlName("Divinefavour").
//                setNarration("Tester").setPublicKey("FLWPUBK-cb9276725a993492849c03b0059d4125-X")
//                .setSecretKey("FLWSECK-91d44ff52c0fac87681199041ba63e29-X")
//                .setTxRef("Ref").acceptAccountPayments(true).
//                acceptCardPayments(true).
////                withTheme().
//                allowSaveCardFeature(true).initialize();

//        Testing paystack
//        String cardNumber = "4084084084084081";
//        String cardName = "Edung Divinefavour";
//        int expiryMonth = 11;
//        int expiryYear = 19;
//        String cvv = "408";
//
//        card = new Card(cardNumber, expiryMonth, expiryYear, cvv, cardName);
//        card.setName("Edung Divinefavour");
//
//        if (card.isValid()) {
////            chargeCard();
////            Toast.makeText(getContext(), "" + card.getType(), Toast.LENGTH_SHORT).show();
//        } else {
////            Toast.makeText(getContext(), "Card not valid", Toast.LENGTH_SHORT).show();
//        }

        withdrawFundsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mWalletBalanceTv.setText("$" + addRandomNumber());
            }
        });

        fundAccountFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), FundWalletActivity.class).putExtra("walletID", walletID));
            }
        });

        getExchangeRate();
//        verifyTransaction("REF");

        return mView;
    }

    private String addRandomNumber() {
        if (count == 1) {
            try {
                num = Double.parseDouble(editText.getText().toString());    //Reading Decimal Number from EditText
                temp = 0;
            } catch (Exception e) {
                return "Invalid Input";
            }
            editText.setEnabled(false);
        }

        if (count < 30) {
//            Store num and temp and count

            double temp2 = Math.random();
            while (true) {
                if (temp + temp2 + num * 0.01 < num * (0.01 * count + 0.1)) {
                    temp = temp + num * 0.01 + temp2;
                    break;
                } else
                    temp2 = temp2 / 100;

            }
            count++;
            return String.valueOf(Math.round((temp + num) * 100) / 100D);


        } else if (count == 30) {
            count++;
            return String.valueOf(num + num * 0.4);
        } else {
            count = 1;
            editText.setEnabled(true);
            editText.setText("");
            return "Output";
        }
    }

    private void chargeCard() {
        charge = new Charge();

        charge.setCard(card)
                .setAmount(400000)
                .setEmail("fxfalcons12@gmail.com")
                .setCurrency("NGN").setReference("djhfkjsdfFER");

        PaystackSdk.chargeCard(getActivity(), charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                Toast.makeText(getContext(), "Success!!!" + charge.getAccessCode(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void beforeValidate(Transaction transaction) {

            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                Toast.makeText(getContext(), "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUserAccounts() {
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    walletID = dataSnapshot.child("walletId").getValue().toString();
                    mWalletBalanceTv.setText("$" + dataSnapshot.child("walletbalance").getValue().toString());
                    mWalletIdTv.setText("#" + walletID);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setUpViews() {
        relativeLayout = mView.findViewById(R.id.relative_layout);
        editText = mView.findViewById(R.id.edittext);
        withdrawFundsBtn = mView.findViewById(R.id.withdrawfundsBtn);
        fundAccountFab = mView.findViewById(R.id.fundAccount);
        mWalletIdTv = mView.findViewById(R.id.wallet_id);
        mWalletBalanceTv = mView.findViewById(R.id.wallet_balance);
    }

    public void getExchangeRate() {
        String url = "http://free.currencyconverterapi.com/api/v5/convert?q=USD_NGN&compact=y";
        final AsyncHttpClient client = new AsyncHttpClient();


        client.get(getContext(), url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject priceObject = new JSONObject(responseString);
                    JSONObject usdObj = priceObject.getJSONObject("USD_NGN");
                    dollarRate = usdObj.getString("val");

//                    Toast.makeText(getContext(), "" + dollarRate.toString(), Toast.LENGTH_SHORT).show();
                    databaseAccess.storeExchangeRate(dollarRate);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void verifyTransaction(String reference) {
        String url = "http://api.paystack.co/transaction/verify/" + reference;
        final AsyncHttpClient client = new AsyncHttpClient();

        client.addHeader("Authorization", "Bearer sk_test_ec7649d186d2cdd7f83215326c98809da3b98eb5");

        client.get(getContext(), url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("TAG", responseString);
                Toast.makeText(getContext(), "" + responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                try {
//                    JSONObject priceObject = new JSONObject(responseString);
//                    JSONObject usdObj = priceObject.getJSONObject("USD_NGN");
//                    dollarRate = usdObj.getString("val");
//
////                    Toast.makeText(getContext(), "" + dollarRate.toString(), Toast.LENGTH_SHORT).show();
//                    databaseAccess.storeExchangeRate(dollarRate);
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                Toast.makeText(getContext(), "" + responseString, Toast.LENGTH_SHORT).show();
                Log.i("TAG", responseString);
            }
        });

    }

//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null){
//            String message = data.getStringExtra("response");
//
//            if(resultCode == RavePayActivity.RESULT_SUCCESS){
//                Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
//            }
//            else if(resultCode == RavePayActivity.RESULT_ERROR){
//                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
//            }
//            else if(resultCode == RavePayActivity.RESULT_CANCELLED){
//                Toast.makeText(getContext(), "CANCELLED", Toast.LENGTH_SHORT).show();
//            }
//        }
//        else{
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
}
