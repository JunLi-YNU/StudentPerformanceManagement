package com.example.junli.studentperformancemanagement;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.junli.studentperformancemanagement.Fragment.AddStudentDialogFragment;
import com.example.junli.studentperformancemanagement.Fragment.GradeFragment;
import com.example.junli.studentperformancemanagement.Fragment.SettingFragment;
import com.example.junli.studentperformancemanagement.Fragment.StudentFragment;
import com.example.junli.studentperformancemanagement.bean.Student;
import com.example.junli.studentperformancemanagement.utils.Constant;
import com.example.junli.studentperformancemanagement.utils.DbManger;
import com.example.junli.studentperformancemanagement.utils.MySqliteHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.junli.studentperformancemanagement.utils.DbManger.*;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationBar.OnTabSelectedListener
    ,StudentFragment.OnListFragmentInteractionListener
     ,AddStudentDialogFragment.addStudentListener
        ,GradeFragment.OnListFragmentInteractionListener
,SettingFragment.OnFragmentInteractionListener{

    private MySqliteHelper helper;
    private BottomNavigationBar bottomNavigationBar;
    public static final List<Student> StudentList = new ArrayList <>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //yin
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        helper = getIntance(this);
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar
                .setBarBackgroundColor("#030303")
                .addItem(new BottomNavigationItem(R.drawable.ic_school_black_24dp,"教学班"))
                .addItem(new BottomNavigationItem(R.drawable.ic_wb_incandescent_black_24dp,"成绩"))
                .addItem(new BottomNavigationItem(R.drawable.ic_settings_black_24dp,"设置"))
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
        replaceFragment(new StudentFragment());

        createDb1();

        SQLiteDatabase db = helper.getReadableDatabase();
        String selectSql = "select * from " + Constant.TABLE_NAME;
        Cursor cursor = DbManger.selectDataBySql(db,selectSql,null);
       cursor.moveToFirst();
       do {
           int _id = cursor.getInt(cursor.getColumnIndex(Constant._ID));
           String name = cursor.getString(cursor.getColumnIndex(Constant.NAME));
           Student student = new Student(_id, name);
           StudentList.add(student);
       }while (cursor.moveToNext());
       db.close();


    }
    public void add_student(View view){
        AddStudentDialogFragment fragment = new AddStudentDialogFragment();
        fragment.show(getSupportFragmentManager(),"Dialog");

    }

    //创建数据库
    public void createDb(View view){

        SQLiteDatabase db = helper.getWritableDatabase();
    }
    public void createDb1(){

        SQLiteDatabase db = helper.getWritableDatabase();
    }


    private  void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.first_fragment,fragment);
        transaction.commit();

    }



    @Override
    public void onTabSelected(int position) {
        switch (position){
            case 0:
                replaceFragment(new StudentFragment());
                break;
            case 1:
                replaceFragment(new GradeFragment());
                break;
            case 2:
                replaceFragment(new SettingFragment());
                break;
        }


    }


    @Override
    public void onTabUnselected(int position) {

    }


    @Override
    public void onTabReselected(int position) {

    }
    @Override
    public void onListFragmentInteraction(Student item) {

    }
    //获取输入存入数据库
    @Override
    public void sendMessage(String id,String name,String grades,String midgrades,String finalgrades) {
        if ((id!=null&&!"".equals(id))&&(name!=null&&!"".equals(name))){
            Integer Id = Integer.valueOf(id);
            String Name = name;
            Integer Grades = Integer.valueOf(grades);
            Integer MidGrades = Integer.valueOf(midgrades);
            Integer FinalGrades = Integer.valueOf(finalgrades);
            SQLiteDatabase db = helper.getWritableDatabase();
            String sql = "insert into "+ Constant.TABLE_NAME+
                    " values("+Id+",'"+Name+"',"+Grades+","+MidGrades+","+FinalGrades+")";
            Log.e("mytag1","执行了吗");
            DbManger.exeSQL(db,sql);
            db.close();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onListFragmentInteraction() {

    }
}
