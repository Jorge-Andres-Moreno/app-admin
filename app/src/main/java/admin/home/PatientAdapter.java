package admin.home;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientHolder> {

    private AgentHome agent;
    private Activity activity;

    public PatientAdapter(AgentHome agent, Activity activity) {
        this.agent = agent;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PatientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_section, parent, false);
        return new PatientHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientHolder holder, int position) {
        holder.position = position;
        holder.textView.setText(agent.pacientes.get(position).getNombre());
    }

    @Override
    public int getItemCount() {

        return agent.pacientes.size();
    }

    public class PatientHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public int position;

        private TextView textView;

        public PatientHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            new AgentMonitor(agent.pacientes.get(position).getId());
            DialogSelectParameter dialogSelectParameter = new DialogSelectParameter(activity);
            dialogSelectParameter.show();
//            Intent in = new Intent(activity, MonitorActivity.class);
//            in.putExtra("id",agent.pacientes.get(position).getId());
//            activity.startActivity(in);
        }
    }

}
