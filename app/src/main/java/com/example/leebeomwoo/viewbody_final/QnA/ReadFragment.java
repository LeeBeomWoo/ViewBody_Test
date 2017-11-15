package com.example.leebeomwoo.viewbody_final.QnA;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.example.leebeomwoo.viewbody_final.R;
import com.example.leebeomwoo.viewbody_final.Response.ResponseQrp;
import com.example.leebeomwoo.viewbody_final.Support.ConAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
@SuppressLint("ValidFragment")
public class ReadFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    View view;
    // TODO: Rename and change types of parameters
    String item;
    String reply, qrpnam, qrpcontent;
    QrItem qrItem;
    TextView qnaContent, qnaTitle;
    List<QrpItem> qrpItems;
    FloatingActionButton add, update, cancel;
    Fragment list, write;
    ResponseQrp responseQrp;
    private Boolean isFabOpen = false;
    private Animation upfab_open, upfab_close, lofab_open, lofab_close, rotate_forward, rotate_backward;

    public ReadFragment(String replycode) {
        // Required empty public constructor
        reply = replycode;

    }

    public ReadFragment(){}
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ReadFragment.
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("reField padfragment", "createview run");
        view = inflater.inflate(R.layout.fragment_read, container, false);
        qnaTitle = (TextView) view.findViewById(R.id.qna_read_title);
        qnaContent = (TextView) view.findViewById(R.id.qna_read_text);

        upfab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.upfab_open);
        upfab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.upfab_close);
        lofab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.lofab_open);
        lofab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.lofab_close);
        rotate_forward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_backward);

        add = (FloatingActionButton) view.findViewById(R.id.reply_add_btn);
        update = (FloatingActionButton) view.findViewById(R.id.reply_update_btn);
        cancel = (FloatingActionButton) view.findViewById(R.id.reply_cancel_btn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
                write = Qna_Reple_write.newInstance(reply);
                getChildFragmentManager().beginTransaction().replace(R.id.reply_container, write).commit();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
                EditText name = (EditText) view.findViewById(R.id.qna_reple_writeName);
                EditText content = (EditText) view.findViewById(R.id.qna_reple_writeContent);
                qrpnam = name.getText().toString(); qrpcontent = content.getText().toString();
                Log.d("updatereple", qrpnam + qrpcontent);
                Call<ResponseQrp> call = ConAdapter.getInstance().QrpPut(qrpcontent, qrpnam, reply);
                call.enqueue(new Callback<ResponseQrp>() {
                    @Override
                    public void onResponse(Call<ResponseQrp> call, Response<ResponseQrp> response) {
                        responseQrp = response.body();
                        Log.d("update_replelist", responseQrp.toString());
                        qrpItems = responseQrp.getQrp_Item();
                        JsonArray myjsonarray = new Gson().toJsonTree(qrpItems).getAsJsonArray();
                        String pass = myjsonarray.toString();
                        list = Qna_ReplyFragment.newInstance(pass);
                        getChildFragmentManager().beginTransaction().replace(R.id.reply_container, list).commit();
                    }

                    @Override
                    public void onFailure(Call<ResponseQrp> call, Throwable t) {

                    }
                });
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
                getChildFragmentManager().beginTransaction().replace(R.id.reply_container, list).commit();
            }
        });
        Log.d("readFragment_put", reply);
        Call<QrItem> call = ConAdapter.getInstance().QrPost(reply);
        call.enqueue(new Callback<QrItem>() {
            @Override
            public void onResponse(Call<QrItem> call, Response<QrItem> response) {
                Log.d("response readfragment", response.body().toString());
                qrItem = response.body();
                Log.d("Q_item_title", qrItem.getQ_Title());
                qnaTitle.setText(qrItem.getQ_Title());
                Log.d("Q_item_content", qrItem.getQ_Content());
                qnaContent.setText(qrItem.getQ_Content());
                Log.d("Qrp_item", qrItem.getQrp_Item().toString());
                qrpItems = qrItem.getQrp_Item();
                JsonArray myjsonarray = new Gson().toJsonTree(qrpItems).getAsJsonArray();
                String pass = myjsonarray.toString();
                Log.d("jsonArr", pass);
                list = Qna_ReplyFragment.newInstance(pass);
                getChildFragmentManager().beginTransaction().add(R.id.reply_container, list).addToBackStack(pass).commit();
            }
            @Override
            public void onFailure(Call<QrItem> call, Throwable t) {
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void animateFAB() {

        if (isFabOpen) {
            add.startAnimation(rotate_backward);
            update.startAnimation(upfab_close);
            cancel.startAnimation(lofab_close);
            update.setClickable(false);
            cancel.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");
        } else {
            add.startAnimation(rotate_forward);
            update.startAnimation(upfab_open);
            cancel.startAnimation(lofab_open);
            update.setClickable(true);
            cancel.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");
        }
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
