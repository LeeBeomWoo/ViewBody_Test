package com.example.leebeomwoo.viewbody_final.QnA;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.leebeomwoo.viewbody_final.R;

/**
 * Created by root on 16. 9. 27.
 */

public class Qna_Reple_write extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    View view;
    private String mParam1;
    public EditText name, content;


    public Qna_Reple_write() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Qna_ReplyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Qna_Reple_write newInstance(String pagenum) {
        Qna_Reple_write fragment = new Qna_Reple_write();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, pagenum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentview = inflater.inflate(R.layout.fragment_read, container, false);
        view = inflater.inflate(R.layout.replewrite, container, false);
        name = (EditText) view.findViewById(R.id.qna_reple_writeName);
        content = (EditText) view.findViewById(R.id.qna_reple_writeContent);
        return view;
    }


}
