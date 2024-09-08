package com.ecommerce.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.resources.preference.Preference;
import com.ecommerce.model.Producto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    public MercadoPagoService() {
        // Configurar el token en el SDK
        MercadoPagoConfig.setAccessToken("PROD_ACCESS_TOKEN");
    }

    public Preference crearPreferencia(List<Producto> productos) throws Exception {
        List<PreferenceItemRequest> items = new ArrayList<>();

        for (Producto producto : productos) {
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id(String.valueOf(producto.getId()))
                    .title(producto.getNombre())
                    .quantity(producto.getCantidad())
                    .unitPrice(BigDecimal.valueOf(producto.getPrecio()))
                    .currencyId("ARS")  // Moneda
                    .build();

            items.add(itemRequest);
        }

        PreferenceBackUrlsRequest backUrlsRequest = PreferenceBackUrlsRequest.builder()
                .success("http://localhost:8080/mercadopago/success")
                .failure("http://localhost:8080/mercadopago/failure")
                .pending("http://localhost:8080/mercadopago/pending")
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrlsRequest)
                .build();

        PreferenceClient client = new PreferenceClient();
        return client.create(preferenceRequest);
    }
}





