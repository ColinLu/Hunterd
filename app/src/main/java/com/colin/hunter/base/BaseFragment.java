package com.colin.hunter.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colin.hunter.R;


/**
 * Fragment的生命周期
 * <p>
 * 1.onAttach->onCreate.....->onDestroy->onDettach
 * <p>
 * 这条生命周期是一个完整的生命周期，onAttach表示从Actitivity附着，onDettach表示从Activity剥离，一般来说，调用add方法后会有这条流程。
 * <p>
 * 注意：在FragmentActivity中使用事务的方法attach和dettach并不会调用onAttach和onDettach，那会发生什么变化呢，看第三条
 * <p>
 * 2. onSaveInstanceState-->onStop ... onStart->onResume-->....
 * <p>
 * <p>
 * 这条生命周期和Activity的onRestart有着一定的关联。
 * <p>
 * 注意，Activity每次调用onRestart之后，Fragment就会执行这条生命周期，但是要注意的是，这条生命周期并不可靠，有时不会执行。
 * <p>
 * 3.onDestroyView-> .... ->onCreateView ->onViewCreate
 * <p>
 * <p>
 * 生命周期反了么，答案是否定的。调用者条生命周期往往是使用了事务的方法dettach和attach。
 * <p>
 * 注意：在这种流程中，可以更好的管理Fragment的加载，也可以解决叠加问题，生命周期循环问题。
 * <p>
 * 4.重复onAttach->onCreate.....->onDestroy->onDettach
 * <p>
 * <p>
 * 这条生命周期是由于每次都是用的是replace方法
 * <p>
 * 5.持久态
 * <p>
 * <p>
 * 在经历了.onAttach->onCreate->onCreatView-->...->onResume之后，如果没掉用replace，add,attach,dettach，而是使用了简单的hide,show等方法
 * <p>
 * 注意：这种可用于回退栈操作。
 * <p>
 * <p>
 * <p>
 * 优化Fragment防止页面多次inflate
 * <p>
 * <p>
 * Android 3.0开始Fragment多次受到广泛关注，是的页面开发更加方便。
 * <p>
 * <p>
 * FragmetnManager+FragmentTransaction+Fragment+FragmentActivity是4个永远离不开的组件。因此来说，开发难度有所提升的同时，效率问题也成为了重点考虑的。
 * <p>
 * <p>
 * <p>
 * 开发时经常遇到的Fragment问题有：管理，事务，添加，移除，通信，回退栈。Fragment的效率问题发生的原因是FragmentManager设计并不完美造成的。FragmentManager只做了一半工作，剩下的一半管理工作需要由开发人员来实现。
 * <p>
 * 开发Fragment需要注意一下几点：
 * 1.
 * 如果Fragment页面只出现一次，类似欢迎界面的话 使用 replace+不断新建Fragment方法即可
 * <p>
 * 2.
 * 如果使用Fragment+BackStack，不要使用replace,应该使用add+remove+popBackStack+addToBackStack+hide+show
 * <p>
 * 3.
 * 如果是做Tab页面，确切的说你还需要定义至少一个List<Fragment> fragmentList。
 * <p>
 * 将初始化完毕的所有Fragment加入fragmentlist，然后使用 add+attach+detattch方法进行管理，add负责添加，其他2个方法负责切换，这样效率特别好，防止了Fragment的叠加。
 * <p>
 * 4.
 * Fragment的内部优化，依据Fragment的生命周期，onCreateView和onDestroyView会被反复调用，因此需定义一个全局的contenrView优化如下
 */
public abstract class BaseFragment extends Fragment {


    public interface OnFragmentListener {
        //fragment编号；item位置，判断,回调数据
        void onFragmentClick(int fragment_id, int position, boolean b, Object object);
    }

    private boolean isChanged;
    protected Activity activity;
    protected OnFragmentListener mListener;
    protected View view;
    /**
     * 是否布局文件加载完成
     */
    protected boolean initviewed = false;
    /**
     * TitleBar控件
     */
    protected LinearLayout linear_top_title_bar;
    protected ImageView image_title_left;
    protected TextView text_title_left;
    protected ImageView image_title_back;
    protected ImageView image_title;
    protected TextView text_title;
    protected ImageView image_title_right;
    protected TextView text_title_right;
    protected ImageView image_title_right_extra;
    protected TextView text_title_right_extra;
    protected ImageView image_title_left_extra;
    protected TextView text_title_left_extra;
    /**
     * Fragment当前是否正在加载数据
     */
    private boolean isLoading = false;
    protected int page = 0;
    private int current_asyntask_page = -1;//异步任务加载完成的某页

