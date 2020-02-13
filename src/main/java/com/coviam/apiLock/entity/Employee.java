package com.coviam.apiLock.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

//@Data
//@NoArgsConstructor
//@RedisHash("Employee")
//public class Employee implements Serializable {
//    @Id
//    private int id;
//    private String name;
//    private double salary;
//}


@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private double salary;
}

