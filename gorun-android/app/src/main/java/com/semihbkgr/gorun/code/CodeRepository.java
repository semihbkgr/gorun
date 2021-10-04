package com.semihbkgr.gorun.code;

import java.util.List;

public interface CodeRepository {

    Code findById(int id);

    List<CodeInfo> findAllInfos();

    Code save(Code code);

    Code update(int id,Code code);

    int delete(int id);

}
