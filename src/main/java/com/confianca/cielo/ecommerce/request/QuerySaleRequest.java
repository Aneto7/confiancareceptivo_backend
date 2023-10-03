package com.confianca.cielo.ecommerce.request;

import com.confianca.cielo.Environment;
import com.confianca.cielo.Merchant;
import com.confianca.cielo.ecommerce.Sale;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

public class QuerySaleRequest extends AbstractSaleRequest<String, Sale> {
    public QuerySaleRequest(Merchant merchant, Environment environment) {
        super(merchant, environment);
    }

    @Override
    public Sale execute(String paymentId) throws IOException, CieloRequestException {
        String url = environment.getApiQueryURL() + "1/sales/" + paymentId;

        HttpGet request = new HttpGet(url);
        HttpResponse response = sendRequest(request);

        return readResponse(response, Sale.class);
    }
}
