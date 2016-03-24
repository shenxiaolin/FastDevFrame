package com.fast.dev.frame.http;


import com.squareup.okhttp.OkHttpClient;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

/**
 * 说明：OkHttp工厂
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/7 17:22
 * <p/>
 * 版本：verson 1.0
 */
public class OkHttpFactory {

    public static OkHttpClient create(long timeout) {
        OkHttpClient client = new OkHttpClient();
        //设置请求时间
        client.setConnectTimeout(timeout, TimeUnit.MILLISECONDS);
        client.setWriteTimeout(timeout, TimeUnit.MILLISECONDS);
        client.setReadTimeout(timeout, TimeUnit.MILLISECONDS);
        //请求不重复
        client.setRetryOnConnectionFailure(false);
        //请求支持重定向
        client.setFollowRedirects(true);
        //启用cookie
        client.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
//        //设置OkHttp，支持访问Https网络
//        if (config != null){
//            if (config.getCertificates() != null){
//                setCertificates(client, config.getCertificates());
//            }
//
//            if (!TextUtils.isEmpty(config.getCer())){
//                setCertificates(client,new ByteArrayInputStream(config.getCer().getBytes()));
//            }
//        }
        return client;
    }

//    /**
//     * 说明：设置证书
//     * @param client
//     * @param certificates
//     */
//    public static void setCertificates(OkHttpClient client,InputStream... certificates) {
//        try {
//            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            keyStore.load(null);
//            int index = 0;
//            for (InputStream certificate : certificates) {
//                String certificateAlias = Integer.toString(index++);
//                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
//
//                try {
//                    if (certificate != null)
//                        certificate.close();
//                } catch (IOException e) {
//                }
//            }
//
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//
//            TrustManagerFactory trustManagerFactory =
//                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//
//            trustManagerFactory.init(keyStore);
//            sslContext.init
//                    (
//                            null,
//                            trustManagerFactory.getTrustManagers(),
//                            new SecureRandom()
//                    );
//            client.setSslSocketFactory(sslContext.getSocketFactory());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
}
