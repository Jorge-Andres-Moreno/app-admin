package admin.professional;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import admin.utils.DefaultCallback;
import admin.utils.NetworkConstants;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AgentProfessional {

    public void register(final String _name,
                         final String _id,
                         final String _profession,
                         final String _email,
                         final String _telephone,
                         final String _mobile_number,
                         final String _state,
                         final String _city,
                         final String _password,
                         final String _name_company,
                         final String _telephone_company,
                         final String _address_company,
                         final DefaultCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build();

                    JSONObject company = new JSONObject();
                    company.put("nombre", _name_company);
                    company.put("telefono", _telephone_company);
                    company.put("direccion", _address_company);

                    JSONObject informacion = new JSONObject();
                    informacion.put("empresa", company);
                    informacion.put("nombre", _name);
                    informacion.put("cedula", _id);
                    informacion.put("profesion", _profession);
                    informacion.put("email", _email);
                    informacion.put("telefono", _telephone);
                    informacion.put("celular", _mobile_number);
                    informacion.put("departamento", _state);
                    informacion.put("ciudad", _city);
                    informacion.put("password", _password);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("informacion", informacion);
                    jsonObject.put("type", "1");

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

}
