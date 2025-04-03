package com.example.planner.planner.entity;


import com.example.planner.global.entity.BaseEntity;
import com.example.planner.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "planner")
public class Planner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    // column 타입 text
    @Column(nullable = false, columnDefinition = "longtext")
    private String contents;

    // 다대일 연관관계
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Planner() {

    }

    public Planner(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updatePlanner(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

}
