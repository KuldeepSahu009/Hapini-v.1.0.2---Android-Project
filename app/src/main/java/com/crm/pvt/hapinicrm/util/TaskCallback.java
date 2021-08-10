package com.crm.pvt.hapinicrm.util;

import com.crm.pvt.hapinicrm.model.TaskModel;

public interface TaskCallback {
    void callToCustomer(TaskModel taskModel);
}
