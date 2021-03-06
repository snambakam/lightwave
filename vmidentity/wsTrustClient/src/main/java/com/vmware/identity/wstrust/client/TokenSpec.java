/*
 *  Copyright (c) 2012-2015 VMware, Inc.  All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *   use this file except in compliance with the License.  You may obtain a copy
 *   of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS, without
 *   warranties or conditions of any kind, EITHER EXPRESS OR IMPLIED.  See the
 *   License for the specific language governing permissions and limitations
 *   under the License.
 */
package com.vmware.identity.wstrust.client;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.vmware.vim.sso.client.Advice;
import com.vmware.vim.sso.client.SamlToken;

/**
 * This class specifies token properties required when exchanging data with the
 * STS service - acquiring token for example. Instances of {@link TokenSpec} are
 * created using the {@link Builder}. {@link TokenSpec} is immutable.
 */
public final class TokenSpec {

    /**
     * Contains the token lifetime period in seconds
     */
    private final long tokenLifetimeSec;

    /**
     * Contains the delegation properties for the token specification
     */
    private final DelegationSpec delegationSpec;

    /**
     * True if the token should be renewable.
     */
    private final boolean isRenewable;

    /**
     * Defines the confirmation type for the token requested using this spec
     */
    private final Confirmation confirmation;

    /**
     * Contains the audience restriction list
     */
    private final Set<String> audienceRestriction;

    /**
     * Contains advice that should be added to the token
     */
    private final List<Advice> advice;

    /**
     * Contains customized claim list that should be added to the token
     */
    private final List<String> claims;

    /**
     * Creates a token specification.
     *
     * @param tokenLifetimeSec
     *            token lifetime in seconds from now; positive number is
     *            required
     * @param delegationSpec
     *            The delegation specification. If <code>null</code> no
     *            delegation will take place.
     * @param isRenewable
     *            True if the token should be renewable, false otherwise
     * @param confirmation
     *            define the confirmation type for the token requested using
     *            this spec.
     * @param audienceRestriction
     *            Set containing audience restriction list. Each element should
     *            be in form of a URI. Cannot be <code>null</code>
     * @param advice
     *            Advice list that should be included in the SAML token. Cannot
     *            be <code>null</code>
     */
    private TokenSpec(long tokenLifetimeSec, DelegationSpec delegationSpec, boolean isRenewable,
            Confirmation confirmation, Set<String> audienceRestriction, List<Advice> advice, List<String> claims) {

        ValidateUtil.validatePositiveNumber(tokenLifetimeSec, "token lifetime");
        // ValidateUtil.validateNotNull(delegationSpec,
        // "Token delegation spec");
        ValidateUtil.validateNotNull(confirmation, "Confirmation type");
        ValidateUtil.validateNotNull(audienceRestriction, "Audience restriction list");
        ValidateUtil.validateNotNull(advice, "Advice");
        ValidateUtil.validateNotNull(claims, "Claims");

        this.tokenLifetimeSec = tokenLifetimeSec;
        this.delegationSpec = delegationSpec;
        this.isRenewable = isRenewable;
        this.confirmation = confirmation;
        this.audienceRestriction = audienceRestriction;
        this.advice = advice;
        this.claims = claims;
    }

    /**
     * Token lifetime in seconds. It is the interval between token start date
     * and expiration date.
     *
     * @return a positive number for the token lifetime
     */
    public long getTokenLifetime() {
        return tokenLifetimeSec;
    }

    /**
     * @return the DelegationSpec. Can be <code>null</code>
     */
    public DelegationSpec getDelegationSpec() {
        return delegationSpec;
    }

    /**
     * @return true if the token should be renewable, false otherwise
     */
    public boolean isRenewable() {
        return isRenewable;
    }

    /**
     * @return the requested confirmation type
     */
    public Confirmation getConfirmation() {
        return confirmation;
    }

    /**
     * @return audience restriction list. Cannot be <code>null</code>
     */
    public Set<String> getAudienceRestriction() {
        return audienceRestriction;
    }

    /**
     * @return advice that should be added to the token. Cannot be
     *         <code>null</code>
     */
    public List<Advice> getAdvice() {
        return advice;
    }

    /**
     * @return claims that should be added to the token. Cannot be
     *         <code>null</code>
     */
    public List<String> getClaims() {
        return claims;
    }

    // Generated by Eclipse. Doesn't contain private data.
    @Override
    public String toString() {
        String claimsAsString = "";
        for (String claim : claims) {
            claimsAsString += "(" + claim + ")";
        }
        return "TokenSpec [tokenLifetimeSec=" + tokenLifetimeSec + ", delegationSpec=" + delegationSpec
                + ", isRenewable=" + isRenewable + ", confirmation=" + confirmation + ", audienceRestriction="
                + audienceRestriction + ", advice=" + advice + ", claims=" + claimsAsString + "]";
    }

    /**
     * Contains the delegation properties
     */
    public static final class DelegationSpec {

        /**
         * True if the token should be delegable.
         */
        private final boolean isDelegable;

        /**
         * If the token should be delegated, specifies the identity of the
         * delegate.
         */
        private final String delegateTo;

        /**
         * Token that will be delegated to the requester.
         */
        private final SamlToken actAsToken;

