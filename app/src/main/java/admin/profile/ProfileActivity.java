package admin.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.admin.R;

public class ProfileActivity extends Activity {

    /**
     * Atributos del Profesional de la salud
     */
    private TextView name;
    private TextView id;
    private TextView profession;
    private TextView email;
    private TextView telephone;
    private TextView mobile_number;
    private TextView state;
    private TextView city;

    /**
     * Atributos de la compania
     */

    private TextView name_company;
    private TextView telephone_company;
    private TextView address_company;


    /**
     * Atributos de la clase
     */
    private AgentProfile agent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_strong));

        agent = new AgentProfile();

        name = findViewById(R.id.name);
        name.setText(agent.getDB().getUser().getName());
        id = findViewById(R.id.id);
        id.setText(agent.getDB().getUser().getId());
        profession = findViewById(R.id.profession);
        profession.setText(agent.getDB().getUser().getProfession());
        email = findViewById(R.id.email);
        email.setText(agent.getDB().getUser().getEmail());
        telephone = findViewById(R.id.telephone);
        telephone.setText(agent.getDB().getUser().getTelephone());
        mobile_number = findViewById(R.id.mobile_number);
        mobile_number.setText(agent.getDB().getUser().getMobile_number());
        state = findViewById(R.id.state);
        state.setText(agent.getDB().getUser().getState());
        city = findViewById(R.id.city);
        city.setText(agent.getDB().getUser().getCity());

        name_company = findViewById(R.id.name_company);
        name_company.setText(agent.getDB().getUser().getName_company());
        telephone_company = findViewById(R.id.telephone_company);
        telephone_company.setText(agent.getDB().getUser().getTelephone_company());
        address_company = findViewById(R.id.address_company);
        address_company.setText(agent.getDB().getUser().getAddress_company());

    }

}
