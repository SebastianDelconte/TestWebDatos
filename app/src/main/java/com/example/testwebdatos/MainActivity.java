package com.example.testwebdatos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.StringReader;
import java.io.Reader;



public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    private String url;
    public String Salida = "";
    //private TextView tv;
    public TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tv=findViewById(R.id.tvPrueba);
        conectar();
        //Log.d("SALIO", "DEL TRADUCTOR:" + Salida );




    }

    public void conectar()  {


        queue = Volley.newRequestQueue(this);
        url = "http://servidordeltapy.dyndns.org/transboliparWS/WSDelta.asmx/Activadores_Inicializar?Agencia=W99&Usuario=CrEa20X&Password=uDc9FoS";



        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            traductor(response);
                            //textView.setText("SALI DEL TRADUCTOR");

                        } catch (XmlPullParserException | IOException e) {
                            e.printStackTrace();
                            textView.setText("ERROR: catch :(");

                        }
                        // Display the first 500 characters of the response string.
                        //textView.setText(response.substring(0,500).toString());
                        //Toast.makeText(MainActivity.this,response.toString(),Toast.LENGTH_LONG).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("ERROR:Volley" + error.toString());
                    }
                });
        stringRequest.setTag("Actualizacion");


        // Add the request to the RequestQueue.
        queue.add(stringRequest);






    }
    //queue.add(stringRequest);
    //requestQueue.add(stringRequest);
//        InputStream Respuesta;
//        traductor(Respuesta);

//        try {
//            Log.d("ENTRO", "AL CONECTAR" );
//            URL url = new URL(urlxml);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            try {
//                Respuesta= new BufferedInputStream(urlConnection.getInputStream());
//
//            }finally {
//                urlConnection.disconnect();
//            }


//            Respuesta= descargarContenido(urlxml);
//            traductor(Respuesta);
//            Log.d("LO QUE DEVOLVIO", Salida );
//            if (Respuesta != null) {
//                Respuesta.close();
//            }

//        } catch (IOException | XmlPullParserException e) {
//                e.printStackTrace();
//                Log.d("ENTRO", "AL CATCH :(" );
//
//        }
//    private InputStream descargarContenido(String urlString) throws IOException {
//        Log.d("ENTRO", "AL DESCARGAR CONTENIDO" );
//        URL url = new URL(urlString);                   //CREA EL URL
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        urlConnection.setReadTimeout(10000);            //SI TARDA MAS DE X TEIMPO EN ms EN LEERLO SALTA
//        urlConnection.setConnectTimeout(15000);         //IDEM PERO PARA LA ESPERA DE CONECCION
//        urlConnection.setRequestMethod("GET");          //UN GET
//        urlConnection.setDoInput(true);
//        urlConnection.connect();                        //SE CONECTA
//
//        return urlConnection.getInputStream();          //DEVUELVE EL IMPUTSTREAM QUE ES LO QUE LEE DSP EL XMLPULLPARSER
//
//
//    }



    @Override
    protected void onStop () {
        super.onStop();
        if (queue != null) {
            queue.cancelAll("Actualizacion");
        }
    }


    private void traductor(String istream) throws XmlPullParserException, IOException {

        Log.d("ENTRO", "AL TRADUCTOR" );

        String tag;
        String dato = "";
        int nroTabla = 0;

        XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
        parserFactory.setNamespaceAware( true );
        XmlPullParser parser = parserFactory.newPullParser();

        //parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);  //DESACTIVA EL FEATURE(FT BIZA)
        parser.setInput(new StringReader(istream));                //LE DICE DONDE LEER
        //parser.setInput(new ByteArrayInputStream(istream.getBytes()),null);

        int event = parser.getEventType();

        while (event != XmlPullParser.END_DOCUMENT){     //SIEMPRE QUE NO ESTE EN EL FINAL
            //Log.d("ENTRO", "AL WHILE DEL TRADUCTOR" );
            tag = parser.getName();
            switch (event){
                case XmlPullParser.START_TAG:           //SI ESTA EN EL PRINCIPIO DE UN TAG
                    if(tag.equals( "Activadores_Inicializar" )) {
                        nroTabla = 1;
                        Salida = Salida + "\n \t Tabla Nro1:";
                    }
                    if(tag.equals( "Removido_Inicializar21" )) {
                        nroTabla = 2;
                        Salida = Salida + "\n \t Tabla Nro2:";
                    }
                    if(tag.equals( "Activadores_Inicializar1" )) {
                        nroTabla = 3;
                        Salida = Salida + "\n \t Tabla Nro3:";
                    }

                    break;
                case XmlPullParser.TEXT:                //GUARDA LO QUE LEE Y LO GUARDA EN DATO
                    dato=parser.getText();
                    //Log.d("El dato es:", dato );

                    break;
                case XmlPullParser.END_TAG:             //LLEGA AL FINAL DEL TAG Y SACA LOS DATOS INDIVIDUALES
                    if(nroTabla == 1){
                        switch (tag){
                            case "Orden":
                                Salida = Salida + "\n Orden:" + dato;
                                break;
                            case "IdParada":
                                Salida = Salida + ("\n IdParada:" + dato);
                                break;
                            case "NombreParada":
                                Salida = Salida + ("\n NombreParada:" + dato);
                                break;
                            case "HoraPasada":
                                Salida = Salida + ("\n HoraPasada:" + dato);
                                break;

                            case "Activadores_Inicializar":
                                nroTabla=0;
                                break;
                        }
                    }
                    if(nroTabla == 2){
                        switch (tag){
                            case "IdParadaOrigen":
                                Salida = Salida + "\n IdParadaOrigen:" + dato;
                                break;
                            case "IdParadaDestino":
                                Salida = Salida + ("\n IdParadaDestino:" + dato);
                                break;
                            case "TarifaA":
                                Salida = Salida + ("\n TarifaA:" + dato);
                                break;
                            case "TarifaB":
                                Salida = Salida + ("\n TarifaB:" + dato);
                                break;
                            case "Removido_Inicializar21":
                                nroTabla=0;
                                break;
                        }
                    }
                    if(nroTabla == 3){
                        switch (tag){
                            case "IdServicio":
                                Salida = Salida + "\n IdServicio:" + dato;
                                break;
                            case "Empresa":
                                Salida = Salida + ("\n Empresa:" + dato);
                                break;
                            case "RUC":
                                Salida = Salida + ("\n RUC:" + dato);
                                break;
                            case "Domicilio":
                                Salida = Salida + "\n Domicilio:" + dato;
                                break;
                            case "Localidad":
                                Salida = Salida + ("\n Localidad:" + dato);
                                break;
                            case "Telefono":
                                Salida = Salida + ("\n Telefono:" + dato);
                                break;
                            case "WEB":
                                Salida = Salida + "\n WEB:" + dato;
                                break;
                            case "NroProxFactura":
                                Salida = Salida + ("\n NroProxFactura:" + dato);
                                break;
                            case "Timbrado":
                                Salida = Salida + ("\n Timbrado:" + dato);
                                break;
                            case "VtoTimbrado":
                                Salida = Salida + ("\n VtoTimbrado:" + dato);
                                break;
                            case "CalidadA":
                                Salida = Salida + ("\n CalidadA:" + dato);
                                break;
                            case "Activadores_Inicializar1":
                                nroTabla=0;
                                break;
                        }
                    }
                    break;
            }
            event = parser.next();
        }
        textView = (TextView) findViewById(R.id.tvPrueba);
        textView.setMovementMethod(new ScrollingMovementMethod());


        textView.setText( Salida );

        if (queue != null) {
            queue.cancelAll("Actualizacion");
        }



    }

}