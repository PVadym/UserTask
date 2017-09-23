package pl.exorigo.upos;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;
import pl.exorigo.upos.dao.UserDao;
import pl.exorigo.upos.entity.User;

/**
 * Created by Вадим on 22.09.2017.
 */
public class DeletePage extends WebPage implements AuthenticatedWebPage {

    @SpringBean
    private UserDao userDao;

    public DeletePage() {

    }

    public DeletePage(User user) {

        add(new Label("id", user.getId()));
        add(new Label("name", user.getName()));
        add(new Label("surname", user.getSurname()));
        add(new Label("password", user.getPassword()));
        add(new Label("gender", user.getGender().name()));
        add(new Label("email", user.getEmail()));

        add(new Link<Void>("delete")
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick()
            {
                userDao.delete(user);
                setResponsePage(MainPage.class);
            }
        });
    }


    public Link<Void> linkDelete(String remove, User user) {
        return new Link<Void>(remove)
        {
            @Override
            public void onClick()
            {
                setResponsePage(new DeletePage(user));
            }
        };
    }

    public Link<Void> linkInfo(final String name, final User user)
    {
        Link<Void> link = new Link<Void>(name) {
            @Override
            public void onClick() {
                setResponsePage(new DeletePage(user));
            }
        };
        link.add(new Label("name", user.getName()));
        return link;

    }

}
