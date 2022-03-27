package rest_api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.assertj.core.api.SoftAssertions;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.within;

public class Player {
    private final String id;
    private final String countryId;
    private final String timezoneId;
    private final String username;
    private final String email;
    private final String name;
    private final String surname;
    private final String gender;
    private final String phoneNumber;
    private final String birthdate;
    private final String bonusesAllowed;
    private final String isVerified;

    @JsonCreator
    private Player(@JsonProperty("id") final String id,
                 @JsonProperty("country_id") final String countryId,
                 @JsonProperty("timezone_id") String timezoneId,
                 @JsonProperty("username") String username,
                 @JsonProperty("email") String email,
                 @JsonProperty("name") String name,
                 @JsonProperty("surname") String surname,
                 @JsonProperty("gender") String gender,
                 @JsonProperty("private") String phoneNumber,
                 @JsonProperty("birthdate") final String birthdate,
                 @JsonProperty("bonuses_allowed") final String bonusesAllowed,
                 @JsonProperty("is_verified") String isVerified) {
        this.id = id;
        this.countryId = countryId;
        this.timezoneId = timezoneId;
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.bonusesAllowed = bonusesAllowed;
        this.isVerified = isVerified;
    }

    /**
     * Class constructor.
     *
     * @param builder builder
     */
    public Player(final Builder builder) {
        this.id = builder.id;
        this.countryId = builder.countryId;
        this.timezoneId = builder.timezoneId;
        this.username = builder.username;
        this.email = builder.email;
        this.name = builder.name;
        this.surname = builder.surname;
        this.gender = builder.gender;
        this.phoneNumber = builder.phoneNumber;
        this.birthdate = builder.birthdate;
        this.bonusesAllowed = builder.bonusesAllowed;
        this.isVerified = builder.isVerified;
    }

    public String getId() {
        return id;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getTimezoneId() {
        return timezoneId;
    }

    public String getUsername() {
        return username;
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

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getBonusesAllowed() {
        return bonusesAllowed;
    }

    public String isVerified() {
        return isVerified;
    }

    public static class Builder {
        String id;
        String countryId;
        String timezoneId;
        String username;
        String email;
        String name;
        String surname;
        String gender;
        String phoneNumber;
        String birthdate;
        String bonusesAllowed;
        String isVerified;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setCountryId(String countryId) {
            this.countryId = countryId;
            return this;
        }

        public Builder setTimezoneId(String timezoneId) {
            this.timezoneId = timezoneId;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            phoneNumber = phoneNumber;
            return this;
        }

        public Builder setBirthdate(String birthdate) {
            this.birthdate = birthdate;
            return this;
        }

        public Builder setLon(String bonusesAllowed) {
            this.bonusesAllowed = bonusesAllowed;
            return this;
        }

        public Builder setIsVerified(String isVerified) {
            this.isVerified = isVerified;
            return this;
        }
        
        public Player build() {
            return new Player(this);
        }
    }

    public void partlyEquals(final Player expected) {
        final var softly = new SoftAssertions();
        softly.assertThat(this.getName()).isEqualTo(expected.getName());
        softly.assertThat(this.getSurname()).isEqualTo(expected.getSurname());
        softly.assertThat(this.getUsername()).isEqualTo(expected.getUsername());
        softly.assertThat(this.getEmail()).isEqualTo(expected.getEmail());
        softly.assertAll();
    }

    public void partlyEquals(final NewPlayer expected) {
        final var softly = new SoftAssertions();
        softly.assertThat(this.getName()).isEqualTo(expected.getName());
        softly.assertThat(this.getSurname()).isEqualTo(expected.getSurname());
        softly.assertThat(this.getUsername()).isEqualTo(expected.getUsername());
        softly.assertThat(this.getEmail()).isEqualTo(expected.getEmail());
        softly.assertAll();
    }
    

}