    /**
     * 对外公开;是否正在异步任务
     *
     * @return
     */
    public boolean isLoading() {
        return isLoading;
    }

    /**
     * 对外公开;是否正在异步任务
     */
    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        //某页数据加载完成之后
        if (!isLoading) {
            current_asyntask_page = page;
        }
    }

    /**
     * 对外公开,布局文件状态
     *
     * @return
     */
    public boolean isInitviewed() {
        return initviewed;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean isChanged) {
        this.isChanged = isChanged;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        this.activity = (Activity) context;
        super.onAttach(context);
        if (context instanceof OnFragmentListener) {
            mListener = (OnFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!initviewed) {
            initTitleBar();
            this.initView();
            this.initData();
            this.initListener();
            initviewed = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && initviewed && !isLoading() && (page != current_asyntask_page)) {
            getAsynData();
        }
    }

    /**
     * 参考：http://stackoverflow.com/questions/10024739/how-to-determine-when-
     * fragment-becomes-visible-in-viewpager
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && initviewed && !isLoading() && (page != current_asyntask_page)) {
            getAsynData();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        hideKeyword();
        initviewed = false;
        view = null;
        activity = null;
        setLoading(false);
        page = 0;
        current_asyntask_page = -1;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initTitleBar() {
        this.linear_top_title_bar = (LinearLayout) this.getView().findViewById(R.id.linear_top_title_bar);
        this.image_title_left = (ImageView) this.getView().findViewById(R.id.image_title_left);
        this.text_title_left = (TextView) this.getView().findViewById(R.id.text_title_left);
        this.image_title_back = (ImageView) this.getView().findViewById(R.id.image_title_back);
        this.image_title = (ImageView) this.getView().findViewById(R.id.image_title);
        this.text_title = (TextView) this.getView().findViewById(R.id.text_title);
        this.image_title_right = (ImageView) this.getView().findViewById(R.id.image_title_right);
        this.text_title_right = (TextView) this.getView().findViewById(R.id.text_title_right);
        this.image_title_right_extra = (ImageView) this.getView().findViewById(R.id.image_title_right_extra);
        this.text_title_right_extra = (TextView) this.getView().findViewById(R.id.text_title_right_extra);
        this.image_title_left_extra = (ImageView) this.getView().findViewById(R.id.image_title_left_extra);
        this.text_title_left_extra = (TextView) this.getView().findViewById(R.id.text_title_right_extra);
        if (this.image_title_back != null) {
            this.image_title_back.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    closeActivity();
                }
            });
        }
    }

    /**
     * 初始化内容布局与行为，仅调用一次
     */
    protected abstract void initView();

    /**
     * 初始化控件上的数据，仅调用一次
     */
    protected abstract void initData();

    /**
     * 初始化控件的监听，仅调用一次
     */
    protected abstract void initListener();

    /**
     * 异步任务加载数据
     */
    protected abstract void getAsynData();

    /**
     * 界面跳转 是否保存当前界面 false 保存；true 不保存
     *
     * @param target
     * @param bundle
     * @param closeSelf
     */
    protected final void startActivity(Class<? extends Activity> target, Bundle bundle, boolean closeSelf) {
        hideKeyword();
        if (closeSelf) {
            getActivity().finish();
        }
        Intent intent = new Intent(getActivity(), target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivity(intent);
        showOpenAnim();
    }

    /**
     * 界面跳转获取值
     *
     * @param target
     * @param requestCode
     * @param bundle
     */
    protected final void startActivityForResult(Class<? extends Activity> target, int requestCode, Bundle bundle) {
        hideKeyword();
        Intent intent = new Intent(getActivity(), target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivityForResult(intent, requestCode);
        showOpenAnim();

    }

    /**
     * 关闭当前界面
     */
    protected final void closeActivity() {
        hideKeyword();
        this.getActivity().onBackPressed();
        showCloseAnim();
    }

    /**
     * 显示动画
     */
    protected void showOpenAnim() {
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * 显示动画
     */
    protected void showCloseAnim() {
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * 隐藏输入法面板
     */
    public void hideKeyword() {
        InputMethodManager imm = ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
        if (imm != null && getActivity().getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
