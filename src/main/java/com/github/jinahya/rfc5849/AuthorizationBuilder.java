/*
 * Copyright 2015 Jin Kwon &lt;jinahya_at_gmail.com&gt;.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jinahya.rfc5849;

import static com.github.jinahya.rfc5849._Percent.encodePercent;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * A builder for builder authorization header value.
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 * @see <a href="https://tools.ietf.org/html/rfc5849#section-3.5.1">3.5.1.
 * Authorization Header (RFC 5849)</a>
 */
public class AuthorizationBuilder implements Builder<String> {

    private static final String REALM = "realm";

    static AuthorizationBuilder of(final String prebuilt) {
        return new AuthorizationBuilder() {
            @Override
            public String build() {
                return prebuilt;
            }
        };
    }

    // ------------------------------------------------------------------- realm
    /**
     * Sets the realm value.
     *
     * @param realm the realm value
     * @return this instance
     */
    public AuthorizationBuilder realm(final String realm) {
        this.realm = realm;
        return this;
    }

    // -------------------------------------------------------------------------
    /**
     * Builds the authorization header value.
     *
     * @return authorization header value.
     * @throws Exception if an error occurs.
     */
    @Override
    public String build() throws Exception {
        if (signatureBuilder == null) {
            throw new IllegalStateException("no signatureBuilder set");
        }
        final BaseStringBuilder baseStringBuilder
                = signatureBuilder.baseStringBuilder();
        if (baseStringBuilder == null) {
            throw new IllegalStateException(
                    "no baseStringBuilder on the signatgureBuilder");
        }
        final Map<String, String> params = new TreeMap<String, String>();
        final String oauthSignature = signatureBuilder.build();
        params.put(Rfc5849Constants.OAUTH_SIGNATURE, oauthSignature);
        for (final Entry<String, List<String>> entiry
             : baseStringBuilder.entries()) {
            final String key = entiry.getKey();
            if (!key.startsWith(BaseStringBuilder.PROTOCOL_PARAMETER_PREFIX)) {
                continue;
            }
            params.put(key, entiry.getValue().get(0));
        }
        final StringBuilder builder = new StringBuilder("OAuth");
        {
            if (realm != null) {
                builder
                        .append(" ")
                        .append(REALM)
                        .append("=\"")
                        .append(realm)
                        .append("\"");
            }
            final Iterator<Entry<String, String>> entries
                    = params.entrySet().iterator();
            if (entries.hasNext()) {
                if (realm != null) {
                    builder.append(",");
                }
                final Entry<String, String> entry = entries.next();
                builder
                        .append(" ")
                        .append(encodePercent(entry.getKey()))
                        .append("=\"")
                        .append(encodePercent(entry.getValue()))
                        .append("\"");
            }
            while (entries.hasNext()) {
                final Entry<String, String> entry = entries.next();
                builder
                        .append(", ")
                        .append(encodePercent(entry.getKey()))
                        .append("=\"")
                        .append(encodePercent(entry.getValue()))
                        .append("\"");
            }
        }
        return builder.toString();
    }

    // --------------------------------------------------------- sinatureBuilder
    /**
     * Sets a signature builder.
     *
     * @param signatureBuilder the signature builder
     * @return this instance
     */
    public AuthorizationBuilder signatureBuilder(
            final SignatureBuilder signatureBuilder) {
        this.signatureBuilder = signatureBuilder;
        return this;
    }

    // -------------------------------------------------------------------------
    private String realm;

    private SignatureBuilder signatureBuilder;
}
