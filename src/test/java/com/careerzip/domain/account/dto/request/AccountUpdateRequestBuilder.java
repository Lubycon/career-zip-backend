package com.careerzip.domain.account.dto.request;

public class AccountUpdateRequestBuilder {

    private String name;
    private String email;

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String email;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public AccountUpdateRequest build() {
            return new AccountUpdateRequest(name, email);
        }
    }
}
