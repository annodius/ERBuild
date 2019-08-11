package ru.aora.erp.model.entity.business;

import org.springframework.security.core.userdetails.UserDetails;
import ru.aora.erp.model.entity.IdAuthority;

import java.util.Collection;
import java.util.StringJoiner;

public class User implements UserDetails {

    private long id;
    private Collection<IdAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private String username;
    private String password;
    private String firstName;
    private String surname;
    private String patronymic;
    private String phoneNumber;
    private String mail;
    private String employeePosition;
    private boolean del;

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public User setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public User setPatronymic(String patronymic) {
        this.patronymic = patronymic;
        return this;
    }

    @Override
    public Collection<IdAuthority> getAuthorities() {
        return authorities;
    }

    public User setAuthorities(Collection<IdAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public User setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
        return this;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public User setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
        return this;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public User setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
        return this;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public User setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public User setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public User setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
        return this;
    }

    public boolean isDel() {
        return del;
    }

    public User setDel(boolean del) {
        this.del = del;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("authorities=" + authorities)
                .add("accountNonExpired=" + accountNonExpired)
                .add("accountNonLocked=" + accountNonLocked)
                .add("credentialsNonExpired=" + credentialsNonExpired)
                .add("enabled=" + enabled)
                .add("username='" + username + "'")
                .add("password='" + password + "'")
                .add("firstName='" + firstName + "'")
                .add("surname='" + surname + "'")
                .add("patronymic='" + patronymic + "'")
                .add("phoneNumber='" + phoneNumber + "'")
                .add("mail='" + mail + "'")
                .add("employeePosition='" + employeePosition + "'")
                .add("del=" + del)
                .toString();
    }
}
