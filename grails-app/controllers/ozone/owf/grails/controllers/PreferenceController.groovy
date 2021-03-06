package ozone.owf.grails.controllers

import grails.converters.JSON
import org.apache.commons.lang.time.StopWatch
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.context.ApplicationContext
import org.springframework.web.context.support.WebApplicationContextUtils
import ozone.owf.grails.OwfException

class PreferenceController extends BaseOwfRestController{

  	def preferenceService
	def modelName = 'preference'

	def show = {
		def statusCode = 200
		def jsonResult
        StopWatch stopWatch = null;

        if (log.isInfoEnabled()) {
          stopWatch = new StopWatch();
          stopWatch.start();
          log.info("Executing preferenceService: show");
        }
		try {
			def result = preferenceService.show(params)
            if (result?.success == true) {
			   jsonResult = getJsonResult(result, modelName, params)
            } 
            else {
               // Currently success always = true to the users
               jsonResult = [success:true, data: null] as JSON
            }
		}
		catch (OwfException owe) {
			handleError(owe)
			statusCode = owe.exceptionType.normalReturnCode
			jsonResult = "Error during show " + owe.exceptionType.generalMessage + " " + owe.message
		}
		
		renderResult(jsonResult, statusCode)
		
		if (log.isInfoEnabled()) {
        	log.info("Executed preferenceService: show in "+stopWatch);
		}
	}
	
	def doesPreferenceExist = {
		def preferenceExist
		def statusCode
		
		try {
			def result = preferenceService.show(params)
			if(result.preference) {
				statusCode = 200
				preferenceExist = true
			}
            else {
				statusCode = 200
				preferenceExist = false
            }
		}
		catch(OwfException owe) {
			handleError(owe)
			statusCode = owe.exceptionType.normalReturnCode
			preferenceExist = false
		}
		
		def jsonResult = [preferenceExist:preferenceExist, statusCode:statusCode] as JSON
		
		renderResult(jsonResult, statusCode)		
	}
	
	def serverResources = {
		def statusCode
		
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext())
		def resource = context.getResource('WEB-INF/classes/about.properties')
		
		def properties = new Properties()
		try {
			if(resource) {
				def is = resource.getInputStream()
				properties.load(is)
				statusCode = 200
			}
		}
		catch(OwfException owe) {
			handleError(owe)
			statusCode = owe.exceptionType.normalReturnCode
		}
		
		def version = properties.getProperty("projectVersion")
		def jsonResult = [serverVersion:version] as JSON
		
		renderResult(jsonResult, statusCode)
	}

	def list = {
		def statusCode
		def jsonResult
        StopWatch stopWatch = null;

        if (log.isInfoEnabled()) {
          stopWatch = new StopWatch();
          stopWatch.start();
          log.info("Executing preferenceService: list");
        }
		try {
			def result = preferenceService.list(params)
			statusCode = 200
			def preferenceList = new JSONArray()
	    	result.preference.each { preferenceList.add(serviceModelService.createServiceModel(it)) }
			if (result.count != null)
			{
				jsonResult = [success:result.success, results: result.count, rows : preferenceList] as JSON
			}
			else
			{
				jsonResult = preferenceList as JSON	
			}
		}
		catch (OwfException owe) {
			handleError(owe)
			statusCode = owe.exceptionType.normalReturnCode
			jsonResult = "Error during list: " + owe.exceptionType.generalMessage + " " + owe.message
		}
		
		renderResult(jsonResult, statusCode)
		
		if (log.isInfoEnabled()) {
        	log.info("Executed preferenceService: list in "+stopWatch);
		}
	}

	def create = {
		def statusCode
		def jsonResult
        StopWatch stopWatch = null;

        if (log.isInfoEnabled()) {
          stopWatch = new StopWatch();
          stopWatch.start();
          log.info("Executing preferenceService: create");
        }
		try {
			def result = preferenceService.create(params)
			statusCode = 200
			jsonResult = getJsonResult(result, modelName, params)
		}
		catch (OwfException owe) {
			handleError(owe)
			statusCode = owe.exceptionType.normalReturnCode
			jsonResult = "Error during create: " + owe.exceptionType.generalMessage + " " + owe.message
		}
		
		renderResult(jsonResult, statusCode)
		
		if (log.isInfoEnabled()) {
        	log.info("Executed preferenceService: create in "+stopWatch);
		}
	}

	def update = {
		def statusCode
		def jsonResult
        StopWatch stopWatch = null;

        if (log.isInfoEnabled()) {
          stopWatch = new StopWatch();
          stopWatch.start();
          log.info("Executing preferenceService: update");
        }
		try {
			def result = preferenceService.update(params)
			statusCode = 200
			jsonResult = getJsonResult(result, modelName, params)
		}
		catch (OwfException owe) {
			handleError(owe)
			statusCode = owe.exceptionType.normalReturnCode
			jsonResult = "Error during update: " + owe.exceptionType.generalMessage + " " + owe.message
		}
		
		renderResult(jsonResult, statusCode)
		
		if (log.isInfoEnabled()) {
        	log.info("Executed preferenceService: update in "+stopWatch);
		}
	}

	def delete = {
		def statusCode
		def jsonResult
        StopWatch stopWatch = null;

        if (log.isInfoEnabled()) {
          stopWatch = new StopWatch();
          stopWatch.start();
          log.info("Executing preferenceService: delete");
        }
		try {
			def result = preferenceService.delete(params)
			statusCode = 200
			jsonResult = getJsonResult(result, modelName, params)
		}
		catch (OwfException owe) {
			handleError(owe)
			statusCode = owe.exceptionType.normalReturnCode
			jsonResult = "Error during delete: " + owe.exceptionType.generalMessage + " " + owe.message
		}
		
		renderResult(jsonResult, statusCode)
		
		if (log.isInfoEnabled()) {
        	log.info("Executed preferenceService: delete in "+stopWatch);
		}
	}
  	
//  	def bulkDelete = {	
//		def statusCode
//		def jsonResult
//        StopWatch stopWatch = null;
//
//        if (log.isInfoEnabled()) {
//          stopWatch = new StopWatch();
//          stopWatch.start();
//          log.info("Executing preferenceService: bulkDelete");
//        }
//		try {
//			def result = preferenceService.bulkDelete(params)
//			statusCode = 200
//			jsonResult = getJsonResult(result, modelName, params)
//		}
//		catch (OwfException owe) {
//			handleError(owe)
//			statusCode = owe.exceptionType.normalReturnCode
//			jsonResult = "Error during bulkDelete: " + owe.exceptionType.generalMessage + " " + owe.message
//		}
//		
//		renderResult(jsonResult, statusCode)
//		
//		if (log.isInfoEnabled()) {
//        	log.info("Executed preferenceService: bulkDelete in "+stopWatch);
//		}
//  	}
}
