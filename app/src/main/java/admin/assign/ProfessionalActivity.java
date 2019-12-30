package admin.assign;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import admin.professional.ProfesionalAdapter;
import admin.utils.DefaultCallback;

public class ProfessionalActivity extends AppCompatActivity implements DefaultCallback {

    private AgentAssign agent;
    private ProfessionalAdapter adapterProfessional;
    private TextView list;
    private RecyclerView recycler;

    private TextView name;
    private TextView age;
    private TextView risk;
    private TextView id;
    private TextView diagnostic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assing_professional);

        agent = AgentAssign.getInstance();

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        risk = findViewById(R.id.risk);
        id = findViewById(R.id.id);
        diagnostic = findViewById(R.id.diagnostic);

        name.setText(agent.selectPatient.getName());
        age.setText(agent.selectPatient.getAge());
        risk.setText(agent.selectPatient.getRisk());
        id.setText(agent.selectPatient.getId());
        diagnostic.setText(agent.selectPatient.getDiagnostic());

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_strong));

        recycler = findViewById(R.id.recycler_persons);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        list = findViewById(R.id.list_text);

        list.setText(R.string.list_professionals);
        adapterProfessional = new ProfessionalAdapter(agent, this);
        recycler.setAdapter(adapterProfessional);
        agent.getProfesionalList(this);

    }

    @Override
    public void onFinishProcess(final boolean hasSucceeded, Object result) {
        if (hasSucceeded)
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (hasSucceeded) {
                        adapterProfessional.notifyDataSetChanged();
                    } else
                        Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_SHORT);
                }
            });
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Asignación");
        builder.setMessage("¿Está seguro que desea asignar este paciente a este profesional?");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do some action
                agent.assign(new DefaultCallback() {
                    @Override
                    public void onFinishProcess(final boolean hasSucceeded, Object result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (hasSucceeded) {
                                    finish();
                                } else
                                    Toast.makeText(ProfessionalActivity.this, "Fail to assign", Toast.LENGTH_SHORT).show();
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
