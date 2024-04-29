package org.example.project_2.Controller;



import org.example.project_2.Models.Lesson;
import org.example.project_2.Models.Register;
import org.example.project_2.Models.Student;
import org.example.project_2.Repositories.LessonRepository;
import org.example.project_2.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @GetMapping
    public String getStudents(Model model) {
        Iterable<Student> students = studentRepository.findAll();
        model.addAttribute("student", students);
        return "Student/Index";
    }

    @GetMapping("/add")
    public String addStudent(Model model){
        Student student = new Student();
        model.addAttribute("student", student);
        return "Student/StudentAdd";
    }

    @PostMapping("/add")
    public String studentAdd(@ModelAttribute("student") Student student){
        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/add/{studentId}")
    public String showAddRegisterForm(@PathVariable Long studentId, Model model) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Öğrenci bulunamadı: " + studentId));
        Iterable<Lesson> lessons = lessonRepository.findAll();
        model.addAttribute("student", student);
        model.addAttribute("lessons", lessons);
        return "Register/AddRegister";
    }




        @GetMapping("/update/{id}")
    public String updateStudentForm(@PathVariable Long id, Model model){
        Student student = studentRepository.findById(id).orElse(null);
        model.addAttribute("student", student);
        return "Student/Update";
    }

    @PostMapping("/update")
    public String studentUpdate(@ModelAttribute("student") Student student){
        studentRepository.save(student);
        return "redirect:/students";
    }

    @PostMapping("/delete/{id}")
    public String studentDelete(@PathVariable Long id){
        studentRepository.deleteById(id);
        return "redirect:/students";
    }
}
