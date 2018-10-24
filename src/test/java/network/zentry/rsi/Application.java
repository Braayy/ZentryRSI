package network.zentry.rsi;

import network.zentry.rsi.annotation.ZentryRequest;
import network.zentry.rsi.types.MethodType;
import network.zentry.rsi.types.UserAgentType;

@ZentryRequest
public class Application {

    public static void main(String[] args) {
        long initial = System.currentTimeMillis();
        ZentryRSI zentryRSI = new ZentryRSI("localhost:8080/minecraft/add?");
        zentryRSI.setMethodRequest(MethodType.POST);
        zentryRSI.setUserAgent(UserAgentType.allUserAgent());
        zentryRSI.addParameter("uuid", "e543fe0d-c26a-4906-a50f-3bfca5e32239");
        System.out.println(zentryRSI.sendRequest());
        System.out.println(System.currentTimeMillis() - initial + "ms");
    }

}