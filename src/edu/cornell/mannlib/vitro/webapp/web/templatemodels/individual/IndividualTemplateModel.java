/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vitro.webapp.web.templatemodels.individual;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

// Added by psf - 2015-06-05
import edu.cornell.mannlib.vitro.webapp.beans.DataProperty;
import edu.cornell.mannlib.vitro.webapp.beans.DataPropertyStatement;
import edu.cornell.mannlib.vitro.webapp.beans.ObjectProperty;

import edu.cornell.mannlib.vitro.webapp.beans.DataPropertyStatement;
import edu.cornell.mannlib.vitro.webapp.beans.Individual;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.controller.freemarker.UrlBuilder;
import edu.cornell.mannlib.vitro.webapp.controller.freemarker.UrlBuilder.ParamMap;
import edu.cornell.mannlib.vitro.webapp.controller.freemarker.UrlBuilder.Route;
import edu.cornell.mannlib.vitro.webapp.controller.visualization.VisualizationFrameworkConstants;
import edu.cornell.mannlib.vitro.webapp.dao.jena.QueryUtils;
import edu.cornell.mannlib.vitro.webapp.dao.WebappDaoFactory;

public class IndividualTemplateModel extends BaseIndividualTemplateModel {

    private static final Log log = LogFactory.getLog(IndividualTemplateModel.class);
    
    private static final String FOAF = "http://xmlns.com/foaf/0.1/";
    private static final String PERSON_CLASS = FOAF + "Person";
    private static final String AWARD_CLASS = "http://vivoweb.org/ontology/core#Award";
    private static final String DEGREE_CLASS = "http://vivoweb.org/ontology/core#AcademicDegree";
    private static final String CONTACT_CLASS = "http://purl.obolibrary.org/obo/ARG_2000376";
    private static final String CREDENTIAL_CLASS = "http://vivoweb.org/ontology/core#Credential";
    private static final String DTP_CLASS = "http://vivoweb.org/ontology/core#DateTimeValuePrecision";
    private static final String ORGANIZATION_CLASS = FOAF + "Organization";
    private static final String EVENT_CLASS = "http://purl.org/NET/c4dm/event.owl#Event";
    private static final String INFO_CONTENT_ENTITY_CLASS = "http://purl.obolibrary.org/obo/IAO_0000030";
    private static final String BASE_VISUALIZATION_URL = 
        UrlBuilder.getUrl(Route.VISUALIZATION_SHORT.path());
    
    public IndividualTemplateModel(Individual individual, VitroRequest vreq) {
        super(individual, vreq);
    }
    
    private String getVisUrl(String visPath) {
        String visUrl;
        boolean isUsingDefaultNameSpace = UrlBuilder.isUriInDefaultNamespace(
                                                getUri(),
                                                vreq);
        
        if (isUsingDefaultNameSpace) {          
            visUrl = visPath + getLocalName();           
        } else {            
            visUrl = UrlBuilder.addParams(
                    visPath, 
                    new ParamMap(VisualizationFrameworkConstants.INDIVIDUAL_URI_KEY, getUri())); 
        }
        
        return visUrl;
    }
    
