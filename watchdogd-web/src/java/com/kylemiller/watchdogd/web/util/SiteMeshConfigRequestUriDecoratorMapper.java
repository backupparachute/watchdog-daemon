package com.kylemiller.watchdogd.web.util;

import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.module.sitemesh.Config;
import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.DecoratorMapper;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.mapper.AbstractDecoratorMapper;
import com.opensymphony.module.sitemesh.mapper.ConfigDecoratorMapper;
import com.opensymphony.module.sitemesh.mapper.ConfigLoader;

/**
 * Extension of the SiteMesh ConfigDecoratorMapper, because of the way it was handling
 * the decorator paths.  It now uses the request uri minus the the context path. 
 * 
 */
public class SiteMeshConfigRequestUriDecoratorMapper extends ConfigDecoratorMapper {
    private ConfigLoader configLoader = null;

    /** Create new ConfigLoader using '/WEB-INF/decorators.xml' file. */
    public void init(Config config, Properties properties, DecoratorMapper parent) throws InstantiationException {
        super.init(config, properties, parent);
        try {
            String fileName = properties.getProperty("config", "/WEB-INF/decorators.xml");
            configLoader = new ConfigLoader(fileName, config);
        }
        catch (Exception e) {
            throw new InstantiationException(e.toString());
        }
    }

    @Override
    public Decorator getDecorator(HttpServletRequest request, Page page) {
        //String thisPath = request.getServletPath();

        // getServletPath() returns null unless the mapping corresponds to a servlet
        //if (thisPath == null) {
            String requestURI = request.getRequestURI();
            
            requestURI = StringUtils.substringAfter(requestURI, request.getContextPath());
            
//            if (request.getPathInfo() != null) {
                // strip the pathInfo from the requestURI
//                requestURI = requestURI.substring(0, requestURI.indexOf(request.getPathInfo()));
//            }
            //else {
                //thisPath = requestURI;
            //}
//        }
        //else if ("".equals(thisPath)) {
            // in servlet 2.4, if a request is mapped to '/*', getServletPath returns null (SIM-130)
            //thisPath = request.getPathInfo();
        //}

        String name = null;
        try {
            //name = configLoader.getMappedName(thisPath);
        	name = configLoader.getMappedName(requestURI);
        }
        catch (ServletException e) {
            e.printStackTrace();
        }

        Decorator result = getNamedDecorator(request, name);
        return result == null ? super.getDecorator(request, page) : result;
    }
}
