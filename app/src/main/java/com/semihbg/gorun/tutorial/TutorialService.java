package com.semihbg.gorun.tutorial;

import java.util.List;

public interface TutorialService {

    List<Section> getSections();

    Section getSection(String title);

}
