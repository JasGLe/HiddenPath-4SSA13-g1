
package com.hiddenpath.guideservice.dto;

public class GuideResponse {

    private Long id;
    private Long userId;
    private String profil;

    public GuideResponse() {
    }

    public GuideResponse(Long id, Long userId, String profil) {
        this.id = id;
        this.userId = userId;
        this.profil = profil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }
}
