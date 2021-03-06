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
import java.security.NoSuchAlgorithmException;
import java.util.function.Function;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Tests {@link OAuthSignerHmacSha1Jca}.
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class OAuthSignatureHmacSha1JcaTest
        extends OAuthSignatureHmacSha1Test<OAuthSignatureHmacSha1Jca> {

    private static final Logger logger = getLogger(lookup().lookupClass());

    static <R> R applySecretKey(final Function<SecretKey, R> function)
            throws NoSuchAlgorithmException {
        final KeyGenerator keyGenerator = KeyGenerator.getInstance(
                OAuthSignatureHmacSha1Jca.ALGORITHM);
        final SecretKey secretKey = keyGenerator.generateKey();
        return function.apply(secretKey);
    }

    /**
     * Creates a new instance.
     */
    public OAuthSignatureHmacSha1JcaTest() {
        super(OAuthSignatureHmacSha1Jca.class);
    }

}
