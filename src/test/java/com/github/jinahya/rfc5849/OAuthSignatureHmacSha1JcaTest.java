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

/**
 * Tests {@link OAuthSignerHmacSha1Jca}.
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class OAuthSignatureHmacSha1JcaTest
        extends OAuthSignatureHmacSha1Test<OAuthSignatureHmacSha1Jca> {

    /**
     * Creates a new instance.
     */
    public OAuthSignatureHmacSha1JcaTest() {
        super(OAuthSignatureHmacSha1Jca.class);
    }
}