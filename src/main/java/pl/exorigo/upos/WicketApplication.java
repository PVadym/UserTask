package pl.exorigo.upos;

import org.apache.wicket.protocol.http.WebApplication;
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
    public void init() {
        super.init();
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx));
    }

    public Class getHomePage() {
        return MainPage.class;
    }


}
