package pl.exorigo.upos;

import org.apache.wicket.protocol.http.WicketFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * Created by Вадим on 21.09.2017.
 */

@WebFilter(value = "/*", initParams = {
        @WebInitParam(name = "applicationClassName", value =
        "pl.exorigo.upos.WicketApplication"),
        @WebInitParam(name="filterMappingUrlPattern", value="/*") })
public class ProjectFilter extends WicketFilter {
}
