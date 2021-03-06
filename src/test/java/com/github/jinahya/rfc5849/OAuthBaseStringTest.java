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

import static java.lang.invoke.MethodHandles.lookup;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests {@link OAuthBaseString}.
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class OAuthBaseStringTest {

    private static final Logger logger = getLogger(lookup().lookupClass());

    static OAuthBaseString baseStringBuilderOf(final String baseString) {
        return new OAuthBaseString() {
            @Override
            public String get() {
                return baseString;
            }
        };
    }

    static OAuthBaseString baseString_twitter() {
        return new OAuthBaseString()
                .httpMethod("POST")
                .baseUri("https://api.twitter.com/1/statuses/update.json")
                .entityParameter(
                        "status",
                        "Hello Ladies + Gentlemen, a signed OAuth request!")
                .entityParameter("include_entities", "true")
                .protocolParameter("oauth_consumer_key",
                                   "xvz1evFS4wEEPTGEFPHBog")
                .protocolParameter("oauth_nonce",
                                   "kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg")
                .protocolParameter("oauth_signature_method", "HMAC-SHA1")
                .protocolParameter("oauth_timestamp", "1318622958")
                .protocolParameter(
                        "oauth_token",
                        "370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb")
                .protocolParameter("oauth_version", "1.0");
    }

    static OAuthBaseString baseString_nouncer() {
        return new OAuthBaseString()
                .httpMethod("GET")
                .baseUri("http://photos.example.net/photos")
                .protocolParameter("oauth_consumer_key", "dpf43f3p2l4k3l03")
                .protocolParameter("oauth_token", "nnch734d00sl2jdk")
                .protocolParameter("oauth_nonce", "kllo9940pd9333jh")
                .protocolParameter("oauth_timestamp", "1191242096")
                .protocolParameter("oauth_signature_method", "HMAC-SHA1")
                .protocolParameter("oauth_version", "1.0")
                .queryParameter("size", "original")
                .queryParameter("file", "vacation.jpg");
    }

    static OAuthBaseString baseString_rfc5849_3_4_1_1() {
        return new OAuthBaseString()
                .httpMethod("POST")
                .baseUri("http://example.com/request")
                .queryParameter("b5", "=%3D")
                .queryParameter("a3", "a")
                .queryParameter("c@", "")
                .queryParameter("a2", "r b")
                .protocolParameter("oauth_consumer_key", "9djdj82h48djs9d2")
                .protocolParameter("oauth_token", "kkk9d7dh3k39sjv7")
                .protocolParameter("oauth_signature_method", "HMAC-SHA1")
                .protocolParameter("oauth_timestamp", "137131201")
                .protocolParameter("oauth_nonce", "7d8f3e4a")
                .entityParameter("c2", "")
                .entityParameter("a3", "2 q");
    }

    @DataProvider
    static Object[][] baseStrings() {
        return new Object[][]{
            {baseString_twitter()},
            {baseString_nouncer()},
            {baseString_rfc5849_3_4_1_1()}
        };
    }

    /**
     * Tests against twitter example.
     *
     * @see
     * <a href="https://dev.twitter.com/oauth/overview/creating-signatures">Creating
     * a signature</a>
     */
    @Test
    public void twitterExample() {
        final OAuthBaseString baseString = baseString_twitter();
        final String expected
                = "POST"
                  + "&https%3A%2F%2Fapi.twitter.com%2F1%2Fstatuses%2Fupdate.json"
                  + "&include_entities%3Dtrue"
                  + "%26oauth_consumer_key%3Dxvz1evFS4wEEPTGEFPHBog"
                  + "%26oauth_nonce%3DkYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg"
                  + "%26oauth_signature_method%3DHMAC-SHA1"
                  + "%26oauth_timestamp%3D1318622958"
                  + "%26oauth_token%3D370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb"
                  + "%26oauth_version%3D1.0"
                  + "%26status%3DHello%2520Ladies%2520%252B%2520Gentlemen%252C%2520a%2520signed%2520OAuth%2520request%2521";
        final String actual = baseString.get();
        assertEquals(actual, expected);
    }

    /**
     * Tests against nouncer example.
     *
     * @see <a href="http://nouncer.com/oauth/authentication.html">OAuth 1.0
     * Authentication Sandbox</a>
     */
    @Test(enabled = true)
    public void nouncerExample() {
        final OAuthBaseString baseString = baseString_nouncer();
        final String expected
                = "GET"
                  + "&http%3A%2F%2Fphotos.example.net%2Fphotos"
                  + "&file%3Dvacation.jpg"
                  + "%26oauth_consumer_key%3Ddpf43f3p2l4k3l03"
                  + "%26oauth_nonce%3Dkllo9940pd9333jh"
                  + "%26oauth_signature_method%3DHMAC-SHA1"
                  + "%26oauth_timestamp%3D1191242096"
                  + "%26oauth_token%3Dnnch734d00sl2jdk"
                  + "%26oauth_version%3D1.0"
                  + "%26size%3Doriginal";
        final String actual = baseString.get();
        assertEquals(actual, expected);
    }

    /**
     * Test against the example from {@code 3.4.1.1.  String Construction}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc5849#section-3.4.1">3.4.1.
     * Signature Base String</a>
     */
    @Test(enabled = true)
    public void rfc5849_3_4_1_1() {
        final OAuthBaseString baseString = baseString_rfc5849_3_4_1_1();
        final String actual = baseString.get();
        final String expected
                = "POST"
                  + "&http%3A%2F%2Fexample.com%2Frequest"
                  + "&a2%3Dr%2520b"
                  + "%26a3%3D2%2520q"
                  + "%26a3%3Da"
                  + "%26b5%3D%253D%25253D"
                  + "%26c%2540%3D"
                  + "%26c2%3D"
                  + "%26oauth_consumer_key%3D9djdj82h48djs9d2"
                  + "%26oauth_nonce%3D7d8f3e4a"
                  + "%26oauth_signature_method%3DHMAC-SHA1"
                  + "%26oauth_timestamp%3D137131201"
                  + "%26oauth_token%3Dkkk9d7dh3k39sjv7";
        assertEquals(actual, expected);
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void buildWithoutHttpMethod() {
        new OAuthBaseString()
                .baseUri("baseUri")
                .oauthNonce("oauthNonce")
                .oauthTimestamp("oauthTimestamp")
                .get();
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void buildWithoutBaseUri() {
        new OAuthBaseString()
                .httpMethod("httpMethod")
                .oauthNonce("oauthNonce")
                .oauthTimestamp("oauthTimestamp")
                .get();
    }

    @Test(expectedExceptions = {IllegalStateException.class},enabled = false)
    @Deprecated
    public void buildWithoutOauthNonce() {
        new OAuthBaseString()
                .httpMethod("httpMethod")
                .baseUri("baseUri")
                .oauthTimestamp("oauthTimestamp")
                .get();
    }

    @Test(expectedExceptions = {IllegalStateException.class},enabled = false)
    @Deprecated
    public void buildWithoutOauthTimestamp() {
        new OAuthBaseString()
                .httpMethod("httpMethod")
                .baseUri("baseUri")
                .oauthNonce("oauthNonce")
                .get();
    }
}
