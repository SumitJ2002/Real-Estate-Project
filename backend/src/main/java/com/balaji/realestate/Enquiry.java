package com.balaji.realestate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.Instant;

@Entity
@Table(name = "enquiries")
public class Enquiry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank @Size(max = 100) private String name;
    @NotBlank @Size(max = 20) private String phone;
    @Email @Size(max = 160) private String email;
    @Column(length = 2000) private String message;
    private Long propertyId;
    private Instant createdAt = Instant.now();
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getMessage(){return message;} public void setMessage(String v){message=v;}
    public Long getPropertyId(){return propertyId;} public void setPropertyId(Long v){propertyId=v;}
    public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant v){createdAt=v;}
}
