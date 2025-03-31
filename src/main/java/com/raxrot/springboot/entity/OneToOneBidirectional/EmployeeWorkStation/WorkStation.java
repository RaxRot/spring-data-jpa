package com.raxrot.springboot.entity.OneToOneBidirectional.EmployeeWorkStation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "work_stations")
public class WorkStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    private boolean isAvailable;

    @OneToOne(mappedBy = "workStation")
    private Employee employee;
}
