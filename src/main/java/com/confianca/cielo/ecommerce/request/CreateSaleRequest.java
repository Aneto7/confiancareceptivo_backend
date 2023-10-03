package com.confianca.cielo.ecommerce.request;

import com.confianca.cielo.Environment;
import com.confianca.cielo.Merchant;
import com.confianca.cielo.ecommerce.Sale;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.IOException;

public class CreateSaleRequest extends AbstractSaleRequest<Sale, Sale> {
    public CreateSaleRequest(Merchant merchant, Environment environment) {
        super(merchant, environment);
    }

    @Override
    public Sale execute(Sale param) throws IOException, CieloRequestException {
        String url = environment.getApiUrl() + "1/sales/";
        HttpPost request = new HttpPost(url);
        request.setEntity(new StringEntity(new GsonBuilder().create().toJson(param)));
        HttpResponse response = sendRequest(request);
        return readResponse(response, Sale.class);
    }
}
