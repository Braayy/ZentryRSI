package network.zentry.rsi;

import network.zentry.rsi.annotation.ZentryRequest;
import network.zentry.rsi.types.MethodType;
import network.zentry.rsi.types.UserAgentType;

@ZentryRequest
public class Application {

    public static void main(String[] args) {
        long initial = System.currentTimeMillis();
        ZentryRSI zentryRSI = new ZentryRSI("https://api.thecatapi.com/v1/images/search?format=json");
        zentryRSI.setMethodRequest(MethodType.GET);
        zentryRSI.setUserAgent(UserAgentType.allUserAgent());
        zentryRSI.sendRequest().whenComplete((r, e) -> {
            if(e != null) {
                e.printStackTrace();
                return;
            }

            System.out.println("Response (" + new DecimalFormat("#,###").format(System.currentTimeMillis() - initial) + "s): " + r);
        });
    }

}
