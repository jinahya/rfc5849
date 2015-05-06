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


import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class AuthorizationBuilder implements Builder {


    public static final String KEY_REALM = "realm";


    public String getRealm() {

        return realm;
    }


    public void setRealm(final String realm) {

        this.realm = realm;
    }


    public AuthorizationBuilder realm(final String realm) {

        setRealm(realm);

        return this;
    }


    public SignatureBuilder getSignatureBuilder() {

        return signatureBuilder;
    }


    public void setSignatureBuidler(final SignatureBuilder signatureBuilder) {

        this.signatureBuilder = signatureBuilder;
    }


    public AuthorizationBuilder signatureBuilder(
        final SignatureBuilder signatureBuilder) {

        setSignatureBuidler(signatureBuilder);

        return this;
    }


    public String build() throws Exception {

        if (signatureBuilder == null) {
            throw new IllegalStateException("no signatureBuilder set");
        }

        final String oauthSignature = signatureBuilder.build();

        final Map headerParameters = new TreeMap();

        headerParameters.put(BaseStringBuilder.KEY_OAUTH_CALLBACK,
                             signatureBuilder.getBaseStringBuilder()
                             .oauthCallback());
        headerParameters.put(BaseStringBuilder.KEY_OAUTH_CONSUMER_KEY,
                             signatureBuilder.getBaseStringBuilder()
                             .oauthConsumerKey());
        headerParameters.put(BaseStringBuilder.KEY_OAUTH_NONCE,
                             signatureBuilder.getBaseStringBuilder()
                             .oauthNonce());
        headerParameters.put(SignatureBuilder.KEY_OAUTH_SIGNATURE,
                             oauthSignature);
        headerParameters.put(BaseStringBuilder.KEY_OAUTH_SIGNATURE_METHOD,
                             signatureBuilder.getBaseStringBuilder()
                             .oauthSignatureMethod());
        headerParameters.put(BaseStringBuilder.KEY_OAUTH_TIMESTAMP,
                             signatureBuilder.getBaseStringBuilder()
                             .getOauthTimestamp());
        headerParameters.put(BaseStringBuilder.KEY_OAUTH_TOKEN,
                             signatureBuilder.getBaseStringBuilder()
                             .oauthToken());
        headerParameters.put(BaseStringBuilder.KEY_OAUTH_VERIFIER,
                             signatureBuilder.getBaseStringBuilder()
                             .oauthVerifier());
        headerParameters.put(BaseStringBuilder.KEY_OAUTH_VERSION,
                             signatureBuilder.getBaseStringBuilder()
                             .oauthVersion());

        for (final Iterator i = headerParameters.entrySet().iterator();
             i.hasNext();) {
            if (((Map.Entry) i.next()).getValue() == null) {
                i.remove();
            }
        }

        final StringBuffer buffer = new StringBuffer("OAuth");
        {
            if (realm != null) {
                buffer
                    .append(" ")
                    .append(KEY_REALM)
                    .append("=")
                    .append("\"")
                    .append(realm)
                    .append("\"");
            }
            final Iterator i = headerParameters.entrySet().iterator();
            if (i.hasNext()) {
                final Map.Entry entry = (Map.Entry) i.next();
                if (realm != null) {
                    buffer.append(",");
                }
                buffer
                    .append(" ")
                    .append(Percent.encode((String) entry.getKey()))
                    .append("=")
                    .append("\"")
                    .append(Percent.encode((String) entry.getValue()))
                    .append("\"");
            }
            while (i.hasNext()) {
                final Map.Entry entry = (Map.Entry) i.next();
                buffer
                    .append(",")
                    .append(" ")
                    .append(Percent.encode((String) entry.getKey()))
                    .append("=")
                    .append("\"")
                    .append(Percent.encode((String) entry.getValue()))
                    .append("\"");
            }
        }

        return buffer.toString();
    }


    private String realm;


    private SignatureBuilder signatureBuilder;


}

