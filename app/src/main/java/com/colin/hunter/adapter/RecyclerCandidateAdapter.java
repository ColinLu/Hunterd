package com.colin.hunter.adapter;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.colin.hunter.R;
import com.colin.hunter.base.BaseFragment;
import com.colin.hunter.bean.CandidateBean;
import com.colin.hunter.bean.Person;
import com.colin.hunter.data.Constants;
import com.colin.hunter.help.OptionsHelp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class RecyclerCandidateAdapter extends RecyclerView.Adapter<RecyclerCandidateAdapter.ViewHolder> {

    private final List<CandidateBean> candidateBeanList;
    private final BaseFragment.OnFragmentListener mListener;
    private final ImageLoader imageLoader = ImageLoader.getInstance();
    private final DisplayImageOptions options = OptionsHelp.getListOptions();
    private final int fragment_id;
    private final boolean showPop;
    private PopupWindow popupWindow = null;

    private RadioGroup radiogroup_popupwindow_recommend_state;
    private Activity activity;

    private boolean isChange = false;

    public RecyclerCandidateAdapter(int fragment_id, List<CandidateBean> candidateBeanList, BaseFragment.OnFragmentListener listener) {
        this.fragment_id = fragment_id;
        this.candidateBeanList = candidateBeanList;
        mListener = listener;
        showPop = false;
    }

    public RecyclerCandidateAdapter(Activity activity, int fragment_id, List<CandidateBean> candidateBeanList, BaseFragment.OnFragmentListener listener) {
        this.fragment_id = fragment_id;
        this.candidateBeanList = candidateBeanList;
        mListener = listener;
        showPop = true;
        this.activity = activity;
        initPopupwindow();
    }

    private void initPopupwindow() {
        if (showPop) {
            View popView = LayoutInflater.from(activity).inflate(R.layout.layout_popupwindow_recommend_state_choose, null);
            popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setBackgroundDrawable(new ColorDrawable(0));
            //设置popwindow出现和消失动画
            popupWindow.setAnimationStyle(R.style.AppTheme_PopupOverlay);
            radiogroup_popupwindow_recommend_state = (RadioGroup) popView.findViewById(R.id.radiogroup_popupwindow_recommend_state);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_candidate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CandidateBean candidateBean = candidateBeanList.get(position);
        loadImage(holder.image_item_candidate_head, position);
        holder.recycler_item_order_candidate_label.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));
        holder.recycler_item_order_candidate_label.setAdapter(new RecyclerCandidateLabelAdapter(candidateBean.getPerson().getLabelList()));
        //设置item之间的间隔
//        holder.recycler_item_order_candidate_label.addItemDecoration(new SpacesItemDecoration(10));

        setCandidateState(holder.text_item_candidate_recommend_state, position, candidateBean.getState());
        //赋值
        //监听，接口回调
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onFragmentClick(Constants.FRAGMENT_CANDIDATE_LIST_VIEW, position, true, candidateBean);
                }
            }
        });
        holder.text_item_candidate_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onFragmentClick(Constants.FRAGMENT_CANDIDATE_LIST_FEEDBACK_RECORD, position, true, candidateBean);
                }
            }
        });
        holder.text_item_candidate_recommend_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onFragmentClick(Constants.FRAGMENT_CANDIDATE_LIST_RECOMMEND_RECORD, position, true, candidateBean);
                }
            }
        });

        holder.text_item_candidate_interview_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onFragmentClick(Constants.FRAGMENT_CANDIDATE_LIST_INTERVIEW_RECORD, position, true, candidateBean);
                }
            }
        });
        holder.text_item_candidate_recommend_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onFragmentClick(Constants.FRAGMENT_CANDIDATE_LIST_RECOMMEND_POSITION, position, true, candidateBean);
                }
            }
        });
        holder.text_item_candidate_recommend_position_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onFragmentClick(Constants.FRAGMENT_CANDIDATE_LIST_RECOMMEND_POSITION_FINISH, position, true, candidateBean);
                }
            }
        });
        holder.text_item_candidate_recommend_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onFragmentClick(Constants.FRAGMENT_CANDIDATE_LIST_RECOMMEND_OK, position, true, candidateBean);
                }
            }
        });

        holder.text_item_candidate_recommend_state.setOnClickListener(new popAction(holder.text_item_candidate_recommend_state, position));
    }

    private void loadImage(ImageView imageview, int position) {
        final Person person = candidateBeanList.get(position).getPerson();
        if (!TextUtils.isEmpty(person.getHead())) {
            imageview.setTag(person.getHead());
            imageview.setImageResource(R.mipmap.image_loading);
            if (imageview.getTag() != null && imageview.getTag().equals(person.getHead())) {
                imageLoader.displayImage(person.getHead(), imageview, options);
            }
        } else {
            imageview.setImageResource(R.mipmap.image_load_empty);
        }

    }

    private void setCandidateState(TextView textView, int position, int state) {
        textView.setText(Constants.RELEASE_PROFESSION_STATE[state]);
        radiobuttonChoose(state);
    }

    private void radiobuttonChoose(int state) {
        if (null != radiogroup_popupwindow_recommend_state) {
            int length = Constants.RELEASE_PROFESSION_STATE.length;
            for (int i = 0; i < length; i++) {
                RadioButton radioButton = ((RadioButton) radiogroup_popupwindow_recommend_state.getChildAt(i));
                if (i == state) {
                    radioButton.setChecked(true);
                    radioButton.setSelected(true);
                } else {
                    radioButton.setChecked(false);
                    radioButton.setSelected(false);
                }
            }

        }
    }

    /**
     * 每个ITEM中more按钮对应的点击动作
     */
    public class popAction implements View.OnClickListener {
        int position;
        View view;

        public popAction(View view, int position) {
            this.position = position;
            this.view = view;
        }

        @Override
        public void onClick(View v) {
            int[] arrayOfInt = new int[2];
            //获取点击按钮的坐标
            this.view.getLocationOnScreen(arrayOfInt);
            int x = arrayOfInt[0];
            int y = arrayOfInt[1];
            showPop(this.view, x, y, position);
        }
    }

    /**
     * 显示popWindow
     */
    public void showPop(final View view, int x, int y, final int position) {
        //设置popwindow显示位置
//        (View parent, int gravity, int x, int y) {
        popupWindow.showAtLocation(view, 0, x, y + view.getHeight());
        //获取popwindow焦点
        popupWindow.setFocusable(true);
        //设置popwindow如果点击外面区域，便关闭。
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        if (popupWindow.isShowing()) {

        }

        this.radiogroup_popupwindow_recommend_state.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                popupWindow.dismiss();
                int satae = 0;
                if (checkedId == R.id.radiobutton_popupwindow_recommend_state_0) {
                    satae = 0;
                } else if (checkedId == R.id.radiobutton_popupwindow_recommend_state_1) {
                    satae = 1;
                } else if (checkedId == R.id.radiobutton_popupwindow_recommend_state_2) {
                    satae = 2;
                } else if (checkedId == R.id.radiobutton_popupwindow_recommend_state_3) {
                    satae = 3;
                }
                ///////////////////////////////////问题////////////////////////////////////////////
                isChange = candidateBeanList.get(position).getState() == satae ? true : false;
                if (isChange) {
                    Toast.makeText(activity, Constants.RELEASE_PROFESSION_STATE[satae], Toast.LENGTH_SHORT).show();
                    setCandidateState((TextView) view, position, satae);
                    isChange = false;
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return candidateBeanList == null ? 0 : candidateBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView image_item_candidate_head;
        public final TextView text_item_candidate_name;

        public final TextView text_item_candidate_recommend_record;
        public final TextView text_item_candidate_interview_record;
        public final TextView text_item_candidate_recommend_state;
        public final TextView text_item_candidate_feedback;
        public final TextView text_item_candidate_recommend_position;
        public final TextView text_item_candidate_recommend_position_finish;
        public final TextView text_item_candidate_recommend_ok;
        public final RecyclerView recycler_item_order_candidate_label;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            image_item_candidate_head = (ImageView) view.findViewById(R.id.image_item_candidate_head);
            text_item_candidate_name = (TextView) view.findViewById(R.id.text_item_candidate_name);

            text_item_candidate_recommend_record = (TextView) view.findViewById(R.id.text_item_candidate_recommend_record);
            text_item_candidate_interview_record = (TextView) view.findViewById(R.id.text_item_candidate_interview_record);
            text_item_candidate_recommend_state = (TextView) view.findViewById(R.id.text_item_candidate_recommend_state);
            text_item_candidate_feedback = (TextView) view.findViewById(R.id.text_item_candidate_feedback);
            text_item_candidate_recommend_position = (TextView) view.findViewById(R.id.text_item_candidate_recommend_position);
            text_item_candidate_recommend_position_finish = (TextView) view.findViewById(R.id.text_item_candidate_recommend_position_finish);
            text_item_candidate_recommend_ok = (TextView) view.findViewById(R.id.text_item_candidate_recommend_ok);
            recycler_item_order_candidate_label = (RecyclerView) view.findViewById(R.id.recycler_item_order_candidate_label);

            if (fragment_id == Constants.FRAGMENT_CANDIDATE_LIST_ID_0) {//人才——>推荐人才
                text_item_candidate_feedback.setVisibility(View.GONE);
                text_item_candidate_recommend_position_finish.setVisibility(View.GONE);
                text_item_candidate_recommend_record.setVisibility(View.VISIBLE);
                text_item_candidate_interview_record.setVisibility(View.VISIBLE);
                text_item_candidate_recommend_state.setVisibility(View.GONE);
                text_item_candidate_recommend_position.setVisibility(View.VISIBLE);
                text_item_candidate_recommend_ok.setVisibility(View.GONE);
            } else if (fragment_id == Constants.FRAGMENT_CANDIDATE_LIST_ID_1) {//人才——>过往人才
                text_item_candidate_feedback.setVisibility(View.GONE);
                text_item_candidate_recommend_position_finish.setVisibility(View.VISIBLE);
                text_item_candidate_recommend_record.setVisibility(View.VISIBLE);
                text_item_candidate_interview_record.setVisibility(View.GONE);
                text_item_candidate_recommend_ok.setVisibility(View.GONE);
                text_item_candidate_recommend_state.setVisibility(View.GONE);
                text_item_candidate_recommend_position.setVisibility(View.VISIBLE);
            } else if (fragment_id == Constants.FRAGMENT_RECOMMEND_CANDIDATE_LIST_ID_0) {//已接单——>推荐人才——>推荐人才
                text_item_candidate_feedback.setVisibility(View.GONE);
                text_item_candidate_recommend_position_finish.setVisibility(View.VISIBLE);
                text_item_candidate_recommend_record.setVisibility(View.GONE);
                text_item_candidate_interview_record.setVisibility(View.VISIBLE);
                text_item_candidate_recommend_state.setVisibility(View.GONE);
                text_item_candidate_recommend_ok.setVisibility(View.VISIBLE);
                text_item_candidate_recommend_position.setVisibility(View.GONE);
            } else if (fragment_id == Constants.FRAGMENT_RECOMMEND_CANDIDATE_LIST_ID_1) {//已接单——>推荐人才——>过往人才
                text_item_candidate_feedback.setVisibility(View.GONE);
                text_item_candidate_recommend_position_finish.setVisibility(View.VISIBLE);
                text_item_candidate_recommend_record.setVisibility(View.GONE);
                text_item_candidate_interview_record.setVisibility(View.VISIBLE);
                text_item_candidate_recommend_state.setVisibility(View.GONE);
                text_item_candidate_recommend_position.setVisibility(View.GONE);
                text_item_candidate_recommend_ok.setVisibility(View.VISIBLE);
            } else if (fragment_id == Constants.FRAGMENT_CANDIDATE_RECOMMEND_STATE_LIST_ID_0) {//已接单——>已推荐——>进程中
                text_item_candidate_feedback.setVisibility(View.GONE);
                text_item_candidate_recommend_position_finish.setVisibility(View.GONE);
                text_item_candidate_recommend_record.setVisibility(View.VISIBLE);
                text_item_candidate_interview_record.setVisibility(View.VISIBLE);
                text_item_candidate_recommend_state.setVisibility(View.VISIBLE);
                text_item_candidate_recommend_ok.setVisibility(View.GONE);
                text_item_candidate_recommend_position.setVisibility(View.GONE);
            } else if (fragment_id == Constants.FRAGMENT_CANDIDATE_RECOMMEND_STATE_LIST_ID_1) {//已接单——>已推荐——>过往人才
                text_item_candidate_feedback.setVisibility(View.VISIBLE);
                text_item_candidate_recommend_position_finish.setVisibility(View.GONE);
                text_item_candidate_recommend_record.setVisibility(View.VISIBLE);
                text_item_candidate_interview_record.setVisibility(View.GONE);
                text_item_candidate_recommend_ok.setVisibility(View.GONE);
                text_item_candidate_recommend_state.setVisibility(View.GONE);
                text_item_candidate_recommend_position.setVisibility(View.GONE);
            }
        }
    }
}
