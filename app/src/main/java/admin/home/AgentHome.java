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


    public LocalDataBase getLocalDb(){
        return LocalDataBase.getInstance(null);
    }




}
