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

import static com.github.jinahya.rfc5849._Base64.encodeBase64ToString;

/**
 * An abstract class for signing request with {@value #SIGNATURE_METHOD}.
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 * @see <a href="https://tools.ietf.org/html/rfc5849#section-3.4.2">3.4.2.
 * HMAC-SHA1</a>
 */
public abstract class OAuthSignatureHmacSha1 extends OAuthSignaturePlaintext {

    /**
     * The signature method name whose value is {@value #SIGNATURE_METHOD}.
     */
    public static final String SIGNATURE_METHOD = "HMAC-SHA1";

    /**
     * Creates a new instance.
     */
    public OAuthSignatureHmacSha1() {
        super(SIGNATURE_METHOD);
    }

    // -------------------------------------------------------------------------
    @Override
    public String get() throws Exception {
        final OAuthBaseString baseString = baseString();
        if (baseString == null) {
            throw new IllegalStateException("no baseString set");
        }
        final String keyString = super.get(); // consumerSecret&tokenSecret
        final byte[] keyBytes = keyString.getBytes("ISO-8859-1");
        final byte[] baseBytes = baseString.get().getBytes("ISO-8859-1");
        final byte[] signature = get(keyBytes, baseBytes);
        return encodeBase64ToString(signature);
    }

    abstract byte[] get(byte[] keyBytes, byte[] baseBytes) throws Exception;
}