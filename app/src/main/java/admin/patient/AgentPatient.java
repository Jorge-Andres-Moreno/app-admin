package admin.patient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import admin.model.LocalDataBase;
import admin.model.Patient;
import admin.utils.DefaultCallback;
import admin.utils.NetworkConstants;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AgentPatient {

    public ArrayList<Patient> pacientes;

    public AgentPatient() {
        pacientes = new ArrayList<>();
        pacientes.add(new Patient());
    }

    public LocalDataBase getLocalDb(){
        return LocalDataBase.getInstance(null);
    }

    public void register(final String _name,
                         final String _id,
                         final String _birth,
                         final String _age,
                         final String _risk,
                         final String _diagnostic,
                         final String _email,
                         final String _telephone,
                         final String _mobile_number,
                         final String _state,
                         final String _city,
                         final String _address,
                         final String _password,
                         final String _name_contact,
                         final String _telephone_contact,
                         final String _relation,
                         final DefaultCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build();

                    JSONObject contact = new JSONObject();
                    contact.put("nombre", _name_contact);
                    contact.put("telefono", _telephone_contact);
                    contact.put("parentesco", _relation);

                    JSONObject informacion = new JSONObject();
                    informacion.put("contacto", contact);
                    informacion.put("nombre", _name);
                    informacion.put("cedula", _id);
                    informacion.put("fecha_nacimiento", _birth);
                    informacion.put("edad", _age);
                    informacion.put("diagnostico", _diagnostic);
                    informacion.put("email", _email);
                    informacion.put("telefono", _telephone);
                    informacion.put("celular", _mobile_number);
                    informacion.put("riesgo", _risk);
                    informacion.put("departamento", _state);
                    informacion.put("ciudad", _city);
                    informacion.put("direccion", _address);
                    informacion.put("password", _password);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("informacion", informacion);
                    jsonObject.put("type", "0");

                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(JSON, jsonObject.toString());

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_PROFILE)
                            .put(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    if (response.code() == 200) {
                        callback.onFinishProcess(true, "success");
                    } else {
                        callback.onFinishProcess(false, "Error intente nuevamente");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFinishProcess(false, "Error en el servidor");
                }
            }
        }).start();
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
