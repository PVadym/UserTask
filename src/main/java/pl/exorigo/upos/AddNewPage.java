package pl.exorigo.upos;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
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
public class AddNewPage extends WebPage {

    @SpringBean
    private UserDao userDao;

    private User userModel = new User();


    public AddNewPage()
    {
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);
        add(new InputForm("inputForm"));
    }

    private class InputForm extends Form<User>
    {
        @SuppressWarnings("serial")
        public InputForm(String name)
        {
            super(name, new CompoundPropertyModel<>(userModel));

            add(new TextField<String>("name").setRequired(true).add(new StringValidator(5,10))
                    .setLabel(new Model<>("name")));

            add(new TextField<String>("surname").setRequired(true).add(new StringValidator(5,20))
                    .setLabel(new Model<>("surname")));

            add(new PasswordTextField("password").setRequired(true).add(new StringValidator(5,10))
                    .setLabel(new Model<>("password")));

            add(new TextField<String>("email").setRequired(false).add(EmailAddressValidator.getInstance())
                    .setLabel(new Model<>("email")));


            RadioChoice<Gender> rc = new RadioChoice<Gender>("gender", Arrays.asList(Gender.values())).setSuffix("");
            rc.setLabel(new Model<>("gender"));
            rc.setRequired(true);
            add(rc);

            add(new Button("saveButton"));
            add(new Button("resetButton")
            {
                @Override
                public void onSubmit()
                {
                    setResponsePage(AddNewPage.class);
                }
            }.setDefaultFormProcessing(false));
        }

        /**
         * @see org.apache.wicket.markup.html.form.Form#onSubmit()
         */
        @Override
        public void onSubmit()
        {
            User userFromDb = userDao.getByName(userModel.getName());
            if (userFromDb == null){
                userDao.save(userModel);
                setResponsePage(MainPage.class);
            } else {
                error("User " + userModel.getName() + "already exist in DB, change name");
            }

        }
    }

}
