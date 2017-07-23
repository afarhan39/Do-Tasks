package my.fallacy.intellijtask1.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.fallacy.intellijtask1.R;
import my.fallacy.intellijtask1.model.Task;

/**
 * Created by amirf on 23/07/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> taskList;
    private OnItemClickListener mOnItemClickListener;
    private Context context;

    public TaskAdapter(Context context, final List<Task> x,
                          final OnItemClickListener onItemClickListener) {
        this.context = context;
        taskList = x;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_task, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Task task = taskList.get(position);

        holder.tTaskName.setText(task.getName());
        holder.tTaskDateUpdated.setText(task.getDateUpdated());
        holder.tTaskDescription.setText(task.getDescription());

        holder.mCardTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(task.getId());
                }
            }
        });
        holder.iTaskDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemDelete(task);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_task) CardView mCardTask;
        @BindView(R.id.t_task_name) TextView tTaskName;
        @BindView(R.id.t_task_dateUpdated) TextView tTaskDateUpdated;
        @BindView(R.id.t_task_description) TextView tTaskDescription;
        @BindView(R.id.i_task_delete) ImageView iTaskDelete;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Integer taskID);
        void onItemDelete(Task task);
    }

}
