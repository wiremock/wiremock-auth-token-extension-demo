package io.wiremock.demo;

import com.github.tomakehurst.wiremock.common.url.PathTemplate;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformerV2;
import com.github.tomakehurst.wiremock.http.QueryParameter;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.binary.Hex;

import java.time.LocalDateTime;
import java.util.UUID;


public class TokenGenTransformer implements ResponseDefinitionTransformerV2 {

    public static final PathTemplate PATH_TEMPLATE = new PathTemplate("/payment/getAuthToken/{username}");

    @Override
    public String getName() {
        return "token-generator";
    }

    @Override
    public ResponseDefinition transform(ServeEvent serveEvent) {
        return PATH_TEMPLATE.matches(serveEvent.getRequest().getUrl()) ?
                generateAuthToken(serveEvent) :
                serveEvent.getResponseDefinition();
    }

    private ResponseDefinition generateAuthToken(ServeEvent serveEvent) {
        String userId = PATH_TEMPLATE.parse(serveEvent.getRequest().getUrl()).get("username");
        final QueryParameter activeParameter = serveEvent.getRequest().queryParameter("active");
        boolean activeStatus = activeParameter.isPresent() && Boolean.parseBoolean(activeParameter.firstValue());

        String email;
        String timestamp = LocalDateTime.now().toString();
        String apiKey = UUID.randomUUID().toString();

        String tokenEncoded;
        if (activeStatus) {
            // Create SHA-256 hash and hex encode it
            email = userId + "@wiremockcloud.com";
            String tokenStr = email + ":" + timestamp + ":" + apiKey;
            tokenEncoded = DigestUtils.sha256Hex(tokenStr);
        } else {
            email = userId + "@wiremockoss.com";
            tokenEncoded = null;
        }

        return ResponseDefinition.okForJson("{ \"token\": \"" + tokenEncoded + "\"}");
    }

    @Override
    public boolean applyGlobally() {
        return ResponseDefinitionTransformerV2.super.applyGlobally();
    }
}
