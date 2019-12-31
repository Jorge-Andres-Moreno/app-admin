package admin.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.admin.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import admin.home.HomeActivity;
import admin.utils.DefaultCallback;

public class LoginActivity extends AppCompatActivity implements DefaultCallback {

    private AgentLogin agentLogin;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        agentLogin = new AgentLogin(this);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button button_login = findViewById(R.id.actionButton);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));
        hideSoftKeyBoard();
    }

    private void hideSoftKeyBoard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void registerUser() {

        String email1 = this.email.getText().toString().trim();
        String password = this.password.getText().toString().trim();

        if (!validateEmail(email1))
            Toast.makeText(getApplicationContext(), R.string.alert_email_in, Toast.LENGTH_LONG).show();
        else if (TextUtils.isEmpty(password))
            Toast.makeText(getApplicationContext(), R.string.alert_password_in, Toast.LENGTH_LONG).show();
        else {
            findViewById(R.id.progress_circular).setVisibility(View.VISIBLE);
            findViewById(R.id.actionButton).setVisibility(View.GONE);
            agentLogin.signIn(email1, password, this);
        }

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
    public void onFinishProcess(final boolean hasSucceeded, Object result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progress_circular).setVisibility(View.GONE);
                findViewById(R.id.actionButton).setVisibility(View.VISIBLE);
                if (hasSucceeded) {
                    Toast.makeText(getApplicationContext(), R.string.welcome, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "fail login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
