package com.example.mrc.attendencesystem.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrc.attendencesystem.R;

/**
 * Created by Mr.C on 2018/5/7.
 */

public class ContactsFragment extends Fragment {
    public static ContactsFragment newInstance(String param1) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ContactsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        Bundle bundle = getArguments();
        return view;
    }
}
