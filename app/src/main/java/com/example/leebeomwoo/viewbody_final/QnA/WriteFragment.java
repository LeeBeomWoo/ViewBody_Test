package com.example.leebeomwoo.viewbody_final.QnA;

import android.content.Context;
import android.content.Intent;
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


import com.example.leebeomwoo.viewbody_final.MainActivity;
import com.example.leebeomwoo.viewbody_final.R;
import com.example.leebeomwoo.viewbody_final.Support.ConAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class WriteFragment extends Fragment {

    View view;
    String upload_text;
    String writer_text;
    String title_text;
    String password_text;
    int fragment;
    Fragment read;
    FloatingActionButton main, add, camera, cancel, upload;
    EditText title, writer, content, password;
    private Boolean isFabOpen = false;
    Animation add_open, add_close, rotate_backward, rotate_forward;
    QwItem qwItem;

    private OnFragmentInteractionListener mListener;

    public WriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WriteFragment.
     */
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_write, container, false);
        content = (EditText)view.findViewById(R.id.qna_write_txtB);
        title = (EditText)view.findViewById(R.id.qna_write_title);
        writer = (EditText)view.findViewById(R.id.qna_write_writer);
        // password = (EditText) view.findViewById(R.id.qna_write_password);
        main = (FloatingActionButton)view.findViewById(R.id.qna_write_add);
        upload = (FloatingActionButton)view.findViewById(R.id.qna_write_upload);
        add = (FloatingActionButton)view.findViewById(R.id.qna_write_main);
        camera = (FloatingActionButton)view.findViewById(R.id.qna_write_camera);
        cancel = (FloatingActionButton)view.findViewById(R.id.qna_write_cancel);
        main.setFocusableInTouchMode(true);
        upload.setFocusableInTouchMode(true);
       // add.setFocusableInTouchMode(true);
        // camera.setFocusableInTouchMode(true);
        cancel.setFocusableInTouchMode(true);

        add_open = AnimationUtils.loadAnimation(getActivity(), R.anim.add_open);
        add_close = AnimationUtils.loadAnimation(getActivity(), R.anim.add_close);
        rotate_forward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_backward);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });
        //작성한 질문 내용을 서버로 업로드
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    upload_text = content.getText().toString();
                    writer_text = writer.getText().toString();
                    title_text = title.getText().toString();
               /**  try {
                    password_text = URLEncoder.encode(password.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                **/
                //데이터 서버에 보낸 후 다시 묻고 답하는 리스트를 호출
               // Call<QwItem> call = ConAdapter.getInstance().QwPut(upload_text, writer_text, title_text, password_text, "bcnsbeta");
                Call<QwItem> call = ConAdapter.getInstance().QwPut(upload_text, writer_text, title_text, "bcnsbeta");
                call.enqueue(new Callback<QwItem>() {
                    @Override
                    public void onResponse(Call<QwItem> call, Response<QwItem> response) {
                        Log.d("response readfragment", response.body().toString());
                        qwItem = response.body();
                        Log.d("getResult", qwItem.getResult());
                        Log.d("getPagenum", qwItem.getPagenum());
                        mListener.onFragmentInteraction(qwItem.getPagenum());
                    }

                    @Override
                    public void onFailure(Call<QwItem> call, Throwable t) {

                    }
                });
                // Get the transition name from the string

            }
        });
        //사진이나 이미지파일등을 추가하는 경우
        /**add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });
         ***/
        //작성을 취소하는 경우
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                fragment = 4;
                intent.putExtra("fragment", fragment);
                startActivity(intent);
            }
        });
        //필요한 사진을 촬영하기 위하여 카메라 모듈을 이용하여 사진 촬영
      /**  camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });

       **/
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String uri);
    }

    public void animateFAB() {

        if (isFabOpen) {

            main.startAnimation(rotate_backward);
            upload.startAnimation(add_close);
           // add.startAnimation(add_close);
           // camera.startAnimation(add_close);
            cancel.startAnimation(add_close);
            upload.setClickable(false);
           // add.setClickable(false);
           // camera.setClickable(false);
          //  cancel.setClickable(false);

            isFabOpen = false;
            Log.d("Raj", "close");
        } else {
            main.startAnimation(rotate_backward);
            upload.startAnimation(add_open);
         //   add.startAnimation(add_open);
          //  camera.startAnimation(add_open);
            cancel.startAnimation(add_open);
            upload.setClickable(true);
          //  add.setClickable(true);
          //  camera.setClickable(true);
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
