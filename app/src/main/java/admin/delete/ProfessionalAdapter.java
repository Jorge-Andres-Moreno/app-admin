package admin.delete;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;


public class ProfessionalAdapter extends RecyclerView.Adapter<ProfessionalAdapter.ProfessionalHolder> {

    private AgentDelete agent;
    private DeleteActivity activity;

    public ProfessionalAdapter(AgentDelete agent, DeleteActivity activity) {
        this.agent = agent;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProfessionalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_professional, parent, false);
        return new ProfessionalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfessionalHolder holder, int position) {
        holder.position = position;
        holder.name.setText(agent.professionals.get(position).getName());
        holder.profession.setText(agent.professionals.get(position).getProfession());
        holder.id.setText(agent.professionals.get(position).getId());
        holder.location.setText(agent.professionals.get(position).getCity() + ", " + agent.professionals.get(position).getState());
    }

    @Override
    public int getItemCount() {
        return agent.professionals.size();
    }

    public class ProfessionalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public int position;

        private TextView name;
        private TextView profession;
        private TextView id;
        private TextView location;


        public ProfessionalHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            profession = itemView.findViewById(R.id.profession);
            id = itemView.findViewById(R.id.id);
            location = itemView.findViewById(R.id.location);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            agent.selectProfessional = agent.professionals.get(position);
            activity.showDialog();
        }
    }

}
