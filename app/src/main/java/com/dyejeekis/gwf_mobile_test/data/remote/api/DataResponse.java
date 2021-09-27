package com.dyejeekis.gwf_mobile_test.data.remote.api;

import com.dyejeekis.gwf_mobile_test.util.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public abstract class DataResponse extends Response {

    public DataResponse(String json) {
        try {
            Object obj = new JSONTokener(json).nextValue();
            JSONObject jsonObject = (JSONObject) obj;
            String code = jsonObject.getString("code");

            if (code.equalsIgnoreCase("token_not_valid")) {
                JSONObject detailObject = jsonObject
                        .getJSONArray("detail")
                        .getJSONObject(0);
                String tokenType = detailObject.getString("token_type");

                if (tokenType.equalsIgnoreCase("access"))
                    throw new NetworkUtil.AccessTokenExpiredException();
                else if (tokenType.equalsIgnoreCase("refresh"))
                    throw new NetworkUtil.RefreshTokenExpiredException();
            }
        } catch (JSONException | ClassCastException e) {
            //e.printStackTrace();
        }
    }
}
