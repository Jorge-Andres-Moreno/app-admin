package admin.home;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import admin.model.LocalDataBase;
import admin.model.Patient;
import admin.utils.DefaultCallback;
import admin.utils.NetworkConstants;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AgentHome {

    public ArrayList<Patient> pacientes;

    public AgentHome() {
        pacientes = new ArrayList<>();
        pacientes.add(new Patient());
    }


    public void getPatientList(final DefaultCallback notify) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build();

                    RequestBody body = new FormBody.Builder()
                            .add("id", LocalDataBase.getInstance(null).getUser().getId())
                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_PATIENT)
                            .post(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    if (response.code() == 200) {

                        JSONObject object = new JSONObject(response.body().string());

                        JSONArray array = object.getJSONArray("pacientes");

                        pacientes = new ArrayList<Patient>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject aux = new JSONObject(array.get(i).toString());
                            Patient paciente = new Patient();
                            paciente.setId(aux.getString("id"));
                            paciente.setNombre(aux.getString("nombre"));
                            pacientes.add(paciente);
                        }

                        notify.onFinishProcess(true, "success");
                    } else {
                        notify.onFinishProcess(false, "Error intente nuevamente");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    notify.onFinishProcess(false, "Error en el servidor");
                }
            }
        }).start();
    }


}
