package ru.aora.erp.model.entity.db;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Counteragent")
public class DbCounteragent implements Serializable,Deactivatable {

    private static final long serialVersionUID = 8481999979205249004L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition="uniqueidentifier")
    public String id;


    @Column(name = "parent_id", columnDefinition="uniqueidentifier")
    public String oldId;

    @Column(name = "counteragent_name")
    private String counteragentName;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "first_name")
    private String directorFirstName;

    @Column(name = "surname")
    private String directorSurname;

    @Column(name = "patronymic")
    private String directorPatronymic;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "mail")
    private String mail;

    @Column(name = "address")
    private String address;

    @Column(name = "inn")
    private BigDecimal inn;

    @Column(name = "deactivation_date")
    private LocalDateTime deactivationDate;

    @Column(name = "deactivated")
    private Integer activeStatus;

    @PrePersist
    private void prePersist(){
        if(activeStatus == null){
            activeStatus = ACTIVE_ENTITY_FLAG;
        }
    }

    public String getId() {
        return id;
    }

    public DbCounteragent setId(String id) {
        this.id = id;
        return this;
    }

    public String getOldId() {
        return oldId;
    }

    public DbCounteragent setOldId(String oldId) {
        this.oldId = oldId;
        return this;
    }

    public String getCounteragentName() {
        return counteragentName;
    }

    public DbCounteragent setCounteragentName(String counteragentName) {
        this.counteragentName = counteragentName;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public DbCounteragent setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getDirectorFirstName() {
        return directorFirstName;
    }

    public DbCounteragent setDirectorFirstName(String directorFirstName) {
        this.directorFirstName = directorFirstName;
        return this;
    }

    public String getDirectorSurname() {
        return directorSurname;
    }

    public DbCounteragent setDirectorSurname(String directorSurname) {
        this.directorSurname = directorSurname;
        return this;
    }

    public String getDirectorPatronymic() {
        return directorPatronymic;
    }

    public DbCounteragent setDirectorPatronymic(String directorPatronymic) {
        this.directorPatronymic = directorPatronymic;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public DbCounteragent setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public DbCounteragent setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public DbCounteragent setAddress(String address) {
        this.address = address;
        return this;
    }

    public BigDecimal getInn() {
        return inn;
    }

    public DbCounteragent setInn(BigDecimal inn) {
        this.inn = inn;
        return this;
    }

    public LocalDateTime getDeactivationDate() {
        return deactivationDate;
    }

    public DbCounteragent setDeactivationDate(LocalDateTime deactivationDate) {
        this.deactivationDate = deactivationDate;
        return this;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public DbCounteragent setActiveStatus(Integer deactivated) {
        this.activeStatus = deactivated;
        return this;
    }

    @Override
    public String toString() {
        return "DbCounteragent{" +
                "id='" + id + '\'' +
                ", oldId='" + oldId + '\'' +
                ", counteragentName='" + counteragentName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", directorFirstName='" + directorFirstName + '\'' +
                ", directorSurname='" + directorSurname + '\'' +
                ", directorPatronymic='" + directorPatronymic + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mail='" + mail + '\'' +
                ", address='" + address + '\'' +
                ", inn='" + inn + '\'' +
                ", deactivationDate=" + deactivationDate +
                ", deactivated=" + activeStatus +
                '}';
    }

}



