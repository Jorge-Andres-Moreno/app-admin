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

import admin.assign.PatientActivity;
import admin.professional.AddActivity;
import admin.delete.DeleteActivity;


public class DialogSelectProfessional extends Dialog implements View.OnClickListener {


    private CheckBox add_check;
    private CheckBox delete_check;
    private CheckBox assign_check;

    private TextView add_text;
    private TextView delete_text;
    private TextView assign_text;


    public DialogSelectProfessional(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_parameter);

        findViewById(R.id.actionButton).setOnClickListener(this);

        add_check = findViewById(R.id.add_check);
        add_check.setOnClickListener(this);

        delete_check = findViewById(R.id.delete_check);
        delete_check.setOnClickListener(this);

        assign_check = findViewById(R.id.assign_check);
        assign_check.setOnClickListener(this);

        add_text = findViewById(R.id.add_text);
        add_text.setOnClickListener(this);

        delete_text = findViewById(R.id.delete_text);
        delete_text.setOnClickListener(this);

        assign_text = findViewById(R.id.assing_text);
        assign_text.setOnClickListener(this);
    }


    private void change(int state) {
        if (state == 0) {
            add_check.setChecked(true);
            add_text.setTextColor(ContextCompat.getColor(getContext(), R.color.blue_strong));

            delete_check.setChecked(false);
            delete_text.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_strong));

            assign_check.setChecked(false);
            assign_text.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_strong));

        } else if (state == 1) {
            add_check.setChecked(false);
            add_text.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_strong));

            delete_check.setChecked(true);
            delete_text.setTextColor(ContextCompat.getColor(getContext(), R.color.blue_strong));

            assign_check.setChecked(false);
            assign_text.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_strong));

        } else if (state == 2) {

            add_check.setChecked(false);
            add_text.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_strong));

            delete_check.setChecked(false);
            delete_text.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_strong));

            assign_check.setChecked(true);
            assign_text.setTextColor(ContextCompat.getColor(getContext(), R.color.blue_strong));

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.add_check:
            case R.id.add_text:
                change(0);
                break;
            case R.id.delete_check:
            case R.id.delete_text:
                change(1);
                break;
            case R.id.assign_check:
            case R.id.assing_text:
                change(2);
                break;

            case R.id.closeButton:
                dismiss();
                break;

            case R.id.actionButton:
                if (add_check.isChecked()) {
                    Intent in = new Intent(v.getContext(), AddActivity.class);
                    v.getContext().startActivity(in);
                    this.dismiss();
                } else if (delete_check.isChecked()) {
                    Intent in = new Intent(v.getContext(), DeleteActivity.class);
                    in.putExtra("isPatientSelect", false);
                    v.getContext().startActivity(in);
                    this.dismiss();

                } else if (assign_check.isChecked()) {
                    Intent in = new Intent(v.getContext(), PatientActivity.class);
                    v.getContext().startActivity(in);

                    this.dismiss();
                } else
                    Toast.makeText(v.getContext(), "Por favor seleccione una categoría", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
