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
@Table(name = "temperaturedoor_data")
public class TemperaturesDoorData {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "temperatures_id")
        private Long temperaturesId;

        @Column(name = "temperaturedoor")
        private Float temperature;

        @CreationTimestamp
        @Column(name = "recorded_at")
        private LocalDateTime recordedAt;
    }

