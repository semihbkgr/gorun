package com.semihbg.gorun.tutorial;

import java.util.List;

public interface TutorialService {

    List<Subject> findSubjects();

    List<String> findLessonsTitle(String subjectName);

    Lesson findLesson(String title);

}
