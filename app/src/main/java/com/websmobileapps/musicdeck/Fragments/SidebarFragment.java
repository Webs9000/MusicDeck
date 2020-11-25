package com.websmobileapps.musicdeck.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.websmobileapps.musicdeck.Model.DatabaseUser;
import com.websmobileapps.musicdeck.R;
import com.websmobileapps.musicdeck.Repository.Repo;
import com.websmobileapps.musicdeck.ViewModels.AuthViewModel;

import java.util.Objects;

public class SidebarFragment extends Fragment {

    private View mSidebarFragment;
    private Button mQRCodeBTN, mProfileBTN, mSignOutBTN, mDelAccBTN, mHomeBTN;
    private TextView mCurrentUserTV;

    private AuthViewModel mAuthViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuthViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        mAuthViewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSidebarFragment = inflater.inflate(R.layout.sidebar_view, container, false);

        mCurrentUserTV = mSidebarFragment.findViewById(R.id.SB_current_user);
        final String userUID = Objects.requireNonNull(mAuthViewModel.getUserMutableLiveData().getValue()).getUid();
        Repo.getInstance().getUserRef(userUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseUser user = snapshot.getValue(DatabaseUser.class);
                String username = Objects.requireNonNull(user).getUsername();
                mCurrentUserTV.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mHomeBTN = mSidebarFragment.findViewById(R.id.home_button);
        mHomeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigate(R.id.action_sidebarFragment_to_homeViewFragment);
            }
        });

        mProfileBTN = mSidebarFragment.findViewById(R.id.profile_button);
        mProfileBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigate(R.id.action_sidebarFragment_to_profileViewFragment);
            }
        });


        mQRCodeBTN = mSidebarFragment.findViewById(R.id.qrCodeScannerButton);
        mQRCodeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigate(R.id.action_sidebarFragment_to_qrCodeScannerFragment);
            }
        });

        mSignOutBTN = mSidebarFragment.findViewById(R.id.sign_out_button);
        mSignOutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuthViewModel.logout();
                Navigation.findNavController(requireView()).navigate(R.id.action_sidebarFragment_to_loginFragment);
            }
        });

        mDelAccBTN = mSidebarFragment.findViewById(R.id.delete_button);
        mDelAccBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuthViewModel.deleteUser();
                Navigation.findNavController(requireView()).navigate(R.id.action_sidebarFragment_to_signUpFragment);
            }
        });

        return mSidebarFragment;
    }
}
