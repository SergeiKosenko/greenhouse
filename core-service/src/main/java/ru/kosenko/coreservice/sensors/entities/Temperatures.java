package ru.kosenko.coreservice.sensors.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "temperatures")
public class Temperatures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "green_house_id")
    private Long greenHouseId;

    @Column(name = "name")
    private String name;

    @CreationTimestamp
    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;
}
