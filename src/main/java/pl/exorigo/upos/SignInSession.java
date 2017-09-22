package pl.exorigo.upos;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import pl.exorigo.upos.dao.UserDao;
import pl.exorigo.upos.entity.User;

/**
 * Created by Вадим on 22.09.2017.
 */
public class SignInSession extends AuthenticatedWebSession {

    @SpringBean
    private UserDao userDao;

    protected SignInSession(Request request)
    {
        super(request);
        Injector.get().inject(this);
    }

    @Override
    public final boolean authenticate(final String username, final String password)
    {
            User userFormDB = userDao.getByName(username);

            if(userFormDB!=null) {
                if (username.equalsIgnoreCase(userFormDB.getName()) && password.equals(userFormDB.getPassword())) {
                    return true;
                }
            }
        return false;
    }


    @Override
    public Roles getRoles()
    {
        return null;
    }
}
