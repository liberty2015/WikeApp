package com.liberty.wikepro.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import com.liberty.libertylibrary.adapter.base.OnRecyclerItemClickListener;
import com.liberty.libertylibrary.adapter.divider.MarginLinearDecoration;
import com.liberty.libertylibrary.widget.ErrorEmptyLayout;
import com.liberty.wikepro.R;
import com.liberty.wikepro.base.BaseRVFragment;
import com.liberty.wikepro.contact.CourseDetailContact;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.model.bean.User;
import com.liberty.wikepro.util.ImageUtil;
import com.liberty.wikepro.view.activity.CourseDetailActivity;
import com.liberty.wikepro.view.activity.CourseVideoActivity;
import com.liberty.wikepro.view.widget.adapter.CourseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by LinJinFeng on 2017/3/1.
 */

public class CourseDetailFragment extends BaseRVFragment<CourseDetailContact.Presenter,Course> {

    @BindView(R.id.clevel)
    TextView clevel;
    @BindView(R.id.cdescription)
    TextView cdescription;

    @BindView(R.id.teacherName)
    TextView teacherName;
    @BindView(R.id.teacherJob)
    TextView teacherJob;

    Course course;

    @Override
    protected int getLayoutResId() {
        return R.layout.course_detail_fragment;
    }

    @Override
    protected void initData() {
        course=getArguments().getParcelable("course");
        if (course!=null){
            cdescription.setText(course.getDescribtion());
            switch (course.getClevel()){
                case 0:{
                    clevel.setText("简单");
                    clevel.setBackground(getResources().getDrawable(R.drawable.easy));
                }
                break;
                case 1:{
                    clevel.setText("中等");
                    clevel.setBackground(getResources().getDrawable(R.drawable.middle));
                }
                break;
                case 2:{
                    clevel.setText("困难");
                    clevel.setBackground(getResources().getDrawable(R.drawable.hard));
                }
                break;
            }
        }

        list.setLayoutManager(new LinearLayoutManager(getHoldActivity(),LinearLayoutManager.HORIZONTAL,false));
        initAdapter(new CourseAdapter(getHoldActivity()), new OnRecyclerItemClickListener(list) {
            @Override
            public void onItemClick(int position, RecyclerView.ViewHolder holder) {
                super.onItemClick(position, holder);
                Course item = mAdapter.getItem(position);
                if (item.getHasStudy()==0) {
                    Intent intent=new Intent(getHoldActivity(), CourseDetailActivity.class);
                    intent.putExtra("course",item);
                    startActivity(intent);
                }else if (item.getHasStudy()==1){
                    Intent intent=new Intent(getHoldActivity(), CourseVideoActivity.class);
                    intent.putExtra("course",item);
                    startActivity(intent);
                }
            }
        },false,false);
        list.addItemDecoration(new MarginLinearDecoration((int)
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics())));
        ImageView headerImg =findView(R.id.headerImg);
        ImageUtil.getCircleImageIntoImageView(getHoldActivity(),headerImg,
                "http://static.oschina.net/uploads/space/2015/0629/162814_ow45_1767531.jpg",true);
        ((TextView)findView(R.id.txt)).setText("相关课程");

//        mAdapter.addHeader(new BaseRecyclerAdapter.ItemView() {
//            @Override
//            public View onCreateView(ViewGroup viewGroup) {
//                return LayoutInflater.from(getHoldActivity()).inflate(R.layout.course_detail_header,viewGroup,false);
//            }
//
//            @Override
//            public void onBindView(View headerView) {
//                ImageView headerImg = (ImageView) headerView.findViewById(R.id.headerImg);
//                ImageUtil.getCircleImageIntoImageView(getHoldActivity(),headerImg,
//                        "http://static.oschina.net/uploads/space/2015/0629/162814_ow45_1767531.jpg",true);
//            }
//        });
//        initTestData();
    }

    private void initTestData(){
        errorEmptyLayout.setLayoutType(ErrorEmptyLayout.HIDE_LAYOUT);
        List<Course> courses=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Course course=new Course();
            course.setCname("Java 入门");
            course.setPdev("http://pic.uuhy.com/uploads/2011/10/15/Macro-Photos15.jpg");
            course.setSpanCount(2);
            courses.add(course);
        }
        mAdapter.addAll(courses);
    }

    public void showTeacher(User user){
        teacherName.setText(user.getName());
        teacherJob.setText(user.getJob());
    }

    public void showDependentCourses(List<Course> courses){
        if (errorEmptyLayout.getLayoutType()!=ErrorEmptyLayout.HIDE_LAYOUT){
            errorEmptyLayout.setLayoutType(ErrorEmptyLayout.HIDE_LAYOUT);
        }
        mAdapter.addAll(courses);
    }
}
