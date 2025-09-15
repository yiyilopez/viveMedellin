package co.medellin.eventos.dto;

import java.time.LocalDateTime;

public class EventSummaryDto {
    private Long id;
    private String title;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private String locationText;
    private String imageUrl;

    public EventSummaryDto() {}
    public EventSummaryDto(Long id, String title, LocalDateTime startsAt, LocalDateTime endsAt, String locationText, String imageUrl) {
        this.id = id;
        this.title = title;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.locationText = locationText;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public LocalDateTime getStartsAt() { return startsAt; }
    public void setStartsAt(LocalDateTime startsAt) { this.startsAt = startsAt; }
    public LocalDateTime getEndsAt() { return endsAt; }
    public void setEndsAt(LocalDateTime endsAt) { this.endsAt = endsAt; }
    public String getLocationText() { return locationText; }
    public void setLocationText(String locationText) { this.locationText = locationText; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    // redundant getters removed
}
