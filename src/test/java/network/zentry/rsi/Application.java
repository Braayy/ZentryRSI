package network.zentry.rsi;

import network.zentry.rsi.annotation.ZentryRequest;
import network.zentry.rsi.builder.Parameter;
import network.zentry.rsi.builder.ZentryBuilder;
import network.zentry.rsi.thread.ZentryThread;
import network.zentry.rsi.types.MethodType;
import network.zentry.rsi.types.UserAgentType;

import java.text.DecimalFormat;

@ZentryRequest
public class Application {

    public static void main(String[] args) {
        long initial = System.currentTimeMillis();
        ZentryBuilder zentryBuilder = new ZentryBuilder(MethodType.GET, "http://localhost:3000/users/JPereirax");
        zentryBuilder.addHeaders(
                new Parameter("User-Agent", UserAgentType.allUserAgent())
        );

        ZentryRSI zentryRSI = zentryBuilder.build();

        ZentryThread zentryThread = new ZentryThread(zentryRSI);
        String response = zentryThread.send();
        System.out.println("Response (" + new DecimalFormat("#,###").format(System.currentTimeMillis() - initial) + "ms): " + response);
    }

}
