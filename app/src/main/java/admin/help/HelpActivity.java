package admin.help;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.android.myapplication.R;

import medical.utils.DefaultCallback;

public class HelpActivity extends Activity implements DefaultCallback {

    private Spinner spin;
    private Button btnSend;
    private EditText msjEdit;
    private ArrayAdapter adapter;
    private AgentHelp agent;
    private String reason = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_strong));

        agent = new AgentHelp();
        reason="Sugerencia";

        final String[] objects = {"Sugerencia", "Duda", "Queja"};
        btnSend = findViewById(R.id.send);
        msjEdit = findViewById(R.id.message);
        spin = findViewById(R.id.spinner);
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, objects);
        spin.setAdapter(adapter);


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reason = objects[position];
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });
    }

    public void send() {
        String msj = msjEdit.getText().toString().trim();
        Log.i("msj:", msj);
        if (!msj.equals("")) {
            agent.sendMessageSupport(reason, msj, this);
        } else
            finish();
    }

    @Override
    public void onFinishProcess(boolean hasSucceeded, Object result) {
        if (hasSucceeded)
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            });
        else
            runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
