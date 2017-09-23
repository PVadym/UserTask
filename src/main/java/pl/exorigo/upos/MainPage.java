package pl.exorigo.upos;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;
import pl.exorigo.upos.dao.UserDao;
import pl.exorigo.upos.entity.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Вадим on 20.09.2017.
 */
public class MainPage extends WebPage implements AuthenticatedWebPage {

    @SpringBean
    private UserDao userDao;

    public MainPage() {

        Form<Void> form = new Form<>("form");
        add(form);

        final TextField<String> field = new TextField<String>("field", new Model<>(""));
        form.add(field);


        List<User> persons = userDao.getAll();

        final PageableListView<User> listView = new PageableListView<User>("users", persons, 5) {
            @Override
            protected void populateItem(ListItem<User> item) {
                item.add(new DeletePage().linkInfo("details", item.getModelObject()));
                item.add(new Label("surname", new PropertyModel(item.getModel(), "surname")));
                item.add(new Label("password", new PropertyModel(item.getModel(), "password")));
                item.add(new Label("gender", new PropertyModel(item.getModel(), "gender")));
                item.add(new Label("email", new PropertyModel(item.getModel(), "email")));
                item.add(new EditPage().link("edit", item.getModelObject().getId()));
                item.add(new DeletePage().linkDelete("remove", item.getModelObject()));
            }
        };

        final WebMarkupContainer dataContainer = new WebMarkupContainer("data");
        dataContainer.setOutputMarkupId(true);
        dataContainer.add(listView);
        dataContainer.add(new PagingNavigator("navigator", listView));
        add(dataContainer);

        OnChangeAjaxBehavior onChangeAjaxBehavior = new OnChangeAjaxBehavior()
        {
            @Override
            protected void onUpdate(AjaxRequestTarget target)
            {
                listView.setList(getValue(field.getDefaultModelObjectAsString()));
                listView.setOutputMarkupId(true);
                dataContainer.addOrReplace(listView);
                target.add(dataContainer);
            }
        };
        field.add(onChangeAjaxBehavior);
    }

    private List<User> getValue(String input) {

        List<User> list = userDao.getAll();

        if (Strings.isEmpty(input)) {

            return list;
        }
        return list.stream()
                .filter(u->(u.getName().toLowerCase()).contains(input.toLowerCase())
                        ||(u.getSurname().toLowerCase()).contains(input.toLowerCase()))
                .collect(Collectors.toList());
    }

}
