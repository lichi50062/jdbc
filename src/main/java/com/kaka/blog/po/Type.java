package com.kaka.blog.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kaka
 */
@Entity
@Data
public class Type {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "類別未填")
    private String typeName;
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
