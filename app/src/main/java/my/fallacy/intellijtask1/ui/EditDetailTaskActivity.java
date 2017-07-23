package my.fallacy.intellijtask1.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.fallacy.intellijtask1.R;
import my.fallacy.intellijtask1.handler.DatabaseHandler;
import my.fallacy.intellijtask1.model.Task;

/**
 * Created by amirf on 23/07/2017.
 */

public class EditDetailTaskActivity extends AppCompatActivity {

    @BindView(R.id.et_task_name)
    EditText etTaskName;
    @BindView(R.id.et_task_description)
    EditText etTaskDescription;
    @BindView(R.id.t_task_id)
    TextView tTaskID;
    @BindView(R.id.t_task_dateCreated)
    TextView tTaskDateCreated;
    @BindView(R.id.t_task_dateUpdated)
    TextView tTaskDateUpdated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_detail_task);
        ButterKnife.bind(this);
        setupTask();
    }

    @OnClick(R.id.b_task_save)
    public void ocBTaskSave() {

        if (validateForm()) {
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());

                Date now = new Date();
                SimpleDateFormat tarikhFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                String tarikh = tarikhFormatter.format(now);

                SimpleDateFormat masaFormatter = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                String masa = masaFormatter.format(now);

            db.editTask(
                    getIntent().getIntExtra("task_ID", 0),
                    etTaskName.getText().toString(),
                    etTaskDescription.getText().toString(),
                    tarikh + " " + masa);

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void setupTask() {
        int taskID = getIntent().getIntExtra("task_ID", 0);
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        Task task = db.getTask(taskID);

        etTaskName.setText(task.getName());
        etTaskDescription.setText(task.getDescription());
        tTaskID.setText(String.valueOf(task.getId()));
        tTaskDateCreated.setText(task.getDateCreated());
        tTaskDateUpdated.setText(task.getDateUpdated());
    }

    private boolean validateForm() {
        boolean result = true;
        if (etTaskName.getText().toString().isEmpty()) {
            etTaskName.setError("Required");
            result = false;
        } else {
            etTaskName.setError(null);

        }

        return result;
    }
}