        /**
         * Creates a delegation specification. It does not ask for changes in
         * the delegation chain.
         *
         * @param isDelegable
         *            True if the token should be delegable.
         */
        public DelegationSpec(boolean isDelegable) {
            this.isDelegable = isDelegable;
            this.delegateTo = null;
            this.actAsToken = null;
            assert invariantRespected();
        }

        /**
         * Creates a delegation specification. The expected format for
         * <code>delegateTo</code> parameter value is as follows:
         * <ul>
         * <li>UPN formatted string for remote users</li>
         * <li>just the user name for local SSO users</li>
         * </ul>
         * *
         *
         * @param isDelegable
         *            True if the token should be delegable.
         * @param delegateTo
         *            If the token should be delegated, specifies the identity
         *            of the delegate otherwise <code>null</code> should be
         *            passed
         */
        public DelegationSpec(boolean isDelegable, String delegateTo) {
            this.isDelegable = isDelegable;
            this.delegateTo = delegateTo;
            this.actAsToken = null;
            assert invariantRespected();
        }

        /**
         * Creates a delegation specification. It asks for delegation of the
         * token, passed as <code>actAsToken</code> parameter, to the requester.
         *
         * @param actAsToken
         *            Token to be delegated, required
         * @param isDelegable
         *            True if the token should be delegable.
         */
        public DelegationSpec(SamlToken actAsToken, boolean isDelegable) {
            ValidateUtil.validateNotNull(actAsToken, "ActAs token");
            this.isDelegable = isDelegable;
            this.delegateTo = null;
            this.actAsToken = actAsToken;
            assert invariantRespected();
        }

        /**
         * @return the isDelegable
         */
        public boolean isDelegable() {
            return isDelegable;
        }

        /**
         * @return the delegateTo
         */
        public String getDelegateTo() {
            return delegateTo;
        }

        /**
         * @return the act as token
         */
        public SamlToken getActAsToken() {
            return actAsToken;
        }

        private boolean invariantRespected() {
            return delegateTo == null || actAsToken == null;
        }

        // Generated by Eclipse. Doesn't contain private data.
        @Override
        public String toString() {
            return "DelegationSpec [isDelegable=" + isDelegable + ", delegateTo=" + delegateTo + "]";
        }
    }

    /**
     * This enum is used to define the confirmation type for the token requested
     * using this spec
     */
    public static enum Confirmation {
        /**
         * Default confirmation will request a token which confirmation type is
         * decided based on the configuration in
         * {@link SecurityTokenServiceConfig}
         */
        DEFAULT,

        /**
         * None confirmation will override the configuration in
         * {@link SecurityTokenServiceConfig} and will request a bearer kind of
         * token
         */
        NONE
    }

    /**
     * Constructs instances of TokenSpec
     */
    public static class Builder {

        private final long tokenLifetime;
        private DelegationSpec delegationSpec;
        private boolean isRenewable;
        private Confirmation confirmation = Confirmation.DEFAULT;
        private Set<String> audienceRestriction = Collections.emptySet();
        private List<Advice> advice = Collections.emptyList();
        private List<String> claims = Collections.emptyList();

        /**
         * Constructs {@link TokenSpec} objects
         *
         * @param tokenLifetime
         *            token lifetime period in seconds from now; positive number
         *            is required
         */
        public Builder(long tokenLifetime) {
            this.tokenLifetime = tokenLifetime;
        }

        /**
         * Configures the {@link DelegationSpec} that will be included in the
         * {@link TokenSpec}
         *
         * @param delegationSpec
         *            cannot be <code>null</code>
         * @return
         */
        public Builder delegationSpec(DelegationSpec delegationSpec) {
            this.delegationSpec = delegationSpec;

            return this;
        }

        /**
         * Configures the renewability of the tokens created by the
         * {@link TokenSpec}
         *
         * @param isRenewable
         * @return
         */
        public Builder renewable(boolean isRenewable) {
            this.isRenewable = isRenewable;

            return this;
        }

        /**
         * Configures the {@link Confirmation} type
         *
         * @param confirmation
         *            cannot be <code>null</code>
         * @return
         */
        public Builder confirmation(Confirmation confirmation) {
            this.confirmation = confirmation;
            return this;
        }

        /**
         * Sets audience restrictions over the {@link TokenSpec} created from
         * this builder
         *
         * @param audienceRestriction
         *            cannot be <code>null</code>
         * @return
         */
        public Builder audienceRestriction(Set<String> audienceRestriction) {
            this.audienceRestriction = Collections.unmodifiableSet(audienceRestriction);
            return this;
        }

        /**
         * Sets advice list
         *
         * @param advice
         *            cannot be <code>null</code>
         * @return
         */
        public Builder advice(List<Advice> advice) {
            this.advice = Collections.unmodifiableList(advice);
            return this;
        }

        /**
         * Sets claim list
         *
         * @param claims
         *            cannot be <code>null</code>
         * @return
         */
        public Builder claims(List<String> claims) {
            this.claims = Collections.unmodifiableList(claims);
            return this;
        }

        /**
         * @return immutable instance of {@link TokenSpec} using the
         *         configuration of the builder
         */
        public TokenSpec createTokenSpec() {
            return new TokenSpec(tokenLifetime, delegationSpec, isRenewable, confirmation, audienceRestriction, advice, claims);
        }
    }
}
