package hnwj.jetty.web;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Simple REST controller mapped to '/caching'
 */
@Controller
public class CachingController {

    private static final Logger LOG = LoggerFactory.getLogger(CachingController.class);

    private static final LoadingCache<String, List> CACHE = CacheBuilder.newBuilder()
//            .expireAfterWrite(24L, TimeUnit.HOURS)
            .expireAfterWrite(2L, TimeUnit.MINUTES)
            .build(new CacheLoader<String, List>() {
                @Override
                public List load(String key) throws Exception {
                    return getObject(key);
                }
            });


    @RequestMapping("/caching")
    public String get(@RequestParam(name = "key", required = false) String key, Model model) {
        model.addAttribute("key", key);

        Object values = null;
        if (!StringUtils.isEmpty(key)) {
            values = CACHE.getUnchecked(key);

            model.addAttribute(key, values);
            model.addAttribute("values", values);
        }

        return "caching";
    }

    private static List getObject(String key) {
        LOG.info("Computing value for {}...", key);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<Object> objects = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            objects.add("afwefewfew" + i);

        }
        return objects;
    }


}
