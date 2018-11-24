package network.zentry.rsi.builder;

import lombok.Getter;

@Getter
public class Parameter {

    private String key;
    private String value;

    public Parameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
