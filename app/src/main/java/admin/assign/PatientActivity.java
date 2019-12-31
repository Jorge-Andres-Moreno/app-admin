package admin.assign;


import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;


import admin.utils.DefaultCallback;

public class PatientActivity extends AppCompatActivity implements DefaultCallback {

    private AgentAssign agent;
    private PatientAdapter adapterPatient;
    private TextView list;
    private RecyclerView recycler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        agent = new AgentAssign();

        setContentView(R.layout.activity_assign_patient);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_strong));

        recycler = findViewById(R.id.recycler_persons);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        list = findViewById(R.id.list_text);


        list.setText(R.string.list_patients);
        adapterPatient = new PatientAdapter(agent, this);
        recycler.setAdapter(adapterPatient);
        agent.getPatientList(this);

    }

    @Override
    public void onFinishProcess(final boolean hasSucceeded, Object result) {
        if (hasSucceeded)
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (hasSucceeded) {

                        adapterPatient.notifyDataSetChanged();
//                        else
//                            adapterProfessional.notifyDataSetChanged();
                    } else
                        Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_SHORT);
                }
            });
    }


}
