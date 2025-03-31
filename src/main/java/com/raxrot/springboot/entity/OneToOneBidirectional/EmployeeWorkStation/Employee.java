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
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String position;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "work_station_id")
    private WorkStation workStation;
}
