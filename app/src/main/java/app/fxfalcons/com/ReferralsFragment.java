package app.fxfalcons.com;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.fxfalcons.com.falconforex.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReferralsFragment extends Fragment {


    public ReferralsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_referrals, container, false);
    }

}
