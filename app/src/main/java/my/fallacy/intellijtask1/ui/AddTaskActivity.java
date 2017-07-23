package my.fallacy.intellijtask1.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
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

public class AddTaskActivity extends AppCompatActivity {

    @BindView(R.id.et_task_name) EditText etTaskName;
    @BindView(R.id.et_task_description) EditText etTaskDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_task);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.b_task_save)
    public void ocBTaskSave() {

        if (validateForm())
        {
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());

            Date now = new Date();
            SimpleDateFormat tarikhFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            String tarikh = tarikhFormatter.format(now);

            SimpleDateFormat masaFormatter = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            String masa = masaFormatter.format(now);

            db.createTask(new Task(
                    etTaskName.getText().toString(),
                    etTaskDescription.getText().toString(),
                    tarikh + " " + masa,
                    tarikh + " " + masa));

            Toast.makeText(this, "Saveddsadaa", Toast.LENGTH_SHORT).show();
            finish();
        }

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
