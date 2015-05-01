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


import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class BaseStringBuilderTest {


    /**
     * Tests against twitter example.
     *
     * @throws Exception if an error occurs.
     *
     * @see
     * <a href="https://dev.twitter.com/oauth/overview/creating-signatures">Creating
     * a signature</a>
     */
    @Test
    public void twitterExample() throws Exception {

        final BaseStringBuilder builder = new BaseStringBuilder();

        builder.httpMethod("POST");
        builder.baseUri("https://api.twitter.com/1/statuses/update.json");
        builder.requestParameter(
            "status", "Hello Ladies + Gentlemen, a signed OAuth request!");
        builder.requestParameter("include_entities", "true");
        builder.protocolParameter(
            "oauth_consumer_key", "xvz1evFS4wEEPTGEFPHBog");
        builder.protocolParameter(
            "oauth_nonce", "kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg");
        builder.protocolParameter("oauth_signature_method", "HMAC-SHA1");
        builder.protocolParameter("oauth_timestamp", "1318622958");
        builder.protocolParameter(
            "oauth_token",
            "370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb");
        builder.protocolParameter("oauth_version", "1.0");

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

        final String actual = builder.build();

        assertEquals(actual, expected);
    }


    /**
     * Tests against nouncer example.
     *
     * @throws Exception if an error occurs.
     *
     * @see <a href="http://nouncer.com/oauth/authentication.html">OAuth 1.0
     * Authentication Sandbox</a>
     */
    @Test(enabled = true)
    public void nouncerExample() throws Exception {

        final BaseStringBuilder builder = new BaseStringBuilder();

        builder.httpMethod("GET");
        builder.baseUri("http://photos.example.net/photos");

        builder.protocolParameter("oauth_consumer_key", "dpf43f3p2l4k3l03");
        builder.protocolParameter("oauth_token", "nnch734d00sl2jdk");
        builder.protocolParameter("oauth_nonce", "kllo9940pd9333jh");
        builder.protocolParameter("oauth_timestamp", "1191242096");
        builder.protocolParameter("oauth_signature_method", "HMAC-SHA1");
        builder.protocolParameter("oauth_version", "1.0");
        builder.requestParameter("size", "original");
        builder.requestParameter("file", "vacation.jpg");

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

        final String actual = builder.build();

        assertEquals(actual, expected);
    }


    @Test(enabled = false, description = "disabled -> no httpMethod & baseUri")
    public void rfc5849_3_5_1() throws Exception {


        final String documented1
            = "a2=r%20b&a3=2%20q&a3=a&b5=%3D%253D&c%40=&c2=&"
              + "oauth_consumer_key=9djdj82h48djs9d2&"
              + "oauth_nonce=7d8f3e4a&"
              + "oauth_signature_method=HMAC-SHA1&"
              + "oauth_timestamp=137131201&"
              + "oauth_token=kkk9d7dh3k39sjv7";

        final String documented = "POST&http%3A%2F%2Fexample.com%2Frequest&a2%3Dr%2520b%26a3%3D2%2520q%26a3%3Da%26b5%3D%253D%25253D%26c%2540%3D%26c2%3D%26oauth_consumer_key%3D9djdj82h48djs9d2%26oauth_nonce%3D7d8f3e4a%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D137131201%26oauth_token%3Dkkk9d7dh3k39sjv7";

        final String expected = documented;

        final BaseStringBuilder builder = new BaseStringBuilder()
            .httpMethod("POST")
            .baseUri("http://example.com/request")
            .requestParameter("b5", "=%3D")
            .requestParameter("a3", "a")
            .requestParameter("c@", "")
            .requestParameter("a2", "r b")
            .requestParameter("oauth_consumer_key", "9djdj82h48djs9d2")
            .requestParameter("oauth_token", "kkk9d7dh3k39sjv7")
            .requestParameter("oauth_signature_method", "HMAC-SHA1")
            .requestParameter("oauth_timestamp", "137131201")
            .requestParameter("oauth_nonce", "7d8f3e4a")
            .requestParameter("c2", "")
            .requestParameter("a3", "2 q");

        final String actual = builder.build();

        assertEquals(actual, expected);
    }


}

