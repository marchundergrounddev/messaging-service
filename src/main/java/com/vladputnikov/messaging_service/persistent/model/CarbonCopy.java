package com.vladputnikov.messaging_service.persistent.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Table(name = "ccs")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarbonCopy {

    @Column(name = "cc_name")
    private String ccName;
}
