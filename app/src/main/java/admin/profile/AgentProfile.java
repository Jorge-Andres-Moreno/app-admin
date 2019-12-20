package admin.profile;

import admin.model.LocalDataBase;

public class AgentProfile {

    public AgentProfile(){}

    public LocalDataBase getDB(){
        return LocalDataBase.getInstance(null);
    }

}
