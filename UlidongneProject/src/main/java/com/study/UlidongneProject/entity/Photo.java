package com.study.UlidongneProject.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "photo")
@Getter
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_idx")
    private Long photoIdx;
    @Column(name = "member_idx")
    private Long memberIdx;
    @Column(name = "photo_url")
    private String photoUrl;
    @Column(name = "like_member")
    private String likeMember;
    @Column(name = "photo_regidate")
    private LocalDate photoRegidate;
}
