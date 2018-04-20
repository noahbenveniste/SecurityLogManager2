package edu.ncsu.csc316.security_log.manager;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import edu.ncsu.csc316.security_log.io.SecurityLogIO;

/**
 * Test class for SecurityLogManager
 * @author Noah Benveniste
 */
public class SecurityLogManagerTest {
    /** activityLog_small.txt sorted
    05/04/2015 02:09:40PM fzalcala sort ICD-9 Code 196
    06/09/2015 06:26:04AM fzalcala sort ICD-9 Code 196
    
    07/18/2015 07:57:42PM quhundley sort ICD-9 Code 196
    10/04/2015 12:17:49PM fzalcala sort ICD-9 Code 196
    02/04/2016 08:49:22AM quhundley sort ICD-9 Code 196
    08/04/2016 06:57:34AM fzalcala resolve message M2964
    10/07/2016 07:08:47AM fzalcala sort ICD-9 Code 196
    11/20/2016 02:07:54PM quhundley sort ICD-9 Code 196
    04/26/2017 12:33:15PM fzalcala sort ICD-9 Code 196
    07/03/2017 12:36:05AM fzalcala sort ICD-9 Code 196
    08/04/2017 11:01:45AM quhundley sort ICD-9 Code 196
    08/10/2017 05:10:54AM fzalcala sort ICD-9 Code 196
    08/26/2017 08:15:06AM fzalcala sort ICD-9 Code 196
    09/21/2017 08:50:13AM quhundley import office visit OV04312
    
    10/24/2017 11:38:02AM fzalcala sort ICD-9 Code 196
    11/20/2017 11:38:22AM fzalcala sort ICD-9 Code 196
    */
    
    private static final String NO_ACTIVITY = "OperationalProfile[\n   No activity was recorded.\n]";
    private static final String BAD_START_TIME = "11/20/2017 11:38:23AM";
    private static final String BAD_END_TIME = "05/04/2015 02:09:39PM";
    private static final String GOOD_START_TIME = "07/18/2015 07:57:41PM";
    private static final String GOOD_END_TIME = "09/21/2017 08:50:14AM";
    
    /**
     * Tests generateOperationalProfile()
     * @throws IOException if the file is poorly formatted
     */
    @Test
    public void testGenerateOperationalProfile() throws IOException {
//        SecurityLogManager m2 = new SecurityLogManager("input/activityLog_medium.txt");
//        System.out.println(m2.generateOperationalProfile("01/01/1980 12:00:00AM", "01/01/2020 12:00:00AM"));
        
        SecurityLogIO io = new SecurityLogIO();
        assertNotNull(io);
        SecurityLogManager manager = new SecurityLogManager("input/activityLog_small.txt");
        // Try generating profile with bad start time
        assertEquals(NO_ACTIVITY, manager.generateOperationalProfile(BAD_START_TIME, BAD_END_TIME));
        assertEquals(NO_ACTIVITY, manager.generateOperationalProfile(GOOD_START_TIME, BAD_END_TIME));
        assertEquals(NO_ACTIVITY, manager.generateOperationalProfile(BAD_START_TIME, GOOD_END_TIME));
        
        String expected = "OperationalProfile[\n" + 
                "   sort ICD-9 Code 196: frequency: 10, percentage: 83.3%\n" + 
                "   import office visit OV04312: frequency: 1, percentage: 8.3%\n" + 
                "   resolve message M2964: frequency: 1, percentage: 8.3%\n" + 
                "]";
        String actual = manager.generateOperationalProfile(GOOD_START_TIME, GOOD_END_TIME);
        assertEquals(expected, actual);
        
    }
    
    /**
     * Tests getUserReport()
     * @throws IOException if the file is poorly formatted
     */
    @Test
    public void testGetUserReport() throws IOException {
        SecurityLogManager manager = new SecurityLogManager("input/activityLog_small.txt");
        
        String expected = "Activity Report for fzalcala[\n" + 
                "   05/04/2015 02:09:40PM - sort ICD-9 Code 196\n" + 
                "   06/09/2015 06:26:04AM - sort ICD-9 Code 196\n" + 
                "   10/04/2015 12:17:49PM - sort ICD-9 Code 196\n" + 
                "   08/04/2016 06:57:34AM - resolve message M2964\n" + 
                "   10/07/2016 07:08:47AM - sort ICD-9 Code 196\n" + 
                "   04/26/2017 12:33:15PM - sort ICD-9 Code 196\n" + 
                "   07/03/2017 12:36:05AM - sort ICD-9 Code 196\n" + 
                "   08/10/2017 05:10:54AM - sort ICD-9 Code 196\n" + 
                "   08/26/2017 08:15:06AM - sort ICD-9 Code 196\n" + 
                "   10/24/2017 11:38:02AM - sort ICD-9 Code 196\n" + 
                "   11/20/2017 11:38:22AM - sort ICD-9 Code 196\n" + 
                "]";
        String actual = manager.getUserReport("fzalcala");
        assertEquals(expected, actual);
    }

}
