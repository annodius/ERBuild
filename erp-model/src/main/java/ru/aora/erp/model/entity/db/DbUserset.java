package ru.aora.erp.model.entity.db;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "SETTINGS")
public class DbUserset implements Serializable, Deactivatable {

    private static final long serialVersionUID = 6765412988706551918L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    private String id;

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

    public DbUserset setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getUserThemeColor() {
        return userThemeColor;
    }

    public DbUserset setUserThemeColor(Integer userThemeColor) {
        this.userThemeColor = userThemeColor;
        return this;
    }

    public Integer getUserThemePattern() {
        return userThemePattern;
    }

    public DbUserset setUserThemePattern(Integer userThemePattern) {
        this.userThemePattern = userThemePattern;
        return this;
    }

        public BigDecimal getUserThemeZoom() { return userThemeZoom; }

        public DbUserset setUserThemeZoom(BigDecimal userThemeZoom) {
            this.userThemeZoom = userThemeZoom;
            return this;
    }

    public LocalDateTime getDeactivationDate() { return deactivationDate; }

    public DbUserset setDeactivationDate(LocalDateTime deactivationDate) {
        this.deactivationDate = deactivationDate;
        return this;
    }

    public Integer getActiveStatus() {return activeStatus; }

    public DbUserset setActiveStatus(Integer deactivated) {
        this.activeStatus = deactivated;
        return this;
    }

    @Override
    public String toString() {
        return "DbUserset{" +
                "id='" + id + '\'' +
                ", userThemeColor='" + userThemeColor + '\'' +
                ", userThemePattern=" + userThemePattern +
                ", userThemeZoom='" + userThemeZoom + '\'' +
                ", deactivationDate=" + deactivationDate +
                ", deactivated=" + activeStatus +
                '}';
    }
}
