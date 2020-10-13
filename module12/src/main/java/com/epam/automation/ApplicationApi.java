package com.epam.automation;

import com.epam.automation.common.GlobalVar;
import com.epam.automation.common.annotation.*;
import io.restassured.response.Response;


@Server(GlobalVar.APPLICATION_URL)
public interface ApplicationApi {

    @Post(path = "/application", description = "Create an application")
    Response createAnApplication(@Body String body);

    @Put(path = "/application", description = "Update an application")
    Response putAnApplication(@Body String body);

    @Get(path = "/application/{}", description = "Get an application")
    Response getAnApplication(@PathVariable int id);

    @Delete(path = "/application/{}", description = "Delete an application")
    Response deleteAnApplication(@PathVariable int id);

}
