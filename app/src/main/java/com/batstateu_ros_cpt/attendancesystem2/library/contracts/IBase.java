package com.batstateu_ros_cpt.attendancesystem2.library.contracts;

import java.util.List;

public interface IBase<T>  {

    List<T> getAll();
    List<T> getAll(String criteria);
    T get(Integer id);
    void add(T model) throws Exception;
    void edit(Integer id, T model) throws Exception;
    void delete(Integer id);
}
