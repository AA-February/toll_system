package com.example.spring_into.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "owners")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String lastName;

    private String firstName;

    private String address;

    @OneToMany(mappedBy = "owner",
            cascade = CascadeType.ALL,
    fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<TollPass> tollPass;
}
