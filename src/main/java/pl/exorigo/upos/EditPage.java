package pl.exorigo.upos;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.validation.FormComponentFeedbackBorder;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;
import pl.exorigo.upos.dao.UserDao;
import pl.exorigo.upos.entity.User;
import pl.exorigo.upos.enums.Gender;

import java.util.Arrays;

/**
 * Created by Вадим on 21.09.2017.
 */
public class EditPage extends WebPage implements AuthenticatedWebPage{

    @SpringBean
    private UserDao userDao;

    private User user;

    public EditPage(){}

    public EditPage(User user) {

        add(new FeedbackPanel("feedback"));
        add(new EditUserForm("editUserForm", user));

    }

     public final class EditUserForm extends Form<User>
    {

        public EditUserForm(final String id, final User user)
        {
            super(id, new CompoundPropertyModel<>(user));

            final TextField<String> name = new TextField<>("name");
            name.setRequired(true);
            name.add(new StringValidator(5, 10));
            final MarkupContainer nameFeedback = new FormComponentFeedbackBorder("nameFeedback");
            add(nameFeedback);
            nameFeedback.add(name);

            final TextField<String> surname = new TextField<>("surname");
            surname.setRequired(true);
            surname.add(new StringValidator(5, 20));;
            final MarkupContainer surnameFeedback = new FormComponentFeedbackBorder("surnameFeedback");
            add(surnameFeedback);
            surnameFeedback.add(surname);

            final PasswordTextField password = new PasswordTextField("password");
            password.setRequired(true);
            password.add(new StringValidator(5, 10));
            final MarkupContainer passwordFeedback = new FormComponentFeedbackBorder("passwordFeedback");
            add(passwordFeedback);
            passwordFeedback.add(password);

            RadioChoice<Gender> rc = new RadioChoice<>("gender", Arrays.asList(Gender.values()));
            add(rc);

            final TextField<String> email = new TextField<>("email");
            email.add(EmailAddressValidator.getInstance());
            final MarkupContainer emailFeedback = new FormComponentFeedbackBorder("emailFeedback");
            add(emailFeedback);
            emailFeedback.add(email);

        }

        @Override
        public final void onSubmit()
        {
            final User user = getModelObject();
            userDao.update(user);
            setResponsePage(MainPage.class);
        }
    }

    public  Link<Void> link(String edit, long id) {
        return new Link<Void>(edit)
        {
            @Override
            public void onClick()
            {
                setResponsePage(new EditPage(userDao.getById(id)));
            }
        };
    }
}
