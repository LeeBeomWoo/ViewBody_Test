package com.example.leebeomwoo.viewbody_final.ItemGroup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leebeomwoo.viewbody_final.Item.WriterItem;
import com.example.leebeomwoo.viewbody_final.R;
import com.example.leebeomwoo.viewbody_final.Response.ResponseTr;
import com.example.leebeomwoo.viewbody_final.Support.ConAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainerInfoFragment extends Fragment {
    ResponseTr responseTr;
    private View view;
    String tr_id, section;
    List<WriterItem> licenses;
    List<WriterItem> awards;
    public TrainerInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tr_id = getArguments().getString("tr_id");
        section = getArguments().getString("section");
    }
//넘겨받은 작성자값을 인자(tr_id)로 조회하여 해당 트레이너의 정보를 출력
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_trainer_info, container, false);
        final RecyclerView rvli = (RecyclerView)view.findViewById(R.id.license_list);
        rvli.setHasFixedSize(true);
        LinearLayoutManager llmli = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvli.setLayoutManager(llmli);
        final RecyclerView rvaw = (RecyclerView)view.findViewById(R.id.award_list);
        rvaw.setHasFixedSize(true);
        LinearLayoutManager llmaw = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvaw.setLayoutManager(llmaw);
        Call<ResponseTr> call = ConAdapter.getInstance().TR_CALL(tr_id);
        call.enqueue(new Callback<ResponseTr>() {
            @Override
            public void onResponse(Call<ResponseTr> call, Response<ResponseTr> response) {
                responseTr = response.body();
                TextView asstxtB = (TextView) view.findViewById(R.id.trainer_association_txt);
                TextView nicktxtB = (TextView) view.findViewById(R.id.trainer_nicname_txt);
                TextView calltxtB = (TextView) view.findViewById(R.id.trainer_call_txt);
                TextView emailtxtB = (TextView) view.findViewById(R.id.trainer_email_txt);
                WebView faceWxb = (WebView) view.findViewById(R.id.trainer_facewebView);
                asstxtB.setText(responseTr.getAssociation());
                nicktxtB.setText(responseTr.getNickName());
                calltxtB.setText(responseTr.getCall());
                emailtxtB.setText(responseTr.getEmail());
                faceWxb.loadUrl(responseTr.getPhotoUrl());
                faceWxb.setFocusable(false);
                licenses = responseTr.getLicense();
                Trainer_license_Adapter adapterli = new Trainer_license_Adapter(getActivity(), licenses);
                rvli.setAdapter(adapterli);
                awards = responseTr.getAward();
                Trainer_award_Adapter adapteraw = new Trainer_award_Adapter(getActivity(), awards);
                rvaw.setAdapter(adapteraw);
            }

            @Override
            public void onFailure(Call<ResponseTr> call, Throwable t) {
                Toast toast = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    toast = Toast.makeText(getContext(),"서버와의 연결이 안됬습니다.", Toast.LENGTH_SHORT);
                }
                toast.show();
            }
        });
        return view;
    }

}
