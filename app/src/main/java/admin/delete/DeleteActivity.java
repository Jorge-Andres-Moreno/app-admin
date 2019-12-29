package admin.delete;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;

import admin.utils.DefaultCallback;

public class DeleteActivity extends AppCompatActivity implements DefaultCallback {

    private AgentDelete agent;
    private PatientAdapter adapterPatient;
    private ProfessionalAdapter adapterProfessional;
    private TextView list;
    private RecyclerView recycler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        agent = new AgentDelete();
        agent.isPatientDelete = getIntent().getExtras().getBoolean("isPatientSelect");

        setContentView(R.layout.activity_delete);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_strong));

        recycler = findViewById(R.id.recycler_persons);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        list = findViewById(R.id.list_text);

        if (agent.isPatientDelete) {
            list.setText(R.string.list_patients);
            adapterPatient = new PatientAdapter(agent, this);
            recycler.setAdapter(adapterPatient);
            agent.getPatientList(this);

        } else {
            list.setText(R.string.list_professionals);
            adapterProfessional = new ProfessionalAdapter(agent, this);
            recycler.setAdapter(adapterProfessional);
            agent.getProfesionalList(this);
        }

    }

    @Override
    public void onFinishProcess(final boolean hasSucceeded, Object result) {
        if (hasSucceeded)
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (hasSucceeded) {
                        if (agent.isPatientDelete)
                            adapterPatient.notifyDataSetChanged();
                        else
                            adapterProfessional.notifyDataSetChanged();
                    } else
                        Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_SHORT);
                }
            });
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Eliminación");
        builder.setMessage("Está seguro que desea eliminar toda la información de esta persona");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do some action
                agent.deletedPerson(new DefaultCallback() {
                    @Override
                    public void onFinishProcess(final boolean hasSucceeded, Object result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (hasSucceeded) finish();
                                else
                                    Toast.makeText(DeleteActivity.this, "Fail delete", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                dialogInterface.dismiss();
            }

        });
        builder.setNegativeButton("No, Gracias", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
