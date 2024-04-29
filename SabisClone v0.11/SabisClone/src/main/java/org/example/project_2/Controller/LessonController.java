package org.example.project_2.Controller;

import org.example.project_2.Models.Lesson;
import org.example.project_2.Repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lessons")
public class LessonController {

    @Autowired
    private LessonRepository lessonRepository;

    @GetMapping
    public String getLessons(Model model) {
        Iterable<Lesson> lessons = lessonRepository.findAll();
        model.addAttribute("lesson", lessons);
        return "Lesson/Index";
    }

    @GetMapping("/add")
    public String addLesson(Model model){
        Lesson lesson = new Lesson();
        model.addAttribute("lesson", lesson);
        return "Lesson/LessonAdd";
    }

    @PostMapping("/add")
    public String lessonAdd(@ModelAttribute("lesson") Lesson lesson){
        lessonRepository.save(lesson);
        return "redirect:/lessons";
    }

    @GetMapping("/update/{id}")
    public String updateLessonForm(@PathVariable Long id, Model model){
        Lesson lesson = lessonRepository.findById(id).orElse(null);
        model.addAttribute("lesson", lesson);
        return "Lesson/Update";
    }

    @PostMapping("/update")
    public String lessonUpdate(@ModelAttribute("lesson") Lesson lesson){
        lessonRepository.save(lesson);
        return "redirect:/lessons";
    }

    @PostMapping("/delete/{id}")
    public String lessonDelete(@PathVariable Long id){
        lessonRepository.deleteById(id);
        return "redirect:/lessons";
    }
}
