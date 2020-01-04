package admin.delete;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import admin.model.Patient;
import admin.model.User;
import admin.utils.DefaultCallback;
import admin.utils.NetworkConstants;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AgentDelete {

    public boolean isPatientDelete;
    public ArrayList<Patient> pacientes;
    public Patient selectPatient;

    public ArrayList<User> professionals;
    public User selectProfessional;

    public AgentDelete() {
        pacientes = new ArrayList<>();
        professionals = new ArrayList<>();
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

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_PATIENT_LIST)
                            .get()
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    if (response.code() == 200) {

                        JSONArray array = new JSONArray(response.body().string());

                        pacientes = new ArrayList<Patient>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject aux = new JSONObject(array.get(i).toString());
                            JSONObject inf = aux.getJSONObject("informacion");

                            Patient patient = new Patient();
                            patient.setUID(inf.getString("id"));
                            patient.setName(inf.getString("nombre"));
                            patient.setId(inf.getString("cedula"));
                            patient.setBirth(inf.getString("fecha_nacimiento"));
                            patient.setAge(inf.getString("edad"));
                            patient.setRisk(inf.getString("riesgo"));
                            patient.setDiagnostic(inf.getString("diagnostico"));
                            patient.setEmail(inf.getString("email"));
                            patient.setMobile_number(inf.getString("celular"));
                            patient.setWeight(inf.getString("altura"));
                            patient.setHeight(inf.getString("peso"));

                            try {
                                patient.setTelephone(inf.getString("telefono"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            patient.setState(inf.getString("departamento"));
                            patient.setCity(inf.getString("ciudad"));
                            patient.setAddress(inf.getString("direccion"));
                            patient.setRef(inf.getString("ref"));


                            JSONObject inf_contact = inf.getJSONObject("contacto");
                            patient.setName_contact(inf_contact.getString("nombre"));
                            patient.setTelephone_contact(inf_contact.getString("telefono"));
                            patient.setRelation(inf_contact.getString("parentesco"));

                            pacientes.add(patient);
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

    public void deletedPerson(final DefaultCallback notify) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okhttp = new OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .build();

                RequestBody body = new FormBody.Builder()
                        .add("type", (isPatientDelete ? 0 : 1) + "")
                        .add("id", isPatientDelete ? selectPatient.getUID() : selectProfessional.getUID())
                        .build();


                Request request = new Request.Builder()
                        .url(NetworkConstants.URL + NetworkConstants.PATH_PROFILE)
                        .delete(body)
                        .build();

                try {

                    Response response = okhttp.newCall(request).execute();

                    if (response.code() == 200)
                        notify.onFinishProcess(true, null);
                    else
                        notify.onFinishProcess(false, null);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    public void getProfesionalList(final DefaultCallback notify) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_PROFESSIONAL_LIST)
                            .get()
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    if (response.code() == 200) {

                        JSONArray array = new JSONArray(response.body().string());
                        professionals = new ArrayList<User>();

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject aux = new JSONObject(array.get(i).toString());
                            JSONObject inf = aux.getJSONObject("informacion");

                            User user = new User();
                            user.setUID(inf.getString("id"));
                            user.setName(inf.getString("nombre"));
                            user.setId(inf.getString("cedula"));
                            user.setProfession(inf.getString("profesion"));
                            user.setEmail(inf.getString("email"));
                            user.setMobile_number(inf.getString("celular"));
                            user.setTelephone(inf.getString("telefono"));
                            user.setState(inf.getString("departamento"));
                            user.setCity(inf.getString("ciudad"));

                            JSONObject company = inf.getJSONObject("empresa");
                            user.setName_company(company.getString("nombre"));
                            user.setTelephone_company(company.getString("telefono"));
                            user.setAddress_company(company.getString("direccion"));

                            professionals.add(user);
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
