package org.ucommerce.shared.rest.client;

import com.google.common.escape.Escaper;
import com.google.common.html.HtmlEscapers;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Builds URI in a fluid-syntax fashion.
 */
public class URIBuilder {

    private String schemeAndHost;
    private List<Pair<String, String>> queryParams = new ArrayList<>();

    private String path = "";

    public URIBuilder() {

    }

    public URIBuilder schemeAndHost(String schemeAndHost) {
        this.schemeAndHost = schemeAndHost;
        return this;
    }

    public URIBuilder appendQuery(String name, String value) {
        queryParams.add(new ImmutablePair<>(name, value));
        return this;
    }

    public URIBuilder appendPath(String newPath) {
        if (!path.endsWith("/") && !newPath.startsWith("/")) {
            path += "/";
        }
        path += newPath;
        return this;
    }

    public URI uri() {
        StringBuilder builder = new StringBuilder();
        builder.append(schemeAndHost);
        builder.append(path);
        boolean paramAdded = false;
        Escaper escaper = HtmlEscapers.htmlEscaper();
        for (Pair<String, String> param : queryParams) {
            if (!paramAdded) {
                paramAdded = true;
                builder.append("?");
            }
            builder.append(param.getKey() + "=" + escaper.escape(param.getValue()));
        }
        try {
            return new URI(builder.toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Wrong URI: " + builder, e);
        }
    }

}
