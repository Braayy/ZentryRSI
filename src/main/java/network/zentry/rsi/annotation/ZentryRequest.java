package network.zentry.rsi.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZentryRequest {

    String username() default "";

    String password() default "";

    String token() default "";

}