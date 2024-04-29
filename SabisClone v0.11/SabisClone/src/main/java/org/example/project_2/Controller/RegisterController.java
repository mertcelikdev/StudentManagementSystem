package org.example.project_2.Controller;

import org.example.project_2.Models.Lesson;
import org.example.project_2.Models.Register;
import org.example.project_2.Models.Student;
import org.example.project_2.Repositories.LessonRepository;
import org.example.project_2.Repositories.RegisterRepository;
import org.example.project_2.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registers")
public class RegisterController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private RegisterRepository registerRepository;

    @GetMapping
    public String getAllRegisters(Model model) {
        Iterable<Register> registers = registerRepository.findAll();
        model.addAttribute("registers", registers);
        return "Register/Index";
    }

    @GetMapping("/add")
    public String showAddRegisterForm(Model model) {
        Iterable<Student> students = studentRepository.findAll();
        Iterable<Lesson> lessons = lessonRepository.findAll();
        model.addAttribute("students", students);
        model.addAttribute("lessons", lessons);
        model.addAttribute("newRegister", new Register()); // Use "newRegister" instead of "registers"
        return "Register/AddRegister";
    }

    @PostMapping("/add")
    public String addRegister(@ModelAttribute("newRegister") Register newRegister, @RequestParam("studentId") Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + studentId));

        Lesson lesson = newRegister.getLesson();

        if (lesson == null) {
            throw new IllegalArgumentException("Lesson is required.");
        }

        Long lessonId = lesson.getLesson_id();

        Lesson foundLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found: " + lessonId));

        newRegister.setStudent(student);
        newRegister.setLesson(foundLesson);
        registerRepository.save(newRegister);

        return "redirect:/registers";
    }
    @GetMapping("/add/{studentId}")
    public String showAddRegisterForm(@PathVariable Long studentId, Model model) {
        // Retrieve student details by studentId from URL
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + studentId));

        // Retrieve all lessons from the lesson repository
        Iterable<Lesson> lessons = lessonRepository.findAll();

        // Prepare model attributes for the form
        model.addAttribute("student", student); // Student info (read-only)
        model.addAttribute("lessons", lessons); // All available lessons
        model.addAttribute("newRegister", new Register()); // Form backing object for new register

        return "Register/AddRegister"; // Thymeleaf template for the form
    }

    // Process form submission to add a new register
    @PostMapping("/add/{studentId}")
    public String addRegisterForStudent(@PathVariable Long studentId,
                                        @ModelAttribute("newRegister") Register newRegister) {
        // Retrieve the student by studentId from the database
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + studentId));

        // Retrieve the selected lesson from the new register object
        Lesson lesson = lessonRepository.findById(newRegister.getLesson().getLesson_id())
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

        // Set the student and lesson for the new register
        newRegister.setStudent(student);
        newRegister.setLesson(lesson);

        // Save the new register to the database
        registerRepository.save(newRegister);

        // Redirect to a suitable endpoint after successful submission (e.g., list of registers)
        return "redirect:/registers";
    }


    @GetMapping("/update/{id}")
    public String showUpdateRegisterForm(@PathVariable("id") Long id, Model model) {
        Register register = registerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid register id: " + id));
        Iterable<Student> students = studentRepository.findAll();
        Iterable<Lesson> lessons = lessonRepository.findAll();
        model.addAttribute("students", students);
        model.addAttribute("lessons", lessons);
        model.addAttribute("register", register); // Use "register" instead of "registers"
        model.addAttribute("registerId", id); // Add the register id to the model
        return "Register/UpdateRegister";
    }

    @PostMapping("/update/{id}")
    public String updateRegister(@PathVariable("id") Long id, @ModelAttribute("register") Register updatedRegister) {
        Register existingRegister = registerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Geçersiz kayıt ID: " + id));

        existingRegister.setAttendance(updatedRegister.getAttendance());
        existingRegister.setClassdate(updatedRegister.getClassdate());
        existingRegister.setTuition(updatedRegister.getTuition());

        Lesson lesson = updatedRegister.getLesson();
        if (lesson != null) {
            Lesson foundLesson = lessonRepository.findById(lesson.getLesson_id())
                    .orElseThrow(() -> new IllegalArgumentException("Ders bulunamadı: " + lesson.getLesson_id()));
            existingRegister.setLesson(foundLesson);
        }

        Student student = updatedRegister.getStudent();
        if (student != null) {
            Student foundStudent = studentRepository.findById(student.getStudent_id())
                    .orElseThrow(() -> new IllegalArgumentException("Öğrenci bulunamadı: " + student.getStudent_id()));
            existingRegister.setStudent(foundStudent);
        }

        registerRepository.save(existingRegister);
        return "redirect:/registers";
    }

    @PostMapping("/delete/{id}")
    public String deleteRegister(@PathVariable("id") Long id) {
        registerRepository.deleteById(id);
        return "redirect:/registers";
    }


    @GetMapping("students/add/{lessonId}")
    public String showAddRegisterForm1(@PathVariable Long lessonId, Model model) {
        // Retrieve lesson details by lessonId from URL
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found with id: " + lessonId));

        // Retrieve all students from the student repository
        Iterable<Student> students = studentRepository.findAll();

        // Prepare model attributes for the form
        model.addAttribute("lesson", lesson); // Lesson info (read-only)
        model.addAttribute("students", students); // All available students
        model.addAttribute("newRegister", new Register()); // Form backing object for new register

        return "Lesson/LessonRegister"; // Thymeleaf template for the form
    }

    // Process form submission to add a new register
    @PostMapping("students/add/{lessonId}")
    public String addRegisterForLesson1(@PathVariable Long lessonId,
                                        @ModelAttribute("newRegister") Register newRegister) {
        // Retrieve the lesson by lessonId from the database
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found with id: " + lessonId));

        // Set the lesson for the new register
        newRegister.setLesson(lesson);

        // Save the new register to the database
        registerRepository.save(newRegister);

        // Redirect to a suitable endpoint after successful submission (e.g., list of registers)
        return "redirect:/registers";
    }






}