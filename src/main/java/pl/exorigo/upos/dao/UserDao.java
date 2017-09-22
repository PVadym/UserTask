package pl.exorigo.upos.dao;

import pl.exorigo.upos.entity.User;

import java.util.List;

/**
 * Created by Вадим on 20.09.2017.
 */
public interface UserDao {

    void save (User user);
    void delete (long id);
    void delete (User user);
    void update (User user);
    User getById(long id);
    User getByName(String name);
    List<User> getAll();
}
