package com.xianhao365.o2o.fragment.my.store.yhj;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.adapter.YHJNoUseListAdapter;
import com.xianhao365.o2o.entity.YHJEneity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoUseFragment extends Fragment {
    private Activity activity;
    private View view;
    private ListView gridView;
    private ArrayList<YHJEneity> yhj ;
    public NoUseFragment() {
    }
    static NoUseFragment newInstance(ArrayList<YHJEneity> slist) {
        ArrayList list = new ArrayList(); //这个list用于在budnle中传递 需要传递的ArrayList<Object>
        list.add(slist);
        NoUseFragment mf = new NoUseFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data",list);
//        bundle.putSerializable("book", book);
        mf.setArguments(bundle);//Fragment传递数据方式
        return mf;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_no_use, null);
        activity = getActivity();
        Bundle args = getArguments();

        ArrayList list = args.getParcelableArrayList("data");
         yhj= (ArrayList<YHJEneity>) list.get(0);

        initView();
        Logs.i("************33333333333333****************");
        return view;
    }
    private void initView(){
        gridView = (ListView) view.findViewById(R.id.yhj_nouse_gridv);
        gridView.setAdapter(new YHJNoUseListAdapter(activity,yhj));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SXUtils.getInstance(activity).ToastCenter("=="+position);
            }
        });
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            Logs.i("****************************");
        } else {
            Logs.i("2222222222222222****************************");

        }
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            Logs.i("88888888888888888888");
            System.out.println("不可见");

        } else {
            Logs.i("9999999999999999999999");
            System.out.println("当前可见");
        }
    }
}
