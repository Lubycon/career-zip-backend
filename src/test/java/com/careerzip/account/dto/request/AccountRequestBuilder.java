package com.careerzip.account.dto.request;

import com.careerzip.account.dto.request.AccountRequest;

public class AccountRequestBuilder {

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String provider;
        private String oAuthId;
        private String name;
        private String email;
        private String avatarUrl;

        public Builder provider(String provider) {
            this.provider = provider;
            return this;
        }

        public Builder oAuthId(String oAuthId) {
            this.oAuthId = oAuthId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder avatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public AccountRequest build() {
            return new AccountRequest(provider, oAuthId, name, email, avatarUrl);
        }
    }
}
