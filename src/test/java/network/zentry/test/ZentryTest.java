package network.zentry.test;

import network.zentry.rsi.ZentryRSI;
import network.zentry.rsi.annotation.ZentryRequest;
import network.zentry.rsi.builder.ZentryBuilder;
import network.zentry.rsi.types.MethodType;

@ZentryRequest
public class ZentryTest {

    public static void main(String[] args) {
        ZentryRSI rsi = new ZentryBuilder(MethodType.GET, "https://api.thecatapi.com/v1/images/search?format=json").build();
        rsi.sendAsync((result, error) -> System.out.println(result));
    }

}