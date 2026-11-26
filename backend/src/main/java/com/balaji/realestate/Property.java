package com.balaji.realestate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.Instant;

@Entity
@Table(name = "properties")
public class Property {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank @Size(max = 160) private String title;
    @NotBlank @Size(max = 40) private String type = "Flat";
    @NotBlank @Size(max = 10) private String listingType = "SALE";
    @NotBlank @Size(max = 180) private String location;
    @PositiveOrZero private long price;
    @Positive private int areaSqft;
    @PositiveOrZero private int bedrooms;
    @PositiveOrZero private int bathrooms;
    @Column(length = 3000) private String description;
    @Column(length = 1000) private String imageUrl;
    @NotBlank @Size(max = 20) private String status = "AVAILABLE";
    private boolean featured;
    private Instant createdAt = Instant.now();

    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getType(){return type;} public void setType(String v){type=v;}
    public String getListingType(){return listingType;} public void setListingType(String v){listingType=v;}
    public String getLocation(){return location;} public void setLocation(String v){location=v;}
    public long getPrice(){return price;} public void setPrice(long v){price=v;}
    public int getAreaSqft(){return areaSqft;} public void setAreaSqft(int v){areaSqft=v;}
    public int getBedrooms(){return bedrooms;} public void setBedrooms(int v){bedrooms=v;}
    public int getBathrooms(){return bathrooms;} public void setBathrooms(int v){bathrooms=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public boolean isFeatured(){return featured;} public void setFeatured(boolean v){featured=v;}
    public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant v){createdAt=v;}
}
