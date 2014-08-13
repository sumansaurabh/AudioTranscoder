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

import static junit.framework.TestCase.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.apache.clerezza.rdf.core.sparql.ParseException;
import org.apache.stanbol.enhancer.contentitem.inmemory.InMemoryContentItemFactory;
import org.apache.stanbol.enhancer.servicesapi.ContentItem;
import org.apache.stanbol.enhancer.servicesapi.ContentItemFactory;
import org.apache.stanbol.enhancer.servicesapi.EngineException;
import org.apache.stanbol.enhancer.servicesapi.impl.StreamSource;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.service.cm.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XuggleTranscoderTest {
    private static final Logger log = LoggerFactory.getLogger(XuggleTranscoderTest.class);

    private static final ContentItemFactory ciFactory = InMemoryContentItemFactory.getInstance();
    private static XuggleTranscodingEngine engine;
    private static MockComponentContext context;
    //private static ModelProviderImpl MP=new ModelProviderImpl(new ClasspathDataFileProvider("DUMMY"));

    @BeforeClass
    public static void setUpServices() throws IOException {
        context = new MockComponentContext();
        engine=new XuggleTranscodingEngine(ciFactory);
        context.properties.put(XuggleTranscodingEngine.PROPERTY_NAME, "xuggle");
    }
		 
    @Before
    public void bindServices() throws ConfigurationException {
    }
    @Test 
    public void testDefaultEnhancements() throws EngineException, IOException, ParseException {
        log.info(">>> Default Model Sphinix Testing WAV  <<<");
        ContentItem ci = createContentItem("test.mp4", "audio/mp4");
        if(ci.getStream()==null)
        	System.out.println("##### Null ");
        //assertFalse(engine.canEnhance(ci) == CANNOT_ENHANCE);
        System.out.println("##################################################"+engine.canEnhance(ci));
        System.out.println("##### Engine open ");
        engine.computeEnhancements(ci);
        System.out.println("##### Engine Close");
           
    }
    private ContentItem createContentItem(String resourceName, String contentType) throws IOException {
        InputStream in = XuggleTranscoderTest.class.getClassLoader().getResourceAsStream(resourceName);
        assertNotNull(in);
        return ciFactory.createContentItem(new StreamSource(in,contentType));
    }
}
