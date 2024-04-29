package org.example.project_2.Repositories;

import org.example.project_2.Models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository <Lesson , Long> {
}
