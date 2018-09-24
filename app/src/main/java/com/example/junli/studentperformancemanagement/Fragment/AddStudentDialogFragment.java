package com.example.junli.studentperformancemanagement.Fragment;



import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.example.junli.studentperformancemanagement.R;


public class AddStudentDialogFragment extends DialogFragment  {

    public static final String USERNAME = "userName";
    public static final String USERPASSWORD = "userPassword";
    private EditText mStudentId;
    private EditText mStudentName;
    private EditText mGrades;
    private EditText mMidGrades;
    private EditText mFinalGrades;
    private addStudentListener StudentListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StudentListener= (addStudentListener) getActivity();
    }

    private AddStudentDialogFragment fragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置背景透明
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_student_dialog, null);

        mStudentId= view.findViewById(R.id.add_student_id);
        mStudentName= view.findViewById(R.id.add_Student_name);
        mGrades = view.findViewById(R.id.G);
        mMidGrades = view.findViewById(R.id.MG);
        mFinalGrades = view.findViewById(R.id.FG);

        builder.setView(view);
        builder.setTitle("请填写学生信息");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String id = mStudentId.getText().toString().trim();
                String name = mStudentName.getText().toString().trim();
                String grades = mGrades.getText().toString().trim();
                String midgrades = mMidGrades.getText().toString().trim();
                String finalgrades = mFinalGrades.getText().toString().trim();

                StudentListener.sendMessage(id,name,grades,midgrades,finalgrades);
            }
        });
        builder.setNegativeButton("取消",null);
        return builder.create();
    }
    public interface addStudentListener{
        public abstract void sendMessage(String id,String name,String grades,String midgrades,String finalgrades);
    }



}
