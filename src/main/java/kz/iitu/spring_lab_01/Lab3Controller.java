package kz.iitu.spring_lab_01;

import kz.iitu.spring_lab_01.config.*;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/lab3")
public class Lab3Controller {

    private final AppProperties props;
    private final EnvironmentBanner banner;
    private final Environment environment;

    public Lab3Controller(AppProperties props, EnvironmentBanner banner,
                          Environment environment) {
        this.props = props;
        this.banner = banner;
        this.environment = environment;
    }

    @GetMapping("/config")
    public Map<String, Object> config() {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("owner", props.owner());
        m.put("group", props.group());
        m.put("mailFrom", props.mail().from());
        m.put("mailRetryCount", props.mail().retryCount());
        m.put("mailTimeout", props.mail().timeout().toString());
        m.put("mailEnabled", props.mail().enabled());
        m.put("serverPort", environment.getProperty("server.port"));
        m.put("activeProfiles", Arrays.asList(environment.getActiveProfiles()));
        m.put("banner", banner.describe());
        return m;
    }
}