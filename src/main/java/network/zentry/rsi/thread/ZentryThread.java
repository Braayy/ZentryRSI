package network.zentry.rsi.thread;

import network.zentry.rsi.ZentryRSI;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ZentryThread implements IZentryThread {

    private ZentryRSI zentryRSI;

    public ZentryThread(ZentryRSI zentryRSI) {
        this.zentryRSI = zentryRSI;
    }

    @Override
    public String send() {
        try {
            List<String> parameter = new ArrayList<>();
            zentryRSI.getParameters().forEach(p -> parameter.add(p.getKey() + "=" + p.getValue()));

            URL url = new URL(zentryRSI.isPost() ? zentryRSI.getUrl() : zentryRSI.getUrl() + String.join("&", parameter));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            zentryRSI.getHeaders().forEach(p -> httpURLConnection.setRequestProperty(p.getKey(), p.getValue()));

            if (!zentryRSI.getZentryRequest().username().isEmpty())
                httpURLConnection.setRequestProperty("user", zentryRSI.getZentryRequest().username());
            if (!zentryRSI.getZentryRequest().password().isEmpty())
                httpURLConnection.setRequestProperty("password", zentryRSI.getZentryRequest().password());
            if (!zentryRSI.getZentryRequest().token().isEmpty())
                httpURLConnection.setRequestProperty("x-access-token", zentryRSI.getZentryRequest().token());

            httpURLConnection.setRequestMethod(zentryRSI.getMethod());

            if (zentryRSI.isPost()) {
                httpURLConnection.setDoOutput(true);
                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                byte[] postData = String.join("&", parameter).getBytes();
                dataOutputStream.write(postData);
            }

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = bufferedReader.readLine()) != null)
                    response.append(inputLine);

                bufferedReader.close();
                return response.toString();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return null;
    }

}