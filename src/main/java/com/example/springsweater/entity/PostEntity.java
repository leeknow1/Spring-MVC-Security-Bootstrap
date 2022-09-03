package com.example.springsweater.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "sweater")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "content can not be empty!")
    @Length(max = 50, message = "max length for content is 50!")
    private String content;

    @Column
    @NotBlank(message = "tag can not be empty!")
    @Length(max = 50, message = "max length for tag is 50!")
    private String tag;

    public PostEntity(String content, String tag) {
        this.content = content;
        this.tag = tag;
    }
}
