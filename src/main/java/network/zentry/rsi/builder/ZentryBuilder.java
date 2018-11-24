package network.zentry.rsi.builder;

import lombok.Getter;
import network.zentry.rsi.ZentryProcessor;
import network.zentry.rsi.ZentryRSI;
import network.zentry.rsi.types.MethodType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class ZentryBuilder {

    private String method;
    private String url;

    private boolean post;

    private List<Parameter> parameters = new ArrayList<>();
    private List<Parameter> headers = new ArrayList<>();

    private Class<?> classSendRequest;

    public ZentryBuilder(MethodType methodType, String url) {
        this.method = methodType.name();
        this.url = url;
        this.post = !method.equals("GET");
    }

    public ZentryBuilder addParameters(Parameter... parameters) {
        this.parameters.addAll(Arrays.asList(parameters));
        return this;
    }

    public ZentryBuilder addHeaders(Parameter... parameters) {
        this.headers.addAll(Arrays.asList(parameters));
        return this;
    }

    public ZentryRSI build() {
        classSendRequest = ZentryProcessor.getClassSendRequest();
        return new ZentryRSI(this);
    }

}