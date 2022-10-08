package ru.aora.erp.model.entity.db;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "settings")
public class DbUserparam implements Serializable, Deactivatable {

    private static final long serialVersionUID = 6765412988706551918L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_settings", columnDefinition = "uniqueidentifier")
    private String id;

    @Column(name = "user_id", columnDefinition = "uniqueidentifier")
    private String userId;

    @Column(name = "user_theme_color")
    private Integer userThemeColor;

    @Column(name = "user_theme_pattern")
    private Integer userThemePattern;

    @Column(name = "user_theme_zoom")
    private BigDecimal userThemeZoom;

    @Column(name = "deactivation_date")
    private LocalDateTime deactivationDate;

    @Column(name = "deactivated")
    private Integer activeStatus;

    @PrePersist
    private void prePersist() {
        if (activeStatus == null) {
            activeStatus = ACTIVE_ENTITY_FLAG;
        }
    }

    public String getId() {
        return id;
    }

    public DbUserparam setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public DbUserparam setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Integer getUserThemeColor() {
        return userThemeColor;
    }

    public DbUserparam setUserThemeColor(Integer userThemeColor) {
        this.userThemeColor = userThemeColor;
        return this;
    }

    public Integer getUserThemePattern() {
        return userThemePattern;
    }

    public DbUserparam setUserThemePattern(Integer userThemePattern) {
        this.userThemePattern = userThemePattern;
        return this;
    }

        public BigDecimal getUserThemeZoom() { return userThemeZoom; }

        public DbUserparam setUserThemeZoom(BigDecimal userThemeZoom) {
            this.userThemeZoom = userThemeZoom;
            return this;
    }

    public LocalDateTime getDeactivationDate() { return deactivationDate; }

    public DbUserparam setDeactivationDate(LocalDateTime deactivationDate) {
        this.deactivationDate = deactivationDate;
        return this;
    }

    public Integer getActiveStatus() {return activeStatus; }

    public DbUserparam setActiveStatus(Integer deactivated) {
        this.activeStatus = deactivated;
        return this;
    }

    @Override
    public String toString() {
        return "dbUserparam{" +
                "id='" + id + '\'' +
                "userId='" + userId + '\'' +
                ", userThemeColor='" + userThemeColor + '\'' +
                ", userThemePattern='" + userThemePattern +'\'' +
                ", userThemeZoom='" + userThemeZoom + '\'' +
                ", deactivationDate=" + deactivationDate +
                ", deactivated=" + activeStatus +
                '}';
    }
}
