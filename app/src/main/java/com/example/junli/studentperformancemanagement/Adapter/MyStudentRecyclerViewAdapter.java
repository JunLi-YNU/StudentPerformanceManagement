package com.example.junli.studentperformancemanagement.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.junli.studentperformancemanagement.Fragment.StudentFragment.OnListFragmentInteractionListener;
import com.example.junli.studentperformancemanagement.R;
import com.example.junli.studentperformancemanagement.bean.Student;


import java.util.List;


public class MyStudentRecyclerViewAdapter extends RecyclerView.Adapter <MyStudentRecyclerViewAdapter.ViewHolder> {

    private final List <Student> StudentsList;
    private final OnListFragmentInteractionListener mListener;

    public MyStudentRecyclerViewAdapter(List <Student> items, OnListFragmentInteractionListener listener) {
        StudentsList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = StudentsList.get(position);
        holder.mIdView.setText(String.valueOf(StudentsList.get(position).get_id()));
        holder.mContentView.setText(StudentsList.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return StudentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Student mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
