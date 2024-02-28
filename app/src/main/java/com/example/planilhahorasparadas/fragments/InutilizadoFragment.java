package com.example.planilhahorasparadas.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.planilhahorasparadas.R;

public class InutilizadoFragment extends Fragment {

    private static final String ARG_PARAM1 = "Inutilizado1";
    private static final String ARG_PARAM2 = "Inutilizado2";

    private String mParam1;
    private String mParam2;
    private String textoRecebido;

    public InutilizadoFragment() {
    }

    public static InutilizadoFragment newInstance(String param1, String param2) {
        InutilizadoFragment fragment = new InutilizadoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            textoRecebido = mParam1 + " teste " + mParam2;
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inutilizado, container, false);


        TextView textView = root.findViewById(R.id.FragmentInutilizadoText);
        textView.setText(textoRecebido);

        return root;



    }
}