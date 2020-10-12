package com.epam.automation;

import com.epam.automation.common.GlobalVar;
import com.epam.automation.common.annotation.*;
import io.restassured.response.Response;


@Server(GlobalVar.USER_URL)
public interface SmokeCheckApi {

    @Get(path = "/users", description = "Check status")
    Response getUsers();

}
