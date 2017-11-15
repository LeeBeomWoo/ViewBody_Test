package com.example.leebeomwoo.viewbody_final.QnA;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leebeomwoo.viewbody_final.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Qna_ReplyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Qna_ReplyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    View view;
    private static RecyclerView rv;
    static ReplyRecyclerViewAdapter adapter;
    // TODO: Rename and change types of parameters
    String item;
    String reply;
    QrItem qrItem;
    TextView qnaContent, qnaTitle;
   List<QrpItem> qrpItems;

    Fragment list, write;

    // TODO: Rename and change types of parameters
    private String mParam1;


    public Qna_ReplyFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Qna_ReplyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Qna_ReplyFragment newInstance(String json) {
        Qna_ReplyFragment fragment = new Qna_ReplyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, json);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            JsonParser parser = new JsonParser();

            JsonElement element = parser.parse(mParam1);
Log.d("replelist", mParam1);
            JsonArray jArray = element.getAsJsonArray();
            qrpItems = new Gson().fromJson(jArray, new TypeToken<List<QrpItem>>(){}.getType());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentview = inflater.inflate(R.layout.fragment_read, container, false);
        view = inflater.inflate(R.layout.reply_list, container, false);
        rv = (RecyclerView) view.findViewById(R.id.reply_list);
        setHasOptionsMenu(true);
        rv.setHasFixedSize(true);
        getActivity().invalidateOptionsMenu();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llm);
        adapter = new ReplyRecyclerViewAdapter(getActivity(), qrpItems);
        rv.setAdapter(adapter);
        return view;
    }


}
