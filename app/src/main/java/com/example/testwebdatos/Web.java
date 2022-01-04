package com.example.testwebdatos;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Web {

    private static String urlxml =  "http://servidordeltapy.dyndns.org/expresoparaguay/wsdelta.asmx/Removido_ObtenerDatosIniciales2?Agencia=W91&Usuario=CrEa20X&Password=uDc9FoS";
    public InputStream Respuesta;

    public void conectar() throws IOException {
        //String Xml = null;
        try {
            Respuesta= descargarContenido(urlxml);
        } catch (IOException e) {
            e.printStackTrace();

        }



    }
    private InputStream descargarContenido(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(15000);
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoInput(true);
        urlConnection.connect();
        return urlConnection.getInputStream();


    }

}
