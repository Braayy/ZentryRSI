package network.zentry.rsi;

import lombok.Getter;
import lombok.Setter;
import network.zentry.rsi.annotation.ZentryRequest;
import network.zentry.rsi.async.ZentryCallback;
import network.zentry.rsi.builder.Parameter;
import network.zentry.rsi.builder.ZentryBuilder;
import network.zentry.rsi.thread.ZentryThread;

import java.util.List;
import java.util.function.Consumer;

@Getter @Setter
public class ZentryRSI {

    private ZentryRequest zentryRequest;

    private String url;
    private String method;

    private boolean post;

    private List<Parameter> parameters;
    private List<Parameter> headers;

    public ZentryRSI(ZentryBuilder zentryBuilder) {
        this.zentryRequest = ZentryProcessor.checkAnnotationPresent(zentryBuilder.getClassSendRequest());
        this.url = zentryBuilder.getUrl();
        this.method = zentryBuilder.getMethod();
        this.post = zentryBuilder.isPost();
        this.parameters = zentryBuilder.getParameters();
        this.headers = zentryBuilder.getHeaders();
    }

    public String send() {
        return new ZentryThread(this).send();
    }

    public void sendAsync(ZentryCallback callback) {
        new ZentryThread(this).sendAsync(callback);
    }
}