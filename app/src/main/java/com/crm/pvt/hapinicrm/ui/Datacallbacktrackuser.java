package com.crm.pvt.hapinicrm.ui;

import com.crm.pvt.hapinicrm.model.Admin;

public interface Datacallbacktrackuser {
    void remove(Admin admin,String usertype);
    void showUsersUnderAdmin(Admin admin,String usertype);
}
