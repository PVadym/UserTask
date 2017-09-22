package pl.exorigo.upos;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import pl.exorigo.upos.dao.UserDao;
import pl.exorigo.upos.entity.User;

import java.util.List;

/**
 * Created by Вадим on 20.09.2017.
 */
public class MainPage extends WebPage implements AuthenticatedWebPage {
    @SpringBean
    private UserDao userDao;

    public MainPage() {
        List<User> persons = userDao.getAll();

        add(new ListView<User>("users", persons) {
            @Override
            protected void populateItem(ListItem<User> item) {
                item.add(new Label("name", new PropertyModel(item.getModel(), "name")));
                item.add(new Label("surname", new PropertyModel(item.getModel(), "surname")));
                item.add(new Label("password", new PropertyModel(item.getModel(), "password")));
                item.add(new Label("gender", new PropertyModel(item.getModel(), "gender")));
                item.add(new Label("email", new PropertyModel(item.getModel(), "email")));
                item.add(new EditPage().link("edit", item.getModelObject().getId()));
                item.add(new DeletePage().link("remove", item.getModelObject()));
            }
        });

    }

}
