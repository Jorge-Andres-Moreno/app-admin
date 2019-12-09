package admin.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.admin.R;

public class DialogSelectParameter extends Dialog implements View.OnClickListener {


    private CheckBox pulsoBox;

    private CheckBox ecgBox;

    private TextView textInmediate;

    private TextView textScheduled;


    public DialogSelectParameter(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_parameter);

        findViewById(R.id.actionButton).setOnClickListener(this);

        ecgBox = findViewById(R.id.ecgCheck);
        ecgBox.setOnClickListener(this);

        pulsoBox = findViewById(R.id.pulsoCheck);
        pulsoBox.setOnClickListener(this);

        textInmediate = findViewById(R.id.imediateText);
        textInmediate.setOnClickListener(this);

        textScheduled = findViewById(R.id.scheduleText);
        textScheduled.setOnClickListener(this);
    }


    private void change(boolean isInmediate) {
        if (!isInmediate) {
            pulsoBox.setChecked(false);
            ecgBox.setChecked(true);

            textScheduled.setTextColor(ContextCompat.getColor(getContext(), R.color.blue_strong));
            textInmediate.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_strong));
        } else {
            ecgBox.setChecked(false);
            pulsoBox.setChecked(true);

            textScheduled.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_strong));
            textInmediate.setTextColor(ContextCompat.getColor(getContext(), R.color.blue_strong));

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.pulsoCheck:
            case R.id.imediateText:
                change(true);
                break;
            case R.id.ecgCheck:
            case R.id.scheduleText:
                change(false);
                break;

            case R.id.closeButton:
                dismiss();
                break;

            case R.id.actionButton:
                if (pulsoBox.isChecked() || ecgBox.isChecked()) {
//                    AgentMonitor.INSTANCE.type = pulsoBox.isChecked() ? 0 : 1;
//                    Intent in = new Intent(v.getContext(), MonitorActivity.class);
//                    v.getContext().startActivity(in);
                    this.dismiss();
                } else
                    Toast.makeText(v.getContext(), "Por favor seleccione una categoría", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
