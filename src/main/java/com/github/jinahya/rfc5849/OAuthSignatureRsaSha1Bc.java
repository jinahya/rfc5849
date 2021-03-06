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

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.signers.RSADigestSigner;

/**
 * A signature builder uses Bouncy Castle.
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 * @see <a href="https://www.bouncycastle.org/java.html">The Legion of the
 * Bouncy Castle</a>
 */
public class OAuthSignatureRsaSha1Bc
        extends OAuthSignatureRsaSha1<CipherParameters> {

    @Override
    byte[] get(final CipherParameters initParam, final byte[] baseBytes)
            throws Exception {
        final Signer signer = new RSADigestSigner(new SHA1Digest());
        signer.init(true, initParam);
        signer.update(baseBytes, 0, baseBytes.length);
        return signer.generateSignature();
    }

    // -------------------------------------------------------------- baseString
    @Override
    public OAuthSignatureRsaSha1Bc baseString(
            final OAuthBaseString baseString) {
        return (OAuthSignatureRsaSha1Bc) super.baseString(baseString);
    }

    @Override
    public OAuthSignatureRsaSha1Bc initParam(final CipherParameters initParam) {
        return (OAuthSignatureRsaSha1Bc) super.initParam(initParam);
    }
}
