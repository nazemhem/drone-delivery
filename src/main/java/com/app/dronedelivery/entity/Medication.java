package com.app.dronedelivery.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Medication {

    @Id
    private String code;

    private String name;
    private double weight;
    @Column(name = "image_url")
    private String imageURL;


    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCode() {
        return code;
    }

    public Medication(String code, String name, double weight) {
        this.code = code;
        this.name = name;
        this.weight = weight;
    }

    public Medication() {
    }

    @Override
    public String toString() {
        return "Medication{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
