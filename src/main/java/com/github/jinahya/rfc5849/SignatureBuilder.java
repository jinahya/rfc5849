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
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public abstract class SignatureBuilder implements Builder<String> {

    public SignatureBuilder(final String signatureMethod) {
        super();
        if (signatureMethod == null) {
            throw new NullPointerException("null signatureMethod");
        }
        this.signatureMethod = signatureMethod;
    }

    protected String getPrebuilt() {
        return prebuilt;
    }

    protected void setPrebuilt(final String prebuilt) {
        this.prebuilt = prebuilt;
    }

    public SignatureBuilder prebuilt(final String prebuilt) {
        setPrebuilt(prebuilt);
        return this;
    }

    /**
     * Returns signature method.
     *
     * @return signature method.
     */
    public String getSignatureMethod() {
        return signatureMethod;
    }

    public BaseStringBuilder getBaseStringBuilder() {
        return baseStringBuilder;
    }

    public void setBaseStringBuilder(
            final BaseStringBuilder baseStringBuilder) {
        this.baseStringBuilder = baseStringBuilder;
        if (this.baseStringBuilder != null) {
            this.baseStringBuilder.setOauthSignatureMethod(signatureMethod);
        }
    }

    public SignatureBuilder baseStringBuilder(
            final BaseStringBuilder baseStringBuilder) {
        setBaseStringBuilder(baseStringBuilder);
        return this;
    }

    private String prebuilt;

    protected final String signatureMethod;

    private BaseStringBuilder baseStringBuilder;
}
