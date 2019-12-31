package admin.professional_add;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.admin.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import admin.utils.DefaultCallback;

public class AddActivity extends AppCompatActivity implements View.OnClickListener, DefaultCallback {

    /**
     * Atributos del Profesional de la salud
     */
    private EditText name;
    private EditText id;
    private EditText profession;
    private EditText email;
    private EditText telephone;
    private EditText mobile_number;
    private EditText state;
    private EditText city;
    private EditText password;
    private EditText password_confirm;

    /**
     * Atributos de la compania
     */

    private EditText name_company;
    private EditText telephone_company;
    private EditText address_company;


    /**
     * Atributos de la clase
     */
    private AgentProfessional agent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profesional);

        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        profession = findViewById(R.id.profession);
        email = findViewById(R.id.email);
        telephone = findViewById(R.id.telephone);
        mobile_number = findViewById(R.id.mobile_number);
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        password = findViewById(R.id.password);
        password_confirm = findViewById(R.id.confirm_password);

        name_company = findViewById(R.id.name_company);
        telephone_company = findViewById(R.id.telephone_company);
        address_company = findViewById(R.id.address_company);

        findViewById(R.id.register).setOnClickListener(this);
        agent = new AgentProfessional();

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_strong));

    }

    private void register() {

        String _name = name.getText().toString().trim();
        String _id = id.getText().toString().trim();
        String _profession = profession.getText().toString().trim();
        String _email = email.getText().toString().trim();
        String _telephone = telephone.getText().toString().trim();
        String _mobile_number = mobile_number.getText().toString().trim();
        String _state = state.getText().toString().trim();
        String _city = city.getText().toString().trim();
        String _password = password.getText().toString().trim();
        String _confirm_password = password_confirm.getText().toString().trim();

        String _name_company = name_company.getText().toString().trim();
        String _telephone_company = telephone_company.getText().toString().trim();
        String _address_company = address_company.getText().toString().trim();


        if (!_name.equals(""))
            if (!_id.equals(""))
                if (!_profession.equals(""))
                    if (validateEmail(_email))
                        if (_mobile_number.length() == 10)
                            if (!_state.equals(""))
                                if (!_city.equals(""))
                                    if (_password.length() >= 6 && _password.equals(_confirm_password))
                                        if (!_name_company.equals(""))
                                            if (!_telephone_company.equals(""))
                                                if (!_address_company.equals(""))
                                                    agent.register(_name, _id, _profession, _email, _telephone, _mobile_number, _state, _city, _password,
                                                            _name_company, _telephone_company, _address_company, this);
                                                else
                                                    Toast.makeText(this, "Ingrese la direccion de la empresa", Toast.LENGTH_SHORT).show();
                                            else
                                                Toast.makeText(this, "Ingrese un telefono de la empresa", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(this, "Ingrese un nombre de la empresa", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(this, "Contraseña no valida", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(this, "Ingrese una Ciudad", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(this, "Ingrese un departamento", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(this, "Ingrese un numero celular", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "Ingrese un correo electronico", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Ingrese una profesion", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Ingrese una cedula", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Ingrese un nombre", Toast.LENGTH_SHORT).show();


    }

    /**
     * Validate given email with regular expression.
     *
     * @param email email for validation
     * @return true valid email, otherwise false
     */
    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                register();
                break;
        }
    }

    @Override
    public void onFinishProcess(final boolean hasSucceeded, Object result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (hasSucceeded)
                    finish();
                else
                    Toast.makeText(AddActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
