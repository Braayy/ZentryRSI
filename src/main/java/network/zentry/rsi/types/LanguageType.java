package network.zentry.rsi.types;

public enum LanguageType {

    PT_BR("pt-BR,br;q=0.5"),
    EN_US("en-US,en;q=0.5");

    private String language;

    LanguageType(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public static String allLanguages() {
        StringBuilder stringBuilder = new StringBuilder();

        for (LanguageType languageType : values())
            stringBuilder.append(languageType.getLanguage() + " ");

        return stringBuilder.toString().trim();
    }
}