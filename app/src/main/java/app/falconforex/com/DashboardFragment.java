package app.falconforex.com;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.falconforex.com.falconforex.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {


    DatabaseReference mUserDatabase;
    FirebaseUser mCurrentUser;
    FirebaseAuth mFireAuth;
    View mView;
    TextView mWalletIdTv;
    TextView mWalletBalanceTv;
    String walletBalance;
    String walletId;

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
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Members").child(mCurrentUser.getUid());


        loadUserAccounts();




        return mView;
    }

    private void loadUserAccounts() {
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mWalletBalanceTv.setText("$"+dataSnapshot.child("walletbalance").getValue().toString());
                mWalletIdTv.setText("#"+dataSnapshot.child("walletId").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setUpViews(){
        mWalletIdTv = mView.findViewById(R.id.wallet_id);
        mWalletBalanceTv = mView.findViewById(R.id.wallet_balance);
    }
}
