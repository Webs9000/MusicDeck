package com.websmobileapps.musicdeck.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.websmobileapps.musicdeck.R;

public class SidebarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sidebar_view, container, false);
        Button qrCodeButton = view.findViewById(R.id.qrCodeScannerButton);

        qrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigate(R.id.action_sidebarFragment_to_qrCodeScannerFragment);
            }
        });

        return view;
    }
}
