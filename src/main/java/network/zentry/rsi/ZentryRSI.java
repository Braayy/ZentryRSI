package network.zentry.rsi;

import network.zentry.rsi.annotation.ZentryRequest;
import network.zentry.rsi.types.MethodType;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class ZentryRSI {

    private String request;
    private ZentryRequest zentryRequest;
    private MethodType methodType;
    private Map<String, String> properties;
    private Map<String, String> parameters;
    private HttpURLConnection httpURLConnection;

    public ZentryRSI(String request) {
        this.zentryRequest = ZentryProcessor.checkAnnotationPresent(ZentryProcessor.getClassSendRequest());
        this.request = request;
        this.properties = new HashMap<>();
        this.parameters = new HashMap<>();
    }

    public ZentryRSI addParameter(String key, String value) {
        parameters.put(key, value);
        return this;
    }

    public void setMethodRequest(MethodType methodType) {
        this.methodType = methodType;
    }

    public void setUserAgent(String userAgentType) {
        properties.put("User-Agent", userAgentType);
    }

    public void addCustomProperty(String name, String token) {
        properties.put(name, token);
    }

    public int getResponseCode() {
        try {
            return httpURLConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 500;
    }

    private void setUsernameAndPassword() {
        if (!zentryRequest.username().isEmpty() && !zentryRequest.password().isEmpty()) {
            httpURLConnection.setRequestProperty("Username", zentryRequest.username());
            httpURLConnection.setRequestProperty("Password", zentryRequest.password());
        }
    }

    private void setToken() {
        if (!zentryRequest.token().isEmpty())
            httpURLConnection.setRequestProperty("Token", zentryRequest.token());
    }

    private StringBuilder setParameters() {
        StringBuilder sb = new StringBuilder();
        AtomicBoolean endsWith = new AtomicBoolean(request.endsWith("/") || request.endsWith("?"));
        new ArrayDeque<>(parameters.entrySet())
                .descendingIterator()
                .forEachRemaining(parameter -> {
                    if (endsWith.get()) {
                        sb.append(parameter);
                        endsWith.set(false);
                    } else sb.append("&").append(parameter);
                });
        parameters.clear();
        return sb;
    }

    private void sendPost() throws IOException {
        if(methodType != MethodType.GET) {
            httpURLConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(setParameters().toString());
            wr.flush();
            wr.close();
        }
    }

    public CompletableFuture<String> sendRequest() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            try {
                httpURLConnection = (HttpURLConnection) new URL(methodType == MethodType.GET ? request += setParameters().toString() : request).openConnection();
                httpURLConnection.setRequestMethod(methodType.name());
                properties.forEach((key, value) -> httpURLConnection.setRequestProperty(key, value));
                properties.clear();

                setUsernameAndPassword();
                setToken();
                sendPost();

                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = bufferedReader.readLine()) != null)
                        response.append(inputLine);

                    bufferedReader.close();
                    cf.complete(response.toString());
                }
            } catch (IOException e) {
                cf.completeExceptionally(e);
            }
        });

        return cf;
    }

}
