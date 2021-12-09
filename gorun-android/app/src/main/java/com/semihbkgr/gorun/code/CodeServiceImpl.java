package com.semihbkgr.gorun.code;

import androidx.annotation.NonNull;

import java.util.List;

public class CodeServiceImpl implements CodeService {

    private final CodeRepository codeRepository;

    public CodeServiceImpl(@NonNull CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @Override
    public Code getById(int id) {
        return codeRepository.findById(id);
    }

    @Override
    public List<CodeInfo> getAllInfos() {
        return codeRepository.findAllInfos();
    }

    @Override
    public Code save(@NonNull Code code) {
        return codeRepository.save(code);
    }

    @Override
    public Code update(int id, @NonNull Code code) {
        return codeRepository.update(id, code);
    }

    @Override
    public int delete(int id) {
        return codeRepository.delete(id);
    }

}
