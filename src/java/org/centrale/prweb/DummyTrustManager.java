/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.centrale.prweb;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author kwyhr
 */
public class DummyTrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        // do nothing
    }

    @Override
    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        // do nothing
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new java.security.cert.X509Certificate[0];
    }
}
