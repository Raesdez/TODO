package com.android.example.todo.ui;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.example.todo.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaskFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {

    //Attributes that come with the Fragment class
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mFragmentType; //Two types: "pending" or "completed"
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //My Attributes
    private View fragmentView;
    private Task tasks;

    private Context mContext;




    public TaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskFragment.
     */
    public static TaskFragment newInstance(String param1, String param2) {
        TaskFragment fragment = new TaskFragment();
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
            mFragmentType = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_task, container, false);

        tasks = new Task();
        //TODO delete later, create sample data
        createSampleData();


        setRecycler();



        // Inflate the layout for this fragment
        return fragmentView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            mContext= context;
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

    //TODO: This will refresh the Fragment
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            setRecycler();
        }
    }

    /**
     * In orden to refresh the data when a task is created or updated
     */
    @Override
    public void onResume() {

        super.onResume();
        setRecycler();
    }

    private void setRecycler() {
        if (fragmentView != null) {
            //TODO delete this
            List<Task> lista = null;

            if (mFragmentType.equals("pending")) {
                //Retrieve pending task list
                lista = tasks.getSpecificTasks("PENDIENTE");
            } else if (mFragmentType.equals("completed")) {
                //Retrieve completed talk list
                lista = tasks.getSpecificTasks("COMPLETADO");
            }


            //RECYCLERVIEW
            RecyclerView rv = (RecyclerView) fragmentView.findViewById(R.id.recycler_view_p);
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));


            //ADAPTER
            TaskAdapter adapter = new TaskAdapter(mContext,mFragmentType, lista);
            rv.setAdapter(adapter);
        } else
            Log.d("TaskFragment", "The fragment view doesn't exists");
    }

    private void createSampleData(){

        //Create 2 pendings
        if(mFragmentType.equals("pending")) {
            Task queryTask = new Task();
            queryTask.setId(0);
            queryTask.setStatus("PENDIENTE");
            queryTask.setTask("Cambiar Aceite");

            queryTask.setCreates(new java.util.Date());
            queryTask.setDueTo(new java.util.Date());

            tasks.createAnUpdateNewTask(queryTask);
        }

        else {
            Task queryTask2 = new Task();
            queryTask2.setId(0);
            queryTask2.setStatus("COMPLETADO");
            queryTask2.setTask("Tomar la pastilla");

            queryTask2.setCreates(new java.util.Date());
            queryTask2.setDueTo(new java.util.Date());

            tasks.createAnUpdateNewTask(queryTask2);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
