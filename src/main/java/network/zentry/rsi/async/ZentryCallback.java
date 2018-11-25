package network.zentry.rsi.async;

@FunctionalInterface
public interface ZentryCallback {

    void call(String result, Throwable error);

}