package hystrix.ribbon;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerListFilter;

import java.util.List;

/**
 * Created by barry on 2017/6/15.
 */
public class CustomServerListFilter implements ServerListFilter<Server> {
    @Override
    public List<Server> getFilteredListOfServers(List servers) {
        return servers;
    }
}
