package github.why168.customtabs.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import github.why168.customtabs.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ActivityDesc> activityDescList = new ArrayList<>();
        ActivityListAdapter listAdapter = new ActivityListAdapter(this, activityDescList);

        ActivityDesc activityDesc = createActivityDesc(R.string.title_activity_simple_chrome_tab,
                R.string.description_activity_simple_chrome_tab,
                SimpleCustomTabActivity.class);
        activityDescList.add(activityDesc);

        activityDesc = createActivityDesc(R.string.title_activity_service_connection,
                R.string.description_activity_service_connection,
                ServiceConnectionActivity.class);
        activityDescList.add(activityDesc);

        activityDesc = createActivityDesc(R.string.title_activity_customized_chrome_tab,
                R.string.description_activity_customized_chrome_tab,
                CustomUIActivity.class);
        activityDescList.add(activityDesc);

        activityDesc = createActivityDesc(R.string.title_activity_notification_parent,
                R.string.title_activity_notification_parent,
                NotificationParentActivity.class);
        activityDescList.add(activityDesc);

        RecyclerView recyclerView = (RecyclerView) findViewById(android.R.id.list);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ActivityDesc createActivityDesc(int titleId, int descriptionId, Class<? extends Activity> activity) {
        ActivityDesc activityDesc = new ActivityDesc();
        activityDesc.mTitle = getString(titleId);
        activityDesc.mDescription = getString(descriptionId);
        activityDesc.mActivity = activity;
        return activityDesc;
    }

    private static class ActivityDesc {
        String mTitle;
        String mDescription;
        Class<? extends Activity> mActivity;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        /* package */ TextView mTitleTextView;
        /* package */ TextView mDescriptionTextView;
        /* package */ int mPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTitleTextView = (TextView) itemView.findViewById(R.id.title);
            this.mDescriptionTextView = (TextView) itemView.findViewById(R.id.description);
        }
    }

    private static class ActivityListAdapter extends RecyclerView.Adapter<ViewHolder>
            implements View.OnClickListener {
        private Context mContext;
        private LayoutInflater mLayoutInflater;
        private List<ActivityDesc> mActivityDescs;

        public ActivityListAdapter(Context context, List<ActivityDesc> activityDescs) {
            this.mActivityDescs = activityDescs;
            this.mContext = context;
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public void onClick(View v) {
            int position = ((ViewHolder) v.getTag()).mPosition;
            ActivityDesc activityDesc = mActivityDescs.get(position);
            Intent intent = new Intent(mContext, activityDesc.mActivity);
            mContext.startActivity(intent);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
            View v = mLayoutInflater.inflate(R.layout.item_example_description, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            v.setOnClickListener(this);
            v.setTag(viewHolder);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            final ActivityDesc activityDesc = mActivityDescs.get(position);
            String title = activityDesc.mTitle;
            String description = activityDesc.mDescription;

            viewHolder.mTitleTextView.setText(title);
            viewHolder.mDescriptionTextView.setText(description);
            viewHolder.mPosition = position;
        }

        @Override
        public int getItemCount() {
            return mActivityDescs.size();
        }
    }
}
