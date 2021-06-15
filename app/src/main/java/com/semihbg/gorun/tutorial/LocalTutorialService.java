package com.semihbg.gorun.tutorial;

import android.content.Context;
import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.semihbg.gorun.AppConstants;
import com.semihbg.gorun.util.ThrowUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class LocalTutorialService implements TutorialService {

    private final Context context;
    private final Gson gson;
    private final List<Section> sectionList;
    private final Map<String, Section> titleSectionMap;

    public LocalTutorialService(Context context, Gson gson) {
        this.context = context;
        this.gson = gson;
        this.sectionList = new ArrayList<>();
        this.titleSectionMap = new HashMap<>();
    }

    @Override
    public List<Section> getSections() {
        if (sectionList.size() == 0) {
            List<Section> sectionList = Arrays.asList(getSectionsFromLocal());
            this.sectionList.addAll(sectionList);
            for (Section section : sectionList)
                titleSectionMap.put(section.title, section);
        }
        return Collections.unmodifiableList(sectionList);
    }

    @Override
    @Nullable
    public Section getSection(String title) {
        return titleSectionMap.getOrDefault(title, null);
    }

    private Section[] getSectionsFromLocal() {
        try (InputStream inputStream = context.getAssets().open(AppConstants.LOCAL_TUTORIAL_FILE_NAME);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
            return gson.fromJson(inputStreamReader, Section[].class);
        } catch (IOException e) {
            return ThrowUtils.sneakyThrow(e);
        }
    }

}
