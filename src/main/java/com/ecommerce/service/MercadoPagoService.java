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

    // Inyección de valor desde el archivo application.properties
    @Value("${mercadopago.access.token}")
    private String accessToken;

    // Constructor para configurar el access token
    public MercadoPagoService() {
        // Asignar el token al SDK de MercadoPago al iniciar el servicio
        MercadoPagoConfig.setAccessToken("PROD_ACCESS_TOKEN");
    }

    // Método para crear una preferencia de pago
    public Preference crearPreferencia(List<Producto> productos) throws Exception {
        List<PreferenceItemRequest> items = new ArrayList<>();

        // Crear los items de la preferencia a partir de los productos
        for (Producto producto : productos) {
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id(String.valueOf(producto.getId()))
                    .title(producto.getNombre())
                    .quantity(producto.getCantidad())
                    .unitPrice(BigDecimal.valueOf(producto.getPrecio()))
                    .currencyId("ARS")  // Moneda, puedes cambiar a USD, etc.
                    .build();

            items.add(itemRequest);
        }

        // Configurar las URL de retorno (éxito, fallo, pendiente)
        PreferenceBackUrlsRequest backUrlsRequest = PreferenceBackUrlsRequest.builder()
                .success("http://localhost:8080/mercadopago/success")
                .failure("http://localhost:8080/mercadopago/failure")
                .pending("http://localhost:8080/mercadopago/pending")
                .build();

        // Crear la solicitud de preferencia
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrlsRequest)
                .build();

        // Crear el cliente de preferencias y enviar la solicitud
        PreferenceClient client = new PreferenceClient();
        return client.create(preferenceRequest);
    }
}





