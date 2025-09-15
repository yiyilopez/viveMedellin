package co.medellin.eventos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime startsAt;

    @Column(nullable = false)
    private LocalDateTime endsAt;

    @Column(nullable = false, length = 255)
    private String locationText;

    @Column(length = 255)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructors
    public Event() {}
    public Event(Long id, String title, String description, LocalDateTime startsAt, LocalDateTime endsAt,
                 String locationText, String imageUrl, User createdBy, Boolean isActive,
                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.locationText = locationText;
        this.imageUrl = imageUrl;
        this.createdBy = createdBy;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getStartsAt() { return startsAt; }
    public void setStartsAt(LocalDateTime startsAt) { this.startsAt = startsAt; }
    public LocalDateTime getEndsAt() { return endsAt; }
    public void setEndsAt(LocalDateTime endsAt) { this.endsAt = endsAt; }
    public String getLocationText() { return locationText; }
    public void setLocationText(String locationText) { this.locationText = locationText; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Simple builder
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final Event e = new Event();
        public Builder id(Long id) { e.id = id; return this; }
        public Builder title(String title) { e.title = title; return this; }
        public Builder description(String description) { e.description = description; return this; }
        public Builder startsAt(LocalDateTime startsAt) { e.startsAt = startsAt; return this; }
        public Builder endsAt(LocalDateTime endsAt) { e.endsAt = endsAt; return this; }
        public Builder locationText(String locationText) { e.locationText = locationText; return this; }
        public Builder imageUrl(String imageUrl) { e.imageUrl = imageUrl; return this; }
        public Builder createdBy(User createdBy) { e.createdBy = createdBy; return this; }
        public Builder isActive(Boolean isActive) { e.isActive = isActive; return this; }
        public Builder createdAt(LocalDateTime createdAt) { e.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { e.updatedAt = updatedAt; return this; }
        public Event build() { return e; }
    }
}
