package admin.patient;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.admin.R;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Atributos del paciente
     */
    private EditText name;
    private EditText id;
    private EditText birth_date;
    private EditText age;
    private Spinner risk;
    private String risk_state;
    private EditText diagnostic;
    private EditText email;
    private EditText telephone;
    private EditText mobile_number;
    private EditText state;
    private EditText city;
    private EditText address;
    private EditText password;
    private EditText password_confirm;

    /**
     * Atributos del contacto del paciente
     */

    private EditText name_contact;
    private EditText telephone_contact;
    private EditText relation;

    /**
     * Atributos de la clase
     */

    private AgentPatient agent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        // init patient attributes
        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        birth_date = findViewById(R.id.birth_date);
        birth_date.setOnClickListener(this);
        age = findViewById(R.id.age);

        risk = findViewById(R.id.risk);
        final String[] risk_states = {"BAJO", "MEDIO", "ALTO"};
        risk_state = risk_states[0];
        risk.setAdapter(new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, risk_states));
        risk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                risk_state = risk_states[position];
                Log.i("RISK_CHANGE", risk_state);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        diagnostic = findViewById(R.id.diagnostic);
        email = findViewById(R.id.email);
        telephone = findViewById(R.id.telephone);
        mobile_number = findViewById(R.id.mobile_number);
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        password = findViewById(R.id.password);
        password_confirm = findViewById(R.id.confirm_password);

        // init contact attributes
        name_contact = findViewById(R.id.name_contact);
        telephone_contact = findViewById(R.id.telephone_contact);
        relation = findViewById(R.id.relation);

        findViewById(R.id.register).setOnClickListener(this);
        agent = new AgentPatient();

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_strong));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.birth_date:
                showDatePicker();
                break;
            case R.id.register:
                register();
        }
    }

    private void register() {

        String _name = name.getText().toString().trim();
        String _id = id.getText().toString().trim();
        String _birth = birth_date.getText().toString().trim();
        String _age = age.getText().toString().trim();
        String _diagnostic = diagnostic.getText().toString().trim();
        String _email = email.getText().toString().trim();
        String _telephone = telephone.getText().toString().trim();
        String _mobile_number = mobile_number.getText().toString().trim();
        String _state = state.getText().toString().trim();
        String _city = city.getText().toString().trim();
        String _address = address.getText().toString().trim();
        String _password = password.getText().toString().trim();
        String _confirm_password = password_confirm.getText().toString().trim();

        String _name_contact = name_contact.getText().toString().trim();
        String _telephone_contact = telephone_contact.getText().toString().trim();
        String _relation = relation.getText().toString().trim();

        if (!_name.equals(""))
            if (!_id.equals(""))
                if (!_birth.equals(""))
                    if (!_age.equals(""))
                        if (!_diagnostic.equals(""))
                            if (validateEmail(_email))
                                if (_mobile_number.length() == 10)
                                    if (!_state.equals(""))
                                        if (!_city.equals(""))
                                            if (!_address.equals(""))
                                                if (_password.length() >= 6 && _password.equals(_confirm_password))
                                                    if (!_name_contact.equals(""))
                                                        if (!_telephone_contact.equals(""))
                                                            if (!_relation.equals(""))
                                                                Toast.makeText(this, "Rico pa rico", Toast.LENGTH_SHORT).show();
                                                            else
                                                                Toast.makeText(this, "Ingrese un relacion al contacto", Toast.LENGTH_SHORT).show();
                                                        else
                                                            Toast.makeText(this, "Ingrese un telefono de contacto", Toast.LENGTH_SHORT).show();
                                                    else
                                                        Toast.makeText(this, "Ingrese un nombre de contacto", Toast.LENGTH_SHORT).show();
                                                else
                                                    Toast.makeText(this, "Contraseña no valida", Toast.LENGTH_SHORT).show();
                                            else
                                                Toast.makeText(this, "Ingrese una dirección", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(this, "Ingrese una Ciudad", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(this, "Ingrese un departamento", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(this, "Ingrese un numero celular", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(this, "Ingrese un correo electronico", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(this, "Ingrese un diagnostico", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "Ingrese una edad", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Seleccione una fecha de nacimiento", Toast.LENGTH_SHORT).show();
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

    private void showDatePicker() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            birth_date.setText(day + "/" + month + "/" + year);
        }

    }
}
