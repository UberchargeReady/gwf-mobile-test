package com.dyejeekis.gwf_mobile_test.data.remote;

public class ApiEndpoint {

    public static final String BASE_URL = "test-api.gwf.ch";

    public static final String LOGIN = "/auth/token";

    public static final String REFRESH = LOGIN + "/refresh";

    public static final String METERS = "/reports/measurements";

    public static final String TOTAL_VALUES = METERS + "/total";
}
