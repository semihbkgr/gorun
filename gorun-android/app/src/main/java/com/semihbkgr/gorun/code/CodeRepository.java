package com.semihbkgr.gorun.code;

import java.util.List;

public interface CodeRepository {

    Code findById(int id);

    List<String> findAllTitle();

    Code save(Code code);

    Code update(int id,Code code);

    void delete(int id);

}
