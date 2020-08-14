package org.fasttrackit.videogameshop.transfer.user;

import org.fasttrackit.videogameshop.domain.UserRole;

import javax.validation.constraints.NotNull;
import java.util.prefs.Preferences;

public class SaveUserRequest {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "SaveUserRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
