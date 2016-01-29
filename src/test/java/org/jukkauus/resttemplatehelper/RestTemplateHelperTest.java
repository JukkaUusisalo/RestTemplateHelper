package org.jukkauus.resttemplatehelper;

import junit.framework.TestCase;

public class RestTemplateHelperTest extends TestCase {

    private static String[] sites = {
            "http://nest.su.se/dataPortal/getStations?latBegin=55.1000&latEnd=55.4000&lonBegin=15.7168&lonEnd=16.2432&dateBegin=2000-01-01&dateEnd=2000-12-31",
            "https://www.nuortenideat.fi/api/open/0.1/ideas/"

    };

    public void testGet() throws Exception {
        RestTemplateHelper templateHelper = new RestTemplateHelper();
        for(String url:sites) {
            assertEquals("200", templateHelper.get(url));
        }

    }

    public void testGetHttp() throws Exception {
        RestTemplateHelper templateHelper = new RestTemplateHelper();
        assertEquals("200", templateHelper.get(sites[0]));
    }
    public void testGetHttps() throws Exception {
        RestTemplateHelper templateHelper = new RestTemplateHelper();
        assertEquals("200", templateHelper.get(sites[1]));
    }


}