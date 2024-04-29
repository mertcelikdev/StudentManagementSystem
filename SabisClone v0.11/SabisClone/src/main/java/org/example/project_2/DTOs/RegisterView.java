package org.example.project_2.DTOs;

import lombok.*;
import org.example.project_2.Models.Lesson;
import org.example.project_2.Models.Student;

import java.util.Date;



@AllArgsConstructor
@NoArgsConstructor
@Data


public class RegisterView {
    private Student student;
    private Lesson lesson;
    private String classdate;
    private double tuition;
    private boolean attendance;


}


