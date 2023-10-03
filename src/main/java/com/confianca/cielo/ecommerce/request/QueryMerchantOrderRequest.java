package com.confianca.cielo.ecommerce.request;

import com.confianca.cielo.Environment;
import com.confianca.cielo.Merchant;
import com.confianca.cielo.ecommerce.QueryMerchantOrderResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

public class QueryMerchantOrderRequest extends AbstractSaleRequest<String, QueryMerchantOrderResponse> {
    public QueryMerchantOrderRequest(Merchant merchant, Environment environment) {
        super(merchant, environment);
    }

    @Override
    public QueryMerchantOrderResponse execute(String merchnatOrderId) throws IOException, CieloRequestException {
        String url = environment.getApiQueryURL() + "1/sales?merchantOrderId=" + merchnatOrderId;

        HttpGet request = new HttpGet(url);
        HttpResponse response = sendRequest(request);

        return readResponse(response, QueryMerchantOrderResponse.class);
    }

}
