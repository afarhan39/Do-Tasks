package my.fallacy.intellijtask1.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.fallacy.intellijtask1.R;
import my.fallacy.intellijtask1.adapter.TaskAdapter;
import my.fallacy.intellijtask1.handler.DatabaseHandler;
import my.fallacy.intellijtask1.model.Task;

/**
 * Created by amirf on 23/07/2017.
 */

public class ViewTaskActivity extends AppCompatActivity implements TaskAdapter.OnItemClickListener {


    @BindView(R.id.starter) TextView starter;
    @BindView(R.id.taskRecyclerView) RecyclerView mTaskRecyclerView;
    private TaskAdapter mTaskAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolbar();
        setupAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupAdapter();
    }

    @OnClick(R.id.f_add)
    public void ocFAdd(View view) {
        startActivity(new Intent(this, AddTaskActivity.class));
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupAdapter() {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<Task> taskList = db.getAllTasks();

        if (!taskList.isEmpty())
            starter.setVisibility(View.GONE);


        mTaskAdapter = new TaskAdapter(getBaseContext(), taskList, this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        mTaskRecyclerView.setLayoutManager(mLayoutManager);
        mTaskRecyclerView.setAdapter(mTaskAdapter);
    }

    @Override
    public void onItemClick(Integer taskID) {
        Intent intent = new Intent(this, EditDetailTaskActivity.class);
        intent.putExtra("task_ID", taskID);
        startActivity(intent);

    }

    @Override
    public void onItemDelete(final Task task) {
        AlertDialog.Builder alert = new AlertDialog.Builder(ViewTaskActivity.this);
        alert.setTitle("Confirm Delete?");
        alert.setMessage("Confirm to delete task.");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.deleteTask(task);
                setupAdapter();
                Toast.makeText(ViewTaskActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();

    }
}

