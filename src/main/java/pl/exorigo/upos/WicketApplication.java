package pl.exorigo.upos;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.exorigo.upos.config.SpringConfig;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 *
 */
public class WicketApplication extends WebApplication {


    public WicketApplication() {
    }

    @Override
    public Session newSession(Request request, Response response)
    {
        return new SignInSession(request);
    }

    @Override
    public void init() {
        super.init();
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx));

        getSecuritySettings().setAuthorizationStrategy(new IAuthorizationStrategy.AllowAllAuthorizationStrategy()
        {
            @Override
            public <T extends IRequestableComponent> boolean isInstantiationAuthorized(
                    Class<T> componentClass)
            {
                if (AuthenticatedWebPage.class.isAssignableFrom(componentClass))
                {
                    if (((SignInSession)Session.get()).isSignedIn())
                    {
                        return true;
                    }

                    throw new RestartResponseAtInterceptPageException(SignIn.class);
                }

                return true;
            }
        });
    }
    public Class getHomePage() {
        return MainPage.class;
    }


}
