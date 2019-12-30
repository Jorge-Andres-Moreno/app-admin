package admin.assign;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;

import admin.delete.DeleteActivity;


public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientHolder> {

    private AgentAssign agent;
    private PatientActivity activity;

    public PatientAdapter(AgentAssign agent, PatientActivity activity) {
        this.agent = agent;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PatientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_patient, parent, false);
        return new PatientHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientHolder holder, int position) {
        holder.position = position;
        holder.name.setText(agent.pacientes.get(position).getName());
        holder.age.setText(agent.pacientes.get(position).getAge());
        holder.risk.setText(agent.pacientes.get(position).getRisk());
        holder.id.setText(agent.pacientes.get(position).getId());
        holder.diagnostic.setText(agent.pacientes.get(position).getDiagnostic());
    }

    @Override
    public int getItemCount() {
        return agent.pacientes.size();
    }

    public class PatientHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public int position;

        private TextView name;
        private TextView age;
        private TextView risk;
        private TextView id;
        private TextView diagnostic;


        public PatientHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            risk = itemView.findViewById(R.id.risk);
            id = itemView.findViewById(R.id.id);
            diagnostic = itemView.findViewById(R.id.diagnostic);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            agent.selectPatient = agent.pacientes.get(position);
            Intent in = new Intent(v.getContext(), ProfessionalActivity.class);
            v.getContext().startActivity(in);
            activity.finish();
            //next intent

        }
    }

}
