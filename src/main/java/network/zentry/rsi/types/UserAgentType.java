package network.zentry.rsi.types;

public enum UserAgentType {

    MOZILLA("Mozilla/5.0 (Windows NT 10.0; Win64; x64)"),
    CHROME("Chrome/68.0.3440.106"),
    APPLE_WEBKIT("AppleWebKit/537.36 (KHTML, like Gecko)"),
    SAFARI("Safari/537.36"),
    OPERA("OPR/55.0.2994.61");

    private String userAgent;

    UserAgentType(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public static String allUserAgent() {
        StringBuilder stringBuilder = new StringBuilder();

        for (UserAgentType userAgentType : values())
            stringBuilder.append(userAgentType.getUserAgent() + " ");

        return stringBuilder.toString().trim();
    }
}