    /* Template methods (for efficiency, not pre-computed) */
    public boolean conceptSubclass() {
        if ( isVClass(AWARD_CLASS) || isVClass(DEGREE_CLASS) ||isVClass(CONTACT_CLASS) || isVClass(CREDENTIAL_CLASS) || isVClass(DTP_CLASS) ) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean person() {
        return isVClass(PERSON_CLASS);
    }
    
    public boolean organization() {
        return isVClass(ORGANIZATION_CLASS);        
    }
    
    public boolean event() {
        return isVClass(EVENT_CLASS);        
    }

    public boolean infoContentEntity() {
        return isVClass(INFO_CONTENT_ENTITY_CLASS);        
    }

    public String coAuthorVisUrl() {   	
        String url = BASE_VISUALIZATION_URL + "/" + VisualizationFrameworkConstants.COAUTHORSHIP_VIS_SHORT_URL + "/";    	
    	return getVisUrl(url);
    }

    public String coInvestigatorVisUrl() {    	
    	String url = 
    	    BASE_VISUALIZATION_URL + "/" + VisualizationFrameworkConstants.COINVESTIGATOR_VIS_SHORT_URL + "/";    	
    	return getVisUrl(url);
    }

    public String temporalGraphUrl() {  
        String url = 
            BASE_VISUALIZATION_URL + "/" + VisualizationFrameworkConstants.PUBLICATION_TEMPORAL_VIS_SHORT_URL + "/";    	
    	return getVisUrl(url);
    }

    public String mapOfScienceUrl() {
    	String url = 
    	    BASE_VISUALIZATION_URL + "/" + VisualizationFrameworkConstants.MAP_OF_SCIENCE_VIS_SHORT_URL + "/";    	
    	return getVisUrl(url);
    }

    /**
     * Overriding method in BaseIndividualTemplateModel
     * - default person image: images/placeholders/person.thumbnail.jpg
     */
    public String getImageUrl() {
        // List<DataProperty> dataPropertyList = individual.getDataPropertyList();
        // for (DataProperty dp : dataPropertyList) {
        //     log.info("~~~ inspecting DataProperty " + dp.toString());
        //     log.info("~~~ ~~~ publicName = " + dp.getPublicName());
        //     log.info("~~~ ~~~ localName = " + dp.getLocalName());
        //     log.info("~~~ ~~~ name = " + dp.getName());
        // }
        String fsmFacultyProfileID = null;
        List<DataPropertyStatement> stmts = individual.getDataPropertyStatements();
        for (DataPropertyStatement dps : stmts) {
            if(dps.getDatapropURI().contains("fsmFacultyProfileID")) {
                fsmFacultyProfileID = dps.getData();
            }

        }
        if(fsmFacultyProfileID == null) {
            log.info("~~~ fsmFacultyProfileID is null");
            String imageUrl = individual.getImageUrl();
            String retval = imageUrl == null ? null : getUrl(imageUrl);
            log.info("~~~ returning " + retval);
            return retval;
        } else {
            log.info("~~~ fsmFacultyProfileID is " + fsmFacultyProfileID);
            String retval = "http://deptcommon.fsm.northwestern.edu/ws/getFacultyPhoto.php?xid=" + fsmFacultyProfileID;
            log.info("~~~ returning " + retval);
            return retval;
        }

    }

    /**
     * Overriding method in BaseIndividualTemplateModel
     */
    public String getThumbUrl() {
        // List<DataProperty> dataPropertyList = individual.getDataPropertyList();
        // for (DataProperty dp : dataPropertyList) {
        //     log.info("~~~ inspecting DataProperty " + dp.toString());
        //     log.info("~~~ ~~~ publicName = " + dp.getPublicName());
        //     log.info("~~~ ~~~ localName = " + dp.getLocalName());
        //     log.info("~~~ ~~~ name = " + dp.getName());
        // }
        String fsmFacultyProfileID = null;
        List<DataPropertyStatement> stmts = individual.getDataPropertyStatements();
        for (DataPropertyStatement dps : stmts) {
            log.info("~~~ inspecting DataPropertyStatement " + dps.toString());
            log.info("~~~ ~~~ data = " + dps.getData());
            log.info("~~~ ~~~ datapropURI = " + dps.getDatapropURI());

            if(dps.getDatapropURI().contains("fsmFacultyProfileID")) {
                log.info("~~~ ~~~ setting fsmFacultyProfileID to " + dps.getData());
                fsmFacultyProfileID = dps.getData();
            }

        }
        if(fsmFacultyProfileID == null) {
            log.info("~~~ fsmFacultyProfileID is null");
            String thumbUrl = individual.getThumbUrl();            
            String retval = thumbUrl == null ? null : getUrl(thumbUrl);
            log.info("~~~ returning " + retval);
            return retval;
        } else {
            log.info("~~~ fsmFacultyProfileID is " + fsmFacultyProfileID);
            String retval = "http://deptcommon.fsm.northwestern.edu/ws/getFacultyPhoto.php?xid=" + fsmFacultyProfileID;
            log.info("~~~ returning " + retval);
            return retval;
        }
    } 

}
