package com.semihbkgr.gorun.tutorial;

import java.util.List;

public interface TutorialService {

    boolean save(Subject subject);

    boolean saveAll(List<? extends Subject> subjectList);

    List<Subject> findSubjects();

    List<String> findLessonsTitle(String subjectName);

    Lesson findLesson(String title);

    int deleteAllSubjects();

    long subjectCount();

}
