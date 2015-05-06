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


package com.github.jinahya.rfc5849.main;


import com.github.jinahya.rfc5849.AuthorizationBuilder;
import com.github.jinahya.rfc5849.BaseStringBuilder;
import com.github.jinahya.rfc5849.Constants;
import com.github.jinahya.rfc5849.NonceBuilder;
import com.github.jinahya.rfc5849.SignatureBuilderHmacSha1Bc;
import com.github.jinahya.rfc5849.TimestampBuilder;
import com.github.jinahya.rfc5849.net.Form;
import static java.lang.invoke.MethodHandles.lookup;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import static java.util.Optional.ofNullable;
import java.util.function.Supplier;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class TwitterPinBasedOAuth {


    private static String systemProperty(final String key,
                                         final Supplier<String> def) {

        return ofNullable(System.getProperty(key)).orElseGet(def);
    }


    private static String systemProperty(final String key) {

        return systemProperty(key, () -> {
            throw new RuntimeException("missing property: " + key);
        });

//        return ofNullable(System.getProperty(key)).orElseThrow(() -> {
//            return new RuntimeException("missing property: " + key);
//        });
    }


    public static void main(final String[] args) throws Exception {

        final Logger logger = getLogger(lookup().lookupClass());

        final String consumerKey = systemProperty("consumer_key");
        final String consumerSecret = systemProperty("consumer_secret");

        final AuthorizationBuilder builder = new AuthorizationBuilder()
            .signatureBuilder(
                new SignatureBuilderHmacSha1Bc()
                .consumerSecret(consumerSecret)
                .tokenSecret("")
                .baseStringBuilder(
                    new BaseStringBuilder()
                    .httpMethod("POST")
                    .baseUri(TwitterConstants.URL_REQUEST_TOKEN)
                    .oauthCallback(Constants.OAUTH_CALLBACK_OUT_OF_BAND)
                    .oauthConsumerKey(consumerKey)
                    .oauthVersion("1.0")
                    .nonceBuilder(new NonceBuilder())
                    .timestampBuilder(new TimestampBuilder())
                    .printer(System.out)
                )
            );
        final String authorization = builder.build();
        logger.debug("authorization: {}", authorization);
        final HttpURLConnection connection
            = (HttpURLConnection) new URL(TwitterConstants.URL_REQUEST_TOKEN)
            .openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", authorization);
        connection.connect();
        final String encoded = IOUtils.toString(
            connection.getInputStream(), "ISO-8859-1");
        logger.debug("encoded: {}", encoded);
        connection.disconnect();
        final Form form = new Form().decode(encoded);
        final String oauthToken
            = (String) ((List) form.asMap().get(Constants.OAUTH_TOKEN)).get(0);;
        final String oauthTokenSecret = (String) ((List) form.asMap().get(Constants.OAUTH_TOKEN_SECRET)).get(0);
        final String oauthCallbackConfirmed = (String) ((List) form.asMap().get(Constants.OAUTH_CALLBACK_CONFIRMED)).get(0);
    }


}
