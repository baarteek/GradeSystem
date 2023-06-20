package com.gradingsystem.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.gradingsystem.controllers.StudentGradesController;

public class StudentGradesControllerTest {

    private String[] gradesData;

    @Before
    public void setUp() {
        String userDataString = "GET_STUDENT_SUBJECTS_DATA_SUCCESS|MATEMATYKA (2023)4|MATEMATYKA (2023)4|MATEMATYKA (2023)5|MATEMATYKA (2023)3|J. POLSKI (2022)|HISTORIA (2022)|MATEMATYKA (2023)|J. POLSKI (2023)|";
        gradesData = userDataString.split("\\|");
    }

    @Test
    public void testExtractSubjectGrades() {
        Map<String, Map<String, List<Integer>>> result = StudentGradesController.extractSubjectGrades(gradesData);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.containsKey("2023"));
        Map<String, List<Integer>> yearMap = result.get("2023");
        Assert.assertTrue(yearMap.containsKey("MATEMATYKA"));
        List<Integer> mathGrades = yearMap.get("MATEMATYKA");
        Assert.assertEquals(4, mathGrades.size());
        Assert.assertArrayEquals(new Integer[]{4, 4, 5, 3}, mathGrades.toArray(new Integer[0]));
        Assert.assertFalse(yearMap.containsKey("BIOLOGIA"));
    }
}

