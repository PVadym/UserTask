package pl.exorigo.upos;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by Вадим on 22.09.2017.
 */
public class SignOut extends WebPage {

    public SignOut(final PageParameters parameters)
    {
        getSession().invalidate();
    }
}
