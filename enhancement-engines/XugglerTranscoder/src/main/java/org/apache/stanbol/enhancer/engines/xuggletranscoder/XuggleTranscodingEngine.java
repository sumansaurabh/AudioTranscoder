/*******************************************************************************
 * Copyright (c)  .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.apache.stanbol.enhancer.engines.xuggletranscoder;

import static org.apache.stanbol.enhancer.servicesapi.helper.EnhancementEngineHelper.randomUUID;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;

import org.apache.clerezza.rdf.core.UriRef;
import org.apache.commons.io.IOUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.stanbol.enhancer.servicesapi.ContentItem;
import org.apache.stanbol.enhancer.servicesapi.ContentItemFactory;
import org.apache.stanbol.enhancer.servicesapi.ContentSink;
import org.apache.stanbol.enhancer.servicesapi.EngineException;
import org.apache.stanbol.enhancer.servicesapi.EnhancementEngine;
import org.apache.stanbol.enhancer.servicesapi.ServiceProperties;
import org.apache.stanbol.enhancer.servicesapi.impl.AbstractEnhancementEngine;
import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(configurationFactory = true, //allow multiple service instances (false for a  singelton instance)
    policy = ConfigurationPolicy.OPTIONAL, //use REQUIRE if a non default option is present
    immediate = true, //activate service instances on startup 
    metatype = true, inherit = true, specVersion = "1.1")
@Service //this will register the engine as an OSGI service
@Properties(value = { //Configuration properties included in the config form
    @Property(name = EnhancementEngine.PROPERTY_NAME, value = "xuggler"),
    @Property(name = Constants.SERVICE_RANKING, intValue = 0)
})
public class XuggleTranscodingEngine extends AbstractEnhancementEngine<RuntimeException,RuntimeException>
        implements EnhancementEngine, ServiceProperties {

    /**
     * Using slf4j for logging
     */
    private static final Logger log = LoggerFactory.getLogger(XuggleTranscodingEngine.class);

    @Reference
    private ContentItemFactory ciFactory;
    
    /**
     * Default constructor used by OSGI
     */
    protected static final Charset UTF8 = Charset.forName("UTF-8");

    private String []SUPPORTED_MIME_TYPES={"mp3","vox","rm","ogg","amr","aac","wav"};
    
    public XuggleTranscodingEngine() {}
    /**
     * Used by the unit tests to init the {@link ContentItemFactory} outside
     * an OSGI environment.
     * @param cifactory
     */
    XuggleTranscodingEngine(ContentItemFactory cifactory) {
        this.ciFactory = cifactory;
    }

    /**
     * TODO: change to fit your engine. See constants defined in the 
     * ServiceProperties class
     */
    protected static final Integer ENGINE_ORDERING = ServiceProperties.ORDERING_PRE_PROCESSING;
    
    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
     * OSGI lifecycle methods
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
     */
    /**
     * Activate and read the properties. Configures and initialises a POSTagger for each language configured in
     * CONFIG_LANGUAGES.
     *
     * @param ce the {@link org.osgi.service.component.ComponentContext}
     */
    @Activate
    protected void activate(ComponentContext ce) throws ConfigurationException {
        super.activate(ce);
        
        log.info("activating {}: {}", getClass().getSimpleName(), getName());
    }
    
    @Deactivate
    protected void deactivate(ComponentContext context) {
        log.info("deactivating {}: {}", getClass().getSimpleName(), getName());
        //TODO: reset fields to default, close resources ...
        super.deactivate(context); //call deactivate on the super class
    }
    
    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
     * ServiceProperties interface method
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
     */
    
    /**
     * ServiceProperties are currently only used for automatic ordering of the 
     * execution of EnhancementEngines (e.g. by the WeightedChain implementation).
     * Default ordering means that the engine is called after all engines that
     * use a value < {@link ServiceProperties#ORDERING_CONTENT_EXTRACTION}
     * and >= {@link ServiceProperties#ORDERING_EXTRACTION_ENHANCEMENT}.
     */
    @Override
    public Map<String,Object> getServiceProperties() {
        return Collections.unmodifiableMap(Collections.<String,Object>singletonMap(
                ENHANCEMENT_ENGINE_ORDERING, (Object)(ORDERING_PRE_PROCESSING+1))); // to let it run above others
    }
    
    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
     * EnhancementEngine interface methods
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
     */

    /**
     * @return if and how (asynchronously) we can enhance a ContentItem
     */
    @Override
    public int canEnhance(ContentItem ci) throws EngineException {
        // check if a Content in the supported type is available
        //NOTE: you can parse multiple content types
        log.info("MimeType: {}", ci.getMimeType());
        if (isSupported(ci.getMimeType())) {
            return ENHANCE_ASYNC;
        }
        return CANNOT_ENHANCE;
    }
    
    
    

   
    @SuppressWarnings("deprecation")
	@Override
    public void computeEnhancements(ContentItem ci) throws EngineException {
    	InputStream in =ci.getBlob().getStream();
    	if(in==null)
        	System.out.println("Cannot Compute enhancement");
    	ConvertAudio cv =new ConvertAudio(in);
    	cv.run();
    	
    	///Conveted Audio added as the Blob to the ContentItem
        
        
        ContentSink audioSink;
        try {
            audioSink = ciFactory.createContentSink("audio/wav");
            
        }catch (IOException e) {
            IOUtils.closeQuietly(in); //close the input stream
            throw new EngineException("Error while initialising Blob for" +
                		"writing the audio to the parsed content",e);
        }
        String random = randomUUID().toString();
        UriRef textBlobUri = new UriRef("urn:Sphinx:text:"+random);//create an UriRef for the Blob
        ci.addPart(textBlobUri, audioSink.getBlob());
        ci.addPart(textBlobUri, cv.getOutputStream());//adds output stream to contentItem
        audioSink=null;
}
    
     private boolean isSupported(String mimeType) {
    	for(String audioMime: SUPPORTED_MIME_TYPES)
    	{
    		if (mimeType.startsWith(audioMime)) 
    			return true;
    	}
    		return false; // As there isn't a list of media types that can contain this
    	
    }
}
