package com.example.junli.studentperformancemanagement.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.junli.studentperformancemanagement.R;
import com.example.junli.studentperformancemanagement.utils.Constant;
import com.example.junli.studentperformancemanagement.utils.DbManger;
import com.example.junli.studentperformancemanagement.utils.MySqliteHelper;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class GradeFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private ListView StudentListView;
    private MySqliteHelper helperone;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GradeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GradeFragment newInstance(int columnCount) {
        GradeFragment fragment = new GradeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grade, container, false);

        StudentListView = view.findViewById(R.id.Grade_ListView);
        helperone = new MySqliteHelper(view.getContext());
        final SQLiteDatabase db = helperone.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + Constant.TABLE_NAME,null);
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this.getActivity(),R.layout.fragment_grade_item,cursor,
                new String[]{Constant.NAME,Constant._ID,Constant.GRADES,Constant.MIDGRADES,Constant.FIANLGRADES},
                new int[]{R.id.NameItem,R.id.IDItem,R.id.GradeItem,R.id.MidItem,R.id.FinalItem},
                        SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        StudentListView.setAdapter(adapter);
        StudentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> adapterView, final View view, final int position, long id) {

                StudentListView.setOnTouchListener(new View.OnTouchListener() {

                    TextView IDItem =(TextView)view.findViewById(R.id.IDItem);
//                    String IDItem = adapter.getItem(position).toString();
//                    String listView = String.valueOf(StudentListView.getChildAt(position-StudentListView.getFirstVisiblePosition()));
                    private int mDownX = 0;
                    private int mDownY = 0;
                    // 滑动效果
                    private int mLastMoveX = 0;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                mDownX = Math.round(event.getX());
                                mDownY = Math.round(event.getY());

                                mLastMoveX = mDownX;
                                break;
                            case MotionEvent.ACTION_MOVE: {
                                int moveX = Math.round(event.getX());
                                // 获取2次点击的item位置  item以外的pos是-1
                                int downPosition = ((ListView)v).pointToPosition(mDownX, mDownY);
                                // 点击在有效item内 才做处理
                                boolean willScroll = (downPosition >= 0);
                                if (willScroll) {
                                    // 需要先获取第一个可见的view  因为getChildAt获取view的时候，坐标是从第一个可见view开始的
                                    int firstVisiableIndex = ((ListView)v).getFirstVisiblePosition();
                                    View viewToScroll = ((ListView)v).getChildAt(downPosition - firstVisiableIndex);

                                    // 滑动
                                    viewToScroll.scrollBy(mLastMoveX - moveX, 0);
                                    mLastMoveX = moveX;
                                }
                                Log.e("tag","实现删除数据");
                                Log.e("mytag","IDItem:"+IDItem.getText().toString());
                                String  delSql =  "delete from "+Constant.TABLE_NAME+" where "+Constant._ID+"="+IDItem.getText().toString();
                                DbManger.exeSQL(db,delSql);
                            }
                            break;
                            case MotionEvent.ACTION_UP:
                            case MotionEvent.ACTION_CANCEL: {
                                int upX = Math.round(event.getX());
                                int upY = Math.round(event.getY());
                                // 获取2次点击的item位置  item以外的pos是-1
                                int downPosition = ((ListView)v).pointToPosition(mDownX, mDownY);
                                boolean willScroll = (downPosition >= 0);
                                if (willScroll) {
                                    // 回滚
                                    int firstVisiableIndex = ((ListView)v).getFirstVisiblePosition();
                                    View viewToScroll = ((ListView)v).getChildAt(downPosition - firstVisiableIndex);

                                    viewToScroll.scrollBy(mLastMoveX - mDownX, 0);
                                }
                            }

                            break;

                        }
                        return false;
                    }
                });
            }
        });
        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction();
    }
}
