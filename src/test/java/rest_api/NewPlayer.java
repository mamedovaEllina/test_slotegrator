package rest_api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class NewPlayer {
    private String username;
    private String passwordChange;
    private String passwordRepeat;
    private String email;
    private String name;
    private String surname;
    private String currencyCode;


    @JsonCreator
    private NewPlayer(@JsonProperty("username") final String username,
                      @JsonProperty("password_change") final String passwordChange,
                      @JsonProperty("password_repeat") final String passwordRepeat,
                      @JsonProperty("email") final String email,
                      @JsonProperty("name") final String name,
                      @JsonProperty("surname") final String surname,
                      @JsonProperty("currency_code") final String currencyCode) {
        this.username = username;
        this.passwordChange = passwordChange;
        this.passwordRepeat = passwordRepeat;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.currencyCode = currencyCode;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordChange() {
        return passwordChange;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }


    public NewPlayer(final Builder builder) {
        this.username = builder.username;
        this.passwordChange = builder.passwordChange;
        this.passwordRepeat = builder.passwordRepeat;
        this.email = builder.email;
        this.name = builder.name;
        this.surname = builder.surname;
        this.currencyCode = builder.currencyCode;
    }    

    public static class Builder {
        private String username;
        private String passwordChange;
        private String passwordRepeat;
        private String email;
        private String name;
        private String surname;
        private String currencyCode;

        public NewPlayer build() {
            return new NewPlayer(this);
        }

        public NewPlayer.Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public NewPlayer.Builder setPasswordChange(String passwordChange) {
            this.passwordChange = passwordChange;
            return this;
        }

        public NewPlayer.Builder setPasswordRepeat(String passwordRepeat) {
            this.passwordRepeat = passwordRepeat;
            return this;
        }

        public NewPlayer.Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public NewPlayer.Builder setName(String name) {
            this.name = name;
            return this;
        }

        public NewPlayer.Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public NewPlayer.Builder setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        public Builder using(NewPlayer player) {
            this.passwordChange = player.passwordChange;
            this.passwordRepeat = player.passwordRepeat;
            this.email = player.email;
            this.name = player.name;
            this.surname = player.surname;
            this.currencyCode = player.currencyCode;
            return this;
        }
    }

}
