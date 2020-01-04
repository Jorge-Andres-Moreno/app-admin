package admin.login;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import admin.model.LocalDataBase;
import admin.model.User;
import admin.utils.DefaultCallback;
import admin.utils.NetworkConstants;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AgentLogin {

    private FirebaseAuth firebaseAuth;
    private boolean isSignIn;

    /**
     * Constructor of Agent that Sign in
     *
     * @param context Context with activity invoke
     */
    public AgentLogin(Context context) {
        firebaseAuth = FirebaseAuth.getInstance();
        isSignIn = LocalDataBase.getInstance(context).getUser() != null;
    }

    /**
     * Method that Sign Out the User
     */
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        LocalDataBase.getInstance(null).deletedCredentials();
    }

    /**
     * @return the answer is User is Sign in that mobile application
     */
    public boolean isSignIn() {
        return isSignIn;
    }

    /**
     * Method of Sign In with Firebase and then get all user data of backend
     *
     * @param email    email that user gonna be a try sign in
     * @param password password that user gonna be a try sign in
     * @param callback uses that response
     */
    public void signIn(final String email, final String password, final DefaultCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() && task.getResult() != null && task.getResult().getUser() != null)
                            getUserData(callback, task.getResult().getUser().getUid());
                        else
                            callback.onFinishProcess(false, null);
                    }
                });
            }
        }).start();
    }

    /**
     * Method gonna be a try all user information of backend and save this information in local database
     *
     * @param callback uses that response
     * @param uid      Uid params of firebase which is used as an id
     */
    private void getUserData(final DefaultCallback callback, final String uid) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okhttp = new OkHttpClient.Builder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(5, TimeUnit.SECONDS)
                        .build();

                RequestBody body = new FormBody.Builder()
                        .add("type", "1")
                        .add("id", uid)
                        .build();

                Request request = new Request.Builder()
                        .url(NetworkConstants.URL + NetworkConstants.PATH_PROFILE)
                        .post(body)
                        .build();
                try {

                    Response response = okhttp.newCall(request).execute();

                    if (response.code() == 200) {

                        assert response.body() != null;
                        JSONObject object = new JSONObject(response.body().string());

                        User user = new User();
                        user.setUID(object.getString("id"));
                        user.setName(object.getString("nombre"));
                        user.setId(object.getString("cedula"));
                        user.setProfession(object.getString("profesion"));
                        user.setEmail(object.getString("email"));
                        user.setMobile_number(object.getString("celular"));
                        user.setTelephone(object.getString("telefono"));
                        user.setState(object.getString("departamento"));
                        user.setCity(object.getString("ciudad"));

                        JSONObject company = object.getJSONObject("empresa");
                        user.setName_company(company.getString("nombre"));
                        user.setTelephone_company(company.getString("telefono"));
                        user.setAddress_company(company.getString("direccion"));

                        LocalDataBase.getInstance(null).saveUser(user);
                        callback.onFinishProcess(true, null);
                    } else
                        callback.onFinishProcess(false, null);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

}
