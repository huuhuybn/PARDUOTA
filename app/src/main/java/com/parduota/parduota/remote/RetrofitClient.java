package com.parduota.parduota.remote;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RawRes;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.parduota.parduota.R;
import com.parduota.parduota.ion.Constant;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = Constant.BASE_URL;
    private static Retrofit retrofit = null;


    public static Retrofit getClient(Context context) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .sslSocketFactory(buildSslSocketFactory(context))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL).client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;

    }

    private static SSLSocketFactory buildSslSocketFactory(Context context) {
        // Add support for self-signed (local) SSL certificates
        // Based on http://developer.android.com/training/articles/security-ssl.html#UnknownCa
        try {

            // Load CAs from an InputStream
            // (could be from a resource or ByteArrayInputStream or ...)
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            // From https://www.washington.edu/itconnect/security/ca/load-der.crt
            InputStream is = context.getResources().getAssets().open("parduota.crt");
            InputStream caInput = new BufferedInputStream(is);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                // System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Create an SSLContext that uses our TrustManager
            SSLContext con = SSLContext.getInstance("TLS");
            con.init(null, tmf.getTrustManagers(), null);
            return con.getSocketFactory();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ABC",e.getMessage());
        }
        return null;

    }


}