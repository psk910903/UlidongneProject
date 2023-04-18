package com.study.UlidongneProject.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "outmember")
@Getter
public class OutMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "outmember_idx")
    private Long outMemberIdx;
    @Column(name = "outmember_name")
    private String outMemberName;
    @Column(name = "outmember_birthday")
    private LocalDate outMemberBirthday;
    @Column(name = "outmember_gender")
    private String outMemberGender;
}
