package org.example.project_2.Models;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Entity
@Table( name ="lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long lesson_id;

    @Getter
    @Column(name="lesson_name", length=36)
    private String lesson_name;

    @Column(name="title", length=36)
    private String title;

    @Getter
    @Column(name="description", length=16)
    private String description;

    @Getter
    @Column(name="semester", length=16)
    private String semester;

    public Long getLesson_id() {
        return lesson_id;
    }
}
