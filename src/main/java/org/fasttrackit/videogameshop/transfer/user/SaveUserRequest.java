package org.fasttrackit.videogameshop.transfer.user;

import org.fasttrackit.videogameshop.domain.UserRole;

import javax.validation.constraints.NotNull;

public class SaveUserRequest {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private UserRole role;


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

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserRole getRole() {
        return role;
    }
}
