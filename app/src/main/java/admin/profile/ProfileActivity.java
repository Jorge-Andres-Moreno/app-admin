package admin.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.admin.R;

import admin.model.LocalDataBase;

public class ProfileActivity extends Activity {

    private TextView nombre;
    private TextView correo;
    private TextView cedula;
    private TextView especialidad;
    private TextView telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_strong));

        nombre = findViewById(R.id.nombre);
        nombre.setText(LocalDataBase.getInstance(null).getUser().getNombre());

        correo = findViewById(R.id.correo);
        correo.setText(LocalDataBase.getInstance(null).getUser().getEmail());

        cedula = findViewById(R.id.cedula);
        cedula.setText(LocalDataBase.getInstance(null).getUser().getCedula());

        especialidad = findViewById(R.id.especialidad);
        especialidad.setText(LocalDataBase.getInstance(null).getUser().getEspecialidad());

        telefono = findViewById(R.id.telefono);
        telefono.setText(LocalDataBase.getInstance(null).getUser().getEspecialidad());

    }

}
