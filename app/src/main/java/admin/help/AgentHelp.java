package admin.help;


import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import medical.model.LocalDataBase;
import medical.utils.DefaultCallback;
import medical.utils.NetworkConstants;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AgentHelp {

    public void sendMessageSupport(final String reason, final String message, final DefaultCallback notify) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build();

                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("razon",reason);
                    jsonBody.put("mensaje",message);
                    jsonBody.put("nombre", LocalDataBase.getInstance(null).getUser().getNombre());
                    jsonBody.put("email",LocalDataBase.getInstance(null).getUser().getEmail());
                    jsonBody.put("id",LocalDataBase.getInstance(null).getUser().getId());

                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(JSON, jsonBody.toString());

//                    RequestBody body = new FormBody.Builder()
//                            .add("reason", message)
//                            .add("type", reason)
//                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_HELP)
                            .put(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    if (response.code() == 200) {
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
