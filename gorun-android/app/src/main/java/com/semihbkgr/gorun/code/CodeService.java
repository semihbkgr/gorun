package com.semihbkgr.gorun.code;

import java.util.List;

public interface CodeService {

    Code getById(int id);

    List<CodeInfo> getAllInfos();

    Code save(Code code);

    Code update(int id,Code code);

    int delete(int id);

}
