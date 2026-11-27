package com.balaji.realestate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoDataInitializer implements CommandLineRunner {
    private final PropertyRepository properties;

    @Value("${app.seed-demo-data:false}")
    private boolean seedDemoData;

    public DemoDataInitializer(PropertyRepository properties) {
        this.properties = properties;
    }

    @Override
    public void run(String... args) {
        if (!seedDemoData || properties.count() > 0) {
            return;
        }

        properties.saveAll(List.of(
            demo("Sample 2 BHK Apartment (Demo)", "Flat", "SALE", 8500000, 980, 2, 2,
                "Hiranandani Estate, Thane West (Demo)",
                "Illustrative demo listing created for preview only. Replace with verified property details before publishing.",
                "https://images.unsplash.com/photo-1600210492486-724fe5c67fb0?auto=format&fit=crop&w=1200&q=85"),
            demo("Sample 1 BHK Rental Flat (Demo)", "Flat", "RENT", 28000, 620, 1, 1,
                "Kolshet Road, Thane West (Demo)",
                "Illustrative rental demo for preview only. Replace with verified property details before publishing.",
                "https://images.unsplash.com/photo-1600607687920-4e2a09cf159d?auto=format&fit=crop&w=1200&q=85"),
            demo("Sample Retail Shop for Rent (Demo)", "Shop", "RENT", 45000, 510, 0, 1,
                "Brahmand Complex, Thane West (Demo)",
                "Illustrative commercial rental demo for preview only. Replace with verified property details before publishing.",
                "https://images.unsplash.com/photo-1604328698692-f76ea9498e76?auto=format&fit=crop&w=1200&q=85"),
            demo("Sample Retail Shop for Sale (Demo)", "Shop", "SALE", 5400000, 390, 0, 1,
                "Azadnagar, Thane West (Demo)",
                "Illustrative commercial sale demo for preview only. Replace with verified property details before publishing.",
                "https://images.unsplash.com/photo-1600566753086-00f18fb6b3ea?auto=format&fit=crop&w=1200&q=85")
        ));
    }

    private Property demo(String title, String type, String listingType, long price, int area,
                          int bedrooms, int bathrooms, String location, String description, String imageUrl) {
        Property property = new Property();
        property.setTitle(title);
        property.setType(type);
        property.setListingType(listingType);
        property.setPrice(price);
        property.setAreaSqft(area);
        property.setBedrooms(bedrooms);
        property.setBathrooms(bathrooms);
        property.setLocation(location);
        property.setDescription(description);
        property.setImageUrl(imageUrl);
        property.setStatus("AVAILABLE");
        property.setFeatured(true);
        return property;
    }
}